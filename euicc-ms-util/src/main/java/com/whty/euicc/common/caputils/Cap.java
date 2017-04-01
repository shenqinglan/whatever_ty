package com.whty.euicc.common.caputils;

import com.whty.euicc.common.utils.CloseUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;


/**
 * CAP包<br>
 * <br>
 * CAP包本质上是压缩的zip包,完整的cap包，包含如下12个子包：<br>
 * 
 * COMPONENT_Header 1<br>
 * COMPONENT_Directory 2<br>
 * COMPONENT_Applet 3<br>
 * COMPONENT_Import 4<br>
 * COMPONENT_ConstantPool 5<br>
 * COMPONENT_Class 6<br>
 * COMPONENT_Method 7<br>
 * COMPONENT_StaticField 8<br>
 * COMPONENT_ReferenceLocation 9<br>
 * COMPONENT_Export 10<br>
 * COMPONENT_Descriptor (optional) 11<br>
 * COMPONENT_Debug (no load) 12<br>
 * <br>
 * 每个子包，是多个APDU的集合<br>
 * 
 * @author bjw@whty.com.cn
 * @date 2014年11月28日 下午4:05:38
 */
public class Cap {
	public static final byte DOWNLOAD_ORDER[] = { 0, 1, 2, 4, 3, 9, 5, 6, 7, 10, 8, 11 };
	public static final byte ORDER_TO_TAG[]   = { 0, 1, 2, 4, 3, 6, 7, 8, 10, 5, 9, 11 };
	public static final byte COMPONENT_TAGS[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };

	private static ResourceBundle msg = ResourceBundle.getBundle("com.whty.euicc.common.cap.MessagesBundle");
	
	private ZipEntry[] components = new ZipEntry[12];
	private InputStream[] byteDatas = new InputStream[12];
	
	private ZipFile zipFile;
	
	private String pkgName;
	private byte f_flags;
	private boolean noBeginEnd;

	private CapHeader capHeader;

	/**
	 * 
	 * @param capPath
	 *            cap包路径
	 * @param pkgName
	 *            需要解析的包名。设为null，解析所有包路径
	 * @throws CapException
	 */
	public Cap(String capPath, String pkgName) throws CapException {
		this(new File(capPath), pkgName);
	}

	public Cap(File capFile, String pkgName) throws CapException {
		this.pkgName = pkgName;
		try {
			zipFile = new ZipFile(capFile);
			

			if (zipFile == null)
				throw new CapException("The cap file is null");

			verifyCAP();

		} catch (CapException e) {
			throw e;
		} catch (Exception e) {
			throw new CapException(MessageFormat.format(msg.getString("CAP.constructor"), ""), e);
		}
	}
	
	public Cap(InputStream stream, String pkgName) throws CapException {
		this.pkgName = pkgName;
		try {
			ZipInputStream zipFile = new ZipInputStream(stream);
			ZipEntry entry;
			while((entry = zipFile.getNextEntry()) != null){
				if(entry.isDirectory()){
					System.out.println(entry.getName());
				}else{
					int size = (int) entry.getSize();
					byte[] data = new byte[size];
					int len = zipFile.read(data);
					if(len == size){
						this.sortComponent(entry,new ByteArrayInputStream(data));
					}else{
						throw new CapException("");
					}
				}				
			}
			this.capHeader = verifyHeader(byteDatas[DOWNLOAD_ORDER[1]], components[DOWNLOAD_ORDER[1]]);
			//verifyCAP();

		} catch (CapException e) {
			throw e;
		} catch (Exception e) {
			throw new CapException(MessageFormat.format(msg.getString("CAP.constructor"), ""), e);
		}
	}

	/**
	 * 获取header信息对象
	 * 
	 * @return
	 */
	public CapHeader getCapHeader() {
		return capHeader;
	}

	/**
	 * 生成APDU脚本
	 * 
	 * @return
	 * @throws CapException
	 */
	public String genScript() throws CapException {

		StringBuilder sb = genBeginCAP("").append("\r\n");
		
		for (int i = 1; i < DOWNLOAD_ORDER.length; i++) {
			if (i == DOWNLOAD_ORDER[11])
				continue;
			if (components[i] == null) {
				if ((i != DOWNLOAD_ORDER[3] || (f_flags & 4) == 4) && (i != DOWNLOAD_ORDER[10] || (f_flags & 2) == 2)) {
					throw new CapException(MessageFormat.format(msg.getString("CAP.missingComp"), new Integer(ORDER_TO_TAG[i])));
				}
				continue;
			}
			sb.append(genComponent(zipFile, components[i], ORDER_TO_TAG[i]));

		}

		sb.append(genEOF());
		return sb.toString();
	}

	private void verifyCAP() throws CapException, IOException {
		for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e.hasMoreElements();) {
			ZipEntry ze = e.nextElement();
			sortComponent(ze,zipFile.getInputStream(ze));
		}

		this.capHeader = verifyHeader(zipFile, components[DOWNLOAD_ORDER[1]]);
	}

	private CapHeader verifyHeader(ZipFile zipFile, ZipEntry header) throws CapException {
		InputStream inputStream = null;
		try {
			String msgArgs = header.getName();
			// 打开流对象
			inputStream = zipFile.getInputStream(header);
			CapHeader ch = new CapHeader(inputStream);

			int f_flags = ch.getFlags();
			// if ((f_flags & 1) != 1)
			// ;
			if ((f_flags & 2) == 2 && !isComponentPresent(DOWNLOAD_ORDER[10])) {
				throw new CapException(MessageFormat.format(msg.getString("CAP.expMissing"), msgArgs));
			}
			if ((f_flags & 4) == 4 && !isComponentPresent(DOWNLOAD_ORDER[3])) {
				throw new CapException(MessageFormat.format(msg.getString("CAP.appMissing"), msgArgs));
			}

			return ch;
		} catch (CapException e) {
			throw e;
		} catch (Exception e) {
			throw new CapException("Exception from CAP.verifyHeader()", e);
		} finally {
			CloseUtil.quietClose(inputStream);
		}
	}
	private CapHeader verifyHeader(InputStream inputStream, ZipEntry header) throws CapException {
		
		try {
			String msgArgs = header.getName();
			// 打开流对象
			CapHeader ch = new CapHeader(inputStream);
			
			int f_flags = ch.getFlags();
			// if ((f_flags & 1) != 1)
			// ;
			if ((f_flags & 2) == 2 && !isComponentPresent(DOWNLOAD_ORDER[10])) {
				throw new CapException(MessageFormat.format(msg.getString("CAP.expMissing"), msgArgs));
			}
			if ((f_flags & 4) == 4 && !isComponentPresent(DOWNLOAD_ORDER[3])) {
				throw new CapException(MessageFormat.format(msg.getString("CAP.appMissing"), msgArgs));
			}
			
			return ch;
		} catch (CapException e) {
			throw e;
		} catch (Exception e) {
			throw new CapException("Exception from CAP.verifyHeader()", e);
		} finally {
			CloseUtil.quietClose(inputStream);
		}
	}

	private void sortComponent(ZipEntry je,InputStream data) throws CapException {
		String name = je.getName();
		int order = 0;
		boolean pkgOK = false;
		if (pkgName == null)
			pkgOK = true;
		int slashindex = name.lastIndexOf('/');
		if (slashindex >= 0 && slashindex < name.length()) {
			String dir = name.substring(0, slashindex);
			name = name.substring(slashindex + 1).toLowerCase();
			if (pkgName != null && dir.replace('/', '.').equals((new StringBuilder()).append(pkgName).append(".javacard").toString()))
				pkgOK = true;
		}

		if (pkgOK && name.equals("header.cap"))
			order = DOWNLOAD_ORDER[1];
		else if (pkgOK && name.equals("directory.cap"))
			order = DOWNLOAD_ORDER[2];
		else if (pkgOK && name.equals("applet.cap"))
			order = DOWNLOAD_ORDER[3];
		else if (pkgOK && name.equals("import.cap"))
			order = DOWNLOAD_ORDER[4];
		else if (pkgOK && name.equals("constantpool.cap"))
			order = DOWNLOAD_ORDER[5];
		else if (pkgOK && name.equals("class.cap"))
			order = DOWNLOAD_ORDER[6];
		else if (pkgOK && name.equals("method.cap"))
			order = DOWNLOAD_ORDER[7];
		else if (pkgOK && name.equals("staticfield.cap"))
			order = DOWNLOAD_ORDER[8];
		else if (pkgOK && name.equals("export.cap"))
			order = DOWNLOAD_ORDER[10];
		else if (pkgOK && name.equals("reflocation.cap"))
			order = DOWNLOAD_ORDER[9];
		else if (pkgOK && name.equals("descriptor.cap"))
			order = DOWNLOAD_ORDER[11];
		else{
			return;
		}
		if (components[order] != null) {
			throw new CapException(MessageFormat.format(msg.getString("CAP.dup"), name));
		} else {
			components[order] = je;
			this.byteDatas[order] = data;
		}
	}

	private StringBuilder genComponent(ZipFile jf, ZipEntry je, int tag) throws CapException {
		InputStream in = null;
		try {
			in = jf.getInputStream(je);
			byte line[] = new byte[32];
			StringBuilder sb = new StringBuilder("\r\n//");
			sb.append(je.getName()).append("\r\n");

			sb.append(genCOMP(tag)).append("\r\n");
			int size;
			while ((size = in.read(line, 0, line.length)) != -1) {
				sb.append(genData(tag, line, size)).append("\r\n");
			}
			sb.append(genEOC(tag)).append("\r\n");
			return sb;
		} catch (Exception e) {
			throw new CapException(MessageFormat.format(msg.getString("CAP.genEx: "), e.toString()));
		} finally {
			CloseUtil.quietClose(in);
		}
	}

	private StringBuilder genData(int tag, byte data[], int size) {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < size; j++) {
			sb.append(" ");
			sb.append(toHex(data[j]));
		}

		return genAPDU(-76, tag, sb.toString(), size);
	}

	private StringBuilder genAPDU(int cmdCode, int tag, String data, int size) {
		StringBuilder apdu = new StringBuilder();
		switch (cmdCode) {
		case -76:
			switch (tag) {
			case 1: // '\001'
			case 2: // '\002'
			case 3: // '\003'
			case 4: // '\004'
			case 5: // '\005'
			case 6: // '\006'
			case 7: // '\007'
			case 8: // '\b'
			case 9: // '\t'
			case 10: // '\n'
				apdu.append(toHex((byte) -128))
				.append(" ").append(toHex((byte) -76))
				.append(" ").append(toHex(COMPONENT_TAGS[tag])).append(" 0x00 ")
				.append(toHex((byte) size)).append(data).append(" 0x7F;");
				break;

			default:
				return apdu;
			}
			break;

		case -70:
			apdu = new StringBuilder(System.getProperty("line.separator", "\n"));
			// fall through

		case -80:
			if (noBeginEnd)
				break;
			// fall through

		case -78:
		case -68:
			apdu.append(apdu).append(toHex((byte) -128)).append(" ").append(toHex((byte) cmdCode));
			if(tag < 0 ){
				apdu.append(" 0x00");
			}else{
				apdu.append(" ").append(toHex((byte) tag));
			}
			//apdu.append(tag < 0 ? " 0x00" : (new StringBuilder()).append(" ").append(toHex((byte) tag)).toString());
			apdu.append(" 0x00");
			if(size <=0 ){
				apdu.append(" 0x00");
			}else{
				apdu.append(" ").append(toHex((byte) size));
			}
			//apdu.append(size <= 0 ? "0x00" : (new StringBuilder()).append(" ").append(toHex((byte) size)).toString());
			if(data != null && data.length() > 0){
				apdu.append(" ").append(data);
			}
			//apdu.append(data != null && data.length() > 0 ? (new StringBuilder()).append(" ").append(data).toString() : "");
			apdu.append(" 0x7F;");
			break;

		case -79:
		case -77:
		case -75:
		case -74:
		case -73:
		case -72:
		case -71:
		case -69:
		default:;
		}
		return apdu;
	}

	private StringBuilder genBeginCAP(String data) {
		return genAPDU(-80, 0, data, data.length());
	}

	private StringBuilder genEOF() {
		return genAPDU(-70, 0, null, 0);
	}

	private StringBuilder genCOMP(int tag) {
		return genAPDU(-78, tag, null, 0);
	}

	private StringBuilder genEOC(int tag) {
		return genAPDU(-68, tag, null, 0);
	}

	private String toHex(byte b) {
		String h = "";
		String n = Integer.toHexString(b & 0xff).toUpperCase();
		if (n.length() == 1)
			h = (new StringBuilder()).append("0x0").append(n).toString();
		else
			h = (new StringBuilder()).append("0x").append(n).toString();
		return h;
	}

	private boolean isComponentPresent(byte tag) {
		return tag < components.length && null != components[tag];
	}

}
