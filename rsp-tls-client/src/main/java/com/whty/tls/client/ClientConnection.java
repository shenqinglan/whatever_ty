package com.whty.tls.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

import com.whty.tls.crypto.Digest;
import com.whty.tls.crypto.digests.SHA1Digest;
import com.whty.tls.crypto.digests.SHA256Digest;
import com.whty.tls.util.CombinedHash;
import com.whty.tls.util.StringUtil;
import com.whty.tls.util.TlsCipherSuite;
import com.whty.tls.util.TlsCipherSuiteManager;
import com.whty.tls.util.TlsNullCipherSuite;
import com.whty.tls.util.TlsUtils;
import com.whty.tls.util.Util;

public abstract class ClientConnection {
	protected short maxFragment = 2;
	protected CombinedHash hash1 = new CombinedHash();
	protected CombinedHash hash2 = new CombinedHash();
	protected Digest hash256_1 = new SHA256Digest();
	protected Digest hash256_2 = new SHA256Digest();
	protected Digest hash1_1 = new SHA1Digest();
	protected Digest hash1_2 = new SHA1Digest();
	protected TlsCipherSuite writeSuiteClient = new TlsNullCipherSuite();
	protected TlsCipherSuite readSuiteClient = new TlsNullCipherSuite();
	protected SecureRandom random = new SecureRandom();
	protected byte[] clientRandom;
	protected byte[] serverRandom;
	public short majorVersion = 3;
	public short minorVersion = 3;
	int maxFragmentBytes = 1024;
	protected byte[] pms;
	protected byte[] ms;
	protected int numberCipher;

	/***
	 * 拼装clientHello
	 * @return
	 * @throws IOException
	 */
	public abstract String clientHello() throws IOException;
	
	/**
	 * 接收数据并解析
	 * @param hex
	 * @return
	 * @throws IOException
	 */
	public int receiveServerDataPaser(String hex) throws IOException{
		int retVal = 0;
		InputStream recordIs = new ByteArrayInputStream(Util
				.hexStringToByteArray(hex));
		short type = TlsUtils.readUint8(recordIs);// 指明当前是handshake/ALERT/
													// Change cipher specs
													// message/application data
		boolean flag = TlsUtils.checkVersion(recordIs);// 03 03
		if (!flag) {
			return 1;
		}
		int size = TlsUtils.readUint16(recordIs);// 2字节的长度，指明后续fragment长度，不超过2^14
													// + 2048

		byte[] fragmentData = new byte[size];// handshake/ALERT/Change cipher
												// specs message/application
												// data的包
		TlsUtils.readFully(fragmentData, recordIs);

		this.hash1.update(fragmentData, 0, size);
		this.hash2.update(fragmentData, 0, size);

		this.hash256_1.update(fragmentData, 0, size);
		this.hash256_2.update(fragmentData, 0, size);

		this.hash1_1.update(fragmentData, 0, size);
		this.hash1_2.update(fragmentData, 0, size);

		byte[] buf = decodeAndVerify(type, fragmentData, size, false);
		ByteArrayInputStream is = new ByteArrayInputStream(buf);
		short handshakeType = TlsUtils.readUint8(is);// handshake层消息类型表示是
		switch (handshakeType) {
			case 2:
				// ServerHelloPaser
				System.out.println("ServerHello >>" + hex);
				retVal = serverHelloPaser(is);
				break;
			case 14:
				// ServerHelloDonePaser
				System.out.println("ServerHelloDone >>" + hex);
				System.out.println("*********ServerHelloDonePaser finish*********");
				break;
			default:
				break;
		}
		
		return retVal;
	}
	
	/**
	 * 拼装serverHello
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public int serverHelloPaser(ByteArrayInputStream is) throws IOException{
		TlsUtils.readUint24(is);
		boolean flag = TlsUtils.checkVersion(is);
		if (!flag) {
			return 11;
		}
		this.serverRandom = new byte[32];
		TlsUtils.readFully(this.serverRandom, is);
		//System.out.println("serverRandom >>"
		//		+ Util.byteArrayToHexString(this.serverRandom, ""));

		short sessionIdLength = TlsUtils.readUint8(is);
		byte[] sessionId = new byte[sessionIdLength];
		TlsUtils.readFully(sessionId, is);

		int cipherSuite = TlsUtils.readUint16(is);
		this.numberCipher = cipherSuite;
		TlsCipherSuiteManager.getCipherSuite(this.numberCipher);
		this.maxFragmentBytes = TlsCipherSuiteManager
				.getCipherSuiteMaxFragment(this.numberCipher, this.maxFragment);

		short compressionMethod = TlsUtils.readUint8(is);
		int extensionLen = TlsUtils.readUint16(is);
		int extensionType = TlsUtils.readUint16(is);
		int extensionDataLen = TlsUtils.readUint16(is);
		System.out.println(compressionMethod+extensionLen+extensionType+extensionDataLen);
		this.maxFragment = TlsUtils.readUint8(is);
		System.out.println("*********serverHelloPaser finish*********");
		return 0;	
	}

	/***
	 * 解析certificate
	 * @param certificateStr
	 * @return
	 * @throws IOException
	 */
	public abstract int certificateDataPaser(String certificateStr) throws IOException;
	
	/**
	 * 解析serverKeyExchange
	 * @param serverKeyExchangeStr
	 * @return
	 * @throws IOException
	 */
	public abstract int serverKeyExchangeDataPaser(String serverKeyExchangeStr) throws IOException;
	
	/**
	 * 拼装clientKeyExchange
	 * @return
	 * @throws IOException
	 */
	public abstract String clientKeyExchange() throws IOException;
	
	/**
	 * 拼装clientChangeCipherSpec
	 * @return
	 * @throws IOException
	 */
	public String clientChangeCipherSpec() throws IOException{
		byte[] cmessage = new byte[1];
		cmessage[0] = 1;

/*		this.writeSuiteClient = TlsCipherSuiteManager
				.getCipherSuite(this.numberCipher);
		this.writeSuiteClient.init(this.ms, this.clientRandom,
				this.serverRandom, this.majorVersion, this.minorVersion);*/
		return writeMessage((short) 20, cmessage, 0, cmessage.length, false,"clientChangeCipherSpec");
	}
	
	/**
	 * 拼装clientFinished
	 * @return
	 * @throws IOException
	 */
	public String clientFinished() throws IOException{
		this.writeSuiteClient = TlsCipherSuiteManager.getCipherSuite(this.numberCipher);
		this.writeSuiteClient.init(this.ms, this.clientRandom,
								this.serverRandom, this.majorVersion, this.minorVersion);
		
		byte[] checksum = new byte[12];

		if ((this.numberCipher == 174) || (this.numberCipher == 176) || (this.numberCipher == 49187)) {
			byte[] sha256 = new byte[32];
			this.hash256_1.doFinal(sha256, 0);
			TlsUtils.PRF_sha256(this.ms, TlsUtils.toByteArray("client finished"),
					sha256, checksum);
		} else {
			byte[] md5andsha1 = new byte[36];
			this.hash1.doFinal(md5andsha1, 0);

			TlsUtils.PRF(this.ms, TlsUtils.toByteArray("client finished"),
					md5andsha1, checksum);
		}

		System.out.println("clientFinished checksum >>>"
				+ Util.byteArrayToHexString(checksum, ""));
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		TlsUtils.writeUint8((short) 20, bos);
		TlsUtils.writeUint24(12, bos);
		bos.write(checksum);
		byte[] message = bos.toByteArray();
	
		this.hash2.update(message, 0, 16);
		this.hash256_2.update(message, 0, 16);
		this.hash1_2.update(message, 0, 16);
	
		return writeMessage((short) 22, message, 0, message.length, true,"clientFinished");
	}
	
	/**
	 * 解析serverChangeCipherSpec
	 * @param hex
	 * @return
	 * @throws IOException
	 */
	public int serverChangeCipherSpecPaser(String hex) throws IOException{
		System.out.println("serverChangeCipherSpecPaser >>" + hex);
		//this.readSuiteClient = this.writeSuiteClient;

		InputStream recordIs = new ByteArrayInputStream(Util
				.hexStringToByteArray(hex));
		short type = TlsUtils.readUint8(recordIs);
		if (type != 20) {
			System.out.println("ContentType check error");
			System.out.println("actual value="
					+ StringUtil.shortToHexWithoutHigh(type));

			System.out.println("expect value="
					+ StringUtil.shortToHexWithoutHigh((short) 20));

//			throw new RuntimeException("check error:actual value="
//					+ StringUtil.shortToHexWithoutHigh(type)
//					+ "  ,expect value="
//					+ StringUtil.shortToHexWithoutHigh((short) 20));
			return 2;
		}

		if (!TlsUtils.checkVersion(recordIs, this.majorVersion,
				this.minorVersion)) {
			return 21;
		}

		int size = TlsUtils.readUint16(recordIs);
		byte[] clientChangeCipherSpec = new byte[size];

		TlsUtils.readFully(clientChangeCipherSpec, recordIs);

		byte[] buf = decodeAndVerify(type, clientChangeCipherSpec, size, false);
		if (buf == null) {
			return 22;
		}

		byte[] cmessage = new byte[1];
		cmessage[0] = 1;

		boolean flag = TlsUtils.compareByteArray(buf, cmessage);
		if (!flag) {
			System.out.println("serverChangeCipherSpecPaser error");
			return 23;
		}

		System.out.println("*********serverChangeCipherSpecPaser finished*********");
		return 0;
	}
	
	/**
	 * 解析serverFinished
	 * @param hex
	 * @return
	 * @throws IOException
	 */
	public int serverFinishedPaser(String hex) throws IOException{
		this.readSuiteClient = this.writeSuiteClient;
		InputStream recordIs = new ByteArrayInputStream(Util
				.hexStringToByteArray(hex));
		short type = TlsUtils.readUint8(recordIs);
		if (type != 22) {
			System.out.println("ContentType check error");
			System.out.println("actual value="
					+ StringUtil.shortToHexWithoutHigh(type));
			System.out.println("expect value="
					+ StringUtil.shortToHexWithoutHigh((short) 22));

//			throw new RuntimeException("check error:actual value="
//					+ StringUtil.shortToHexWithoutHigh(type)
//					+ "  ,expect value="
//					+ StringUtil.shortToHexWithoutHigh((short) 22));
			return 3;
		}

		if (!TlsUtils.checkVersion(recordIs, this.majorVersion,
				this.minorVersion)) {
			return 31;
		}

		int size = TlsUtils.readUint16(recordIs); // 0010
		byte[] serverFinished = new byte[size];

		TlsUtils.readFully(serverFinished, recordIs);// 1400000C05EB93843E8FD5460900700C

		System.out.println("serverFinished  >>"
				+ Util.byteArrayToHexString(serverFinished, ""));

		byte[] buf = decodeAndVerify(type, serverFinished, size, true);
		if (buf == null) {
			return 32;
		}

		ByteArrayInputStream is = new ByteArrayInputStream(buf);

		type = TlsUtils.readUint8(is);
		if (type != 20) {
			System.out.println("HandshakeType check error");
			System.out.println("actual value="
					+ StringUtil.shortToHexWithoutHigh(type));
			System.out.println("expect value="
					+ StringUtil.shortToHexWithoutHigh((short) 20));

//			throw new RuntimeException("check error:actual value="
//					+ StringUtil.shortToHexWithoutHigh(type)
//					+ "  ,expect value="
//					+ StringUtil.shortToHexWithoutHigh((short) 20));
			return 33;
		}

		int verifyData = TlsUtils.readUint24(is);// 00 00 0C
		if (verifyData != 12) {
			System.out.println("verifyData length check error");
			System.out.println("actual length="
					+ StringUtil.intToHexWithHigh(verifyData));
			System.out.println("expect value=0x0C");

//			throw new RuntimeException("check error:actual length="
//					+ StringUtil.intToHexWithHigh(verifyData)
//					+ "  ,expect value=" + "0x0C");
			return 34;
		}

		byte[] receivedChecksum = new byte[12];
		TlsUtils.readFully(receivedChecksum, is);

		byte[] checksum = new byte[12];
		boolean re = false;

		if (this.minorVersion == 3) {
			if ((this.numberCipher == 174) || (this.numberCipher == 176)) {
				byte[] sha256 = new byte[32];
				this.hash256_2.doFinal(sha256, 0);
				TlsUtils.PRF_hash(this.ms, TlsUtils
						.toByteArray("server finished"), sha256, checksum,
						new SHA256Digest());
			} else {
				byte[] sha1 = new byte[20];
				this.hash1_2.doFinal(sha1, 0);
				TlsUtils.PRF_hash(this.ms, TlsUtils
						.toByteArray("server finished"), sha1, checksum,
						new SHA1Digest());
			}

			re = TlsUtils.compareByteArray(receivedChecksum, checksum);
		} else {
			byte[] md5andsha1 = new byte[36];
			this.hash2.doFinal(md5andsha1, 0);
			TlsUtils.PRF(this.ms, TlsUtils.toByteArray("server finished"),
					md5andsha1, checksum);

			re = TlsUtils.compareByteArray(receivedChecksum, checksum);
		}

		if (!re) {
			System.out.println("Finished的verify data不正确");
			System.out.println("verify data actual value="
					+ Util.byteArrayToHexString(receivedChecksum, " "));
			System.out.println("verify data expect value="
					+ Util.byteArrayToHexString(checksum, " "));

//			throw new RuntimeException("check error:actual value="
//					+ Util.byteArrayToHexString(receivedChecksum, " ")
//					+ "  ,expect value="
//					+ Util.byteArrayToHexString(checksum, " "));
			return 35;
		}

		System.out.println("*********serverFinishedPaser finished*********");
		return 0;
	}
	
	/**
	 * 客户端类TLS协议流程包头信息拼装
	 * @author Administrator
	 *
	 */
	protected String writeMessage(short type, byte[] message, int offset, int len,
			boolean flag, String shakeValue) throws IOException {
		if (type == 22) {
			ByteArrayInputStream bis = new ByteArrayInputStream(message);
			short typeMessage = TlsUtils.readUint8(bis);
			 if (typeMessage != 20) {
				 this.hash1.update(message, offset, len);
				 this.hash2.update(message, offset, len);
				 this.hash256_1.update(message, offset, len);
				 this.hash256_2.update(message, offset, len);
				 this.hash1_1.update(message, offset, len);
				 this.hash1_2.update(message, offset, len);
			 }
		}

		byte[] ciphertext;
		if (flag) {
			ciphertext = this.writeSuiteClient.encodePlaintext(type, message,
					offset, len, 0);
		} else {
			ciphertext = message;
		}
		
		byte[] writeMessage = new byte[ciphertext.length + 5];
		TlsUtils.writeUint8(type, writeMessage, 0);
		TlsUtils.writeUint8(this.majorVersion, writeMessage, 1);
		TlsUtils.writeUint8(this.minorVersion, writeMessage, 2);
		TlsUtils.writeUint16(ciphertext.length, writeMessage, 3);
		System.arraycopy(ciphertext, 0, writeMessage, 5, ciphertext.length);

    	System.out.println(shakeValue + "  >>" + Util.byteArrayToHexString(writeMessage, ""));
        System.out.println("**********"+ shakeValue + " finished**********");
		return Util.byteArrayToHexString(writeMessage, "");
	}
	
	/**
	 * 客户端类对发送的https数据进行加密
	 * @author Administrator
	 *
	 */
	
	public byte[] encryptApplicationData(byte[] message, int offset, int len) throws IOException {
		System.out.println("***********Client encryptApplicationData Begin***********");
		short type = 23;
		byte[] ciphertext = this.writeSuiteClient.encodePlaintext(type,
				message, offset, len);
		byte[] writeMessage = new byte[ciphertext.length + 5];
		TlsUtils.writeUint8(type, writeMessage, 0);
		TlsUtils.writeUint8(majorVersion, writeMessage, 1);
		TlsUtils.writeUint8(minorVersion, writeMessage, 2);
		TlsUtils.writeUint16(ciphertext.length, writeMessage, 3);
		System.arraycopy(ciphertext, 0, writeMessage, 5, ciphertext.length);
		System.out.println("***********Client encryptApplicationData End***********");
		return writeMessage;
	}

	/**
	 * 客户端类对接收的https加密数据进行解密
	 * @author Administrator
	 *
	 */
	public byte[] decryptApplicationData(byte[] hex) throws IOException {
		System.out.println("***********Client decryptApplicationData Begin***********");

		InputStream recordIs = new ByteArrayInputStream(hex);
		short type = TlsUtils.readUint8(recordIs);
		if (((type != 23 ? 1 : 0) & (type != 21 ? 1 : 0)) != 0) {
			System.out.println("ContentType check error");
			System.out.println("actual type value=" + type);
			System.out.println("expect type value=23");
			return null;
		}
		TlsUtils.checkVersion(recordIs, majorVersion, minorVersion);
		int size = TlsUtils.readUint16(recordIs);
		/*if (size > maxFragmentBytes) {
			System.out.println("MaxFragment check error");
			System.out.println("actual type value=" + size);
			System.out.println("expect type value=" + maxFragmentBytes);
			return null;
		}*/
		byte[] applicationData = new byte[size];
		TlsUtils.readFully(applicationData, recordIs);
		byte[] buffer = decodeAndVerify(type, applicationData, size, true);
		System.out.println("***********Client decryptApplicationData End***********");
		return buffer;
	}

	/**
	 * 数据解密
	 * @author Administrator
	 *
	 */
	protected byte[] decodeAndVerify(short type, byte[] buf, int len, boolean flag)
			throws IOException {
		if (flag) {
			byte[] result = this.readSuiteClient.decodeCiphertext(type, buf, 0,
					buf.length);
			return result;
		}
		return buf;
	}
}
