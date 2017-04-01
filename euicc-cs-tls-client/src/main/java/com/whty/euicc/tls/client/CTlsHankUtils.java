package com.whty.euicc.tls.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.security.SecureRandom;

import org.apache.commons.lang3.StringUtils;

import com.whty.euicc.tls.client.crypto.Digest;
import com.whty.euicc.tls.client.crypto.digests.SHA1Digest;
import com.whty.euicc.tls.client.crypto.digests.SHA256Digest;
import com.whty.euicc.tls.client.tls.CombinedHash;
import com.whty.euicc.tls.client.tls.IniFile;
import com.whty.euicc.tls.client.tls.PSK;
import com.whty.euicc.tls.client.tls.StringUtil;
import com.whty.euicc.tls.client.tls.TlsCipherSuite;
import com.whty.euicc.tls.client.tls.TlsCipherSuiteManager;
import com.whty.euicc.tls.client.tls.TlsNullCipherSuite;
import com.whty.euicc.tls.client.tls.TlsUtils;
import com.whty.euicc.tls.client.tls.Util;
import com.whty.security.ecc.ECCUtils;

public class CTlsHankUtils {	
	PSK aPsk;
	private DataInputStream in;
	private DataOutputStream out;
	private short maxFragment = 2;
	private CombinedHash hash1 = new CombinedHash();
	private CombinedHash hash2 = new CombinedHash();
	private Digest hash256_1 = new SHA256Digest();
	private Digest hash256_2 = new SHA256Digest();
	private Digest hash1_1 = new SHA1Digest();
	private Digest hash1_2 = new SHA1Digest();
	private TlsCipherSuite writeSuiteClient = new TlsNullCipherSuite();
	private TlsCipherSuite readSuiteClient = new TlsNullCipherSuite();
	private SecureRandom random = new SecureRandom();
	private byte[] clientRandom;
	private byte[] serverRandom;
	public static final String FileName_pskKeyIni = "pskKey.ini";
	public static final String pskKeyIni_PSKKEY = "PSKKEY";
	public static final String pskKeyIni_PSKID = "PSKID";
	public short majorVersion = 3;
	public short minorVersion = 3;
	int maxFragmentBytes = 1024;
	private byte[] pms;
	private byte[] ms;
	private int numberCipher;
	private byte[] ecdsaPublicKey;
	private byte[] serverECDHEPublicKey;
	private String P;
	private String A;
	private String B;
	private String Gx;
	private String Gy;
	private String N;
	private String H;
	/**
	 * 客户端类TLS协议流程
	 * @author Administrator
	 *
	 */
	public int shakeHands(Socket s) throws Exception {		
		in = new DataInputStream(s.getInputStream());
		out = new DataOutputStream(s.getOutputStream());
		int retVal = 0;
		//第一步 客户端发送clientHello
		String clientHello = clientHello();
		int length = clientHello.getBytes().length;
		out.writeInt(length);
		SocketUtils.writeBytes(out, clientHello.getBytes(), length);

		//第二步 客户端收到serverHello做解析
		int len = in.readInt();
		in.skipBytes(4);
		byte[] serverHello = SocketUtils.readBytes(in, len);
		String strServerHello = new String(serverHello);
		retVal = receiveServerDataPaser(strServerHello);
		if (retVal != 0) {
			return retVal;
		}
		
		//第三步 客户端收到certificateSend做解析，需要保存公钥信息
		len = in.readInt();
		in.skipBytes(4);
		byte[] certificate = SocketUtils.readBytes(in, len);
		String certificateStr = new String(certificate);
		retVal = certificateDataPaser(certificateStr);
		if (retVal != 0) {
			return retVal;
		}

		//第四步 客户端收到serverKeyExchange做解析
		//先验签，提取ECDH临时公钥，后面用于计算PMS
		len = in.readInt();
		in.skipBytes(4);
		byte[] serverKeyExchange = SocketUtils.readBytes(in, len);
		String serverKeyExchangeStr = new String(serverKeyExchange);
		retVal = serverKeyExchangeDataPaser(serverKeyExchangeStr);
		if (retVal != 0) {
			return retVal;
		}
		
		// 第五步 客户端收到服务器发送的数据，判断是不是serverHelloDone，是的话，发送数据给服务器
		len = in.readInt();
		in.skipBytes(4);
		byte[] serverHelloDone = SocketUtils.readBytes(in, len);
		String strServerHelloDone = new String(serverHelloDone);
		retVal = receiveServerDataPaser(strServerHelloDone);
		if (retVal != 0) {
			return retVal;
		}
		
		// 第六步 客户端发送clientKeyExchange
		String clientKeyExchange = clientKeyExchange();
		length = clientKeyExchange.getBytes().length;
		out.writeInt(length);
		SocketUtils.writeBytes(out, clientKeyExchange.getBytes(), length);

		// 第七步 客户端发送clientChangeCipherSpec
		String clientChangeCipherSpec = clientChangeCipherSpec();
		length = clientChangeCipherSpec.getBytes().length;
		out.writeInt(length);
		SocketUtils.writeBytes(out, clientChangeCipherSpec.getBytes(), length);

		// 第八步发送ClientFinish
		String clientFinished = clientFinished();
		length = clientFinished.getBytes().length;
		out.writeInt(length);
		SocketUtils.writeBytes(out, clientFinished.getBytes(), length);

		// 第九步 收到serverChangeCipherSpec
		length = in.readInt();
		in.skipBytes(4);
		byte[] serverChangeCipherSpec = SocketUtils.readBytes(in, length);
		String strserverChangeCipherSpec = new String(serverChangeCipherSpec);
		retVal = serverChangeCipherSpecPaser(strserverChangeCipherSpec);
		if (retVal != 0) {
			return retVal;
		}

		// 第十步 收到serverFinished
		length = in.readInt();
		in.skipBytes(4);
		byte[] serverFinishedPaser = SocketUtils.readBytes(in, length);
		String strserverFinishedPaser = new String(serverFinishedPaser);
		retVal = serverFinishedPaser(strserverFinishedPaser);
		if (retVal != 0) {
			return retVal;
		}
		
		return 0;
	}

	/**
	 * 客户端类TLS协议流程clienthello数据拼装
	 * @author Administrator
	 *
	 */
	private String clientHello() throws IOException {
		this.hash1.reset();
		this.hash2.reset();
		this.hash256_1.reset();
		this.hash256_2.reset();
		this.hash1_1.reset();
		this.hash1_2.reset();

		this.clientRandom = new byte[32];
		this.random.nextBytes(this.clientRandom);
		TlsUtils.writeGMTUnixTime(this.clientRandom, 0);
		System.out.println("clientRandom >>"
				+ Util.byteArrayToHexString(this.clientRandom, ""));

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TlsUtils.writeVersion(os, (short) 3, (short) 2);
		
        //this.clientRandom = Util.hexStringToByteArray("00010203000102030405060708090A0B0C0D0E0F101112131415161718191A1B");
        //System.out.println("clientRandom >>" + Util.byteArrayToHexString(this.clientRandom, ""));  
        
		os.write(this.clientRandom);
		
/*		byte[] sessionID = new byte[32];
		for (int i = 0; i < sessionID.length; i++) {
			sessionID[i] = 0;
		}
		sessionID[31] = 1;
		TlsUtils.writeUint8((short) sessionID.length, os);
		os.write(sessionID);*/
		//sessionID length :0
		TlsUtils.writeUint8((short) 0, os);
		
		//Cipher Suites Length:
		TlsUtils.writeUint16(8, os);
		//Cipher Suites
		TlsUtils.writeUint16(174, os);
		TlsUtils.writeUint16(168, os);
		TlsUtils.writeUint16(176, os);
		//CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 = {0xC0,0x23};
		TlsUtils.writeUint16(49187, os);
		
		//Compression Methods Length:
		TlsUtils.writeUint8((short) 1, os);
		//Compression Methods
		TlsUtils.writeUint8((short) 0, os);
		
		//Extension client_hello_extension_list
		//Extensions Length:
		TlsUtils.writeUint16(19, os);
		//Extension:
		//Type: max_fragment_length(0x0001)
		TlsUtils.writeUint16(1, os);
		//Length:
		TlsUtils.writeUint16(1, os);
		//
		TlsUtils.writeUint8((short) 2, os);

		//RFC4492
		//This section specifies two TLS extensions that can be included with
		//the ClientHello message as described in [4], the Supported Elliptic
		//Curves Extension and the Supported Point Formats Extension
		//扩展字段
		//Ec_point_formats：支持的曲线点格式，默认都是 uncompressed。
		//ec_point_formats  0x000b 0002 01 00
		//Type:
		TlsUtils.writeUint16(11, os);
		//Length:
		TlsUtils.writeUint16(2, os);
		//EC point formats Length:
		TlsUtils.writeUint8((short) 1, os);
		//EC point formats:
		TlsUtils.writeUint8((short) 0, os);
		
		//Elliptic_curves：客户端支持的曲线类型和有限域参数。现在使用最多的是 256 位的素数域
		//elliptic_curves  0x000a 0004 0002 0017
		//0x0017代表的是Elliptic curve:secp256r1(0x0017)
		//Type:
		TlsUtils.writeUint16(10, os);
		//Length:
		TlsUtils.writeUint16(4, os);
		//Elliptic Curves Length:
		TlsUtils.writeUint16(2, os);
		//Elliptic Curves:
		TlsUtils.writeUint16(23, os);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		TlsUtils.writeUint8((short) 1, bos);
		// 01 handshake层消息类型表示是client_hello
		TlsUtils.writeUint24(os.size(), bos);
		bos.write(os.toByteArray());
		byte[] message = bos.toByteArray();
		bos.close();

		System.out.println("1 >>clientHello finish");
		return writeMessage((short) 22, message, 0, message.length, false);
	}

	/**
	 * 客户端类TLS协议流程接收服务端发送的serverHello
	 * 及serverHelloDone通用头部解析方法
	 * @author Administrator
	 *
	 */
	private int receiveServerDataPaser(String hex) throws IOException {
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

		System.out.println("receive fragmentData >>"
				+ Util.byteArrayToHexString(fragmentData, ""));
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
				retVal = serverHelloPaser(is);
				break;
			case 14:
				// ServerHelloDonePaser
				System.out.println("5 >>ServerHelloDonePaser finish");
				break;
			default:
				break;
		}
		
		return retVal;
	}

	/**
	 * 客户端类TLS协议流程解析serverHello体部分数据
	 * @author Administrator
	 *
	 */
	private int serverHelloPaser(ByteArrayInputStream is) throws IOException {
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
		System.out.println("cipherSuite " + cipherSuite);
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
		System.out.println("2 >>serverHelloPaser finish");
		return 0;
	}

	/**
	 * 客户端类TLS协议流程解析certificate数据
	 * @param certificateStr
	 * @return
	 * @throws IOException
	 */
	private int certificateDataPaser(String certificateStr) throws IOException {
		int retVal = 0;
		InputStream recordIs = new ByteArrayInputStream(Util
				.hexStringToByteArray(certificateStr));
		short type = TlsUtils.readUint8(recordIs);// 指明当前是handshake/ALERT/
													// Change cipher specs
													// message/application data

		boolean flag = TlsUtils.checkVersion(recordIs);// 03 03
		if (!flag) {
			return 1;
		}
		int size = TlsUtils.readUint16(recordIs);// 2字节的长度，指明后续fragment长度，不超过2^14
													// + 2048

		byte[] fragmentData = new byte[size];
		TlsUtils.readFully(fragmentData, recordIs);
		System.out.println("receive certificateData >>"
				+ Util.byteArrayToHexString(fragmentData, ""));
		this.hash1.update(fragmentData, 0, size);
		this.hash2.update(fragmentData, 0, size);

		this.hash256_1.update(fragmentData, 0, size);
		this.hash256_2.update(fragmentData, 0, size);

		this.hash1_1.update(fragmentData, 0, size);
		this.hash1_2.update(fragmentData, 0, size);

		byte[] buf = decodeAndVerify(type, fragmentData, size, false);
		ByteArrayInputStream is = new ByteArrayInputStream(buf);
		short handshakeType = TlsUtils.readUint8(is);// handshake层消息类型表示是 11表示证书
		
		TlsUtils.readUint24(is);
		flag = TlsUtils.checkVersion(is);
		if (!flag) {
			return 11;
		}
		
		//公钥长度字节
		short pubLen = TlsUtils.readUint8(is);
		this.ecdsaPublicKey = new byte[pubLen];
		TlsUtils.readFully(this.ecdsaPublicKey, is);
		System.out.println("ECDSAPublicKey >>"
				+ Util.byteArrayToHexString(this.ecdsaPublicKey, ""));

		System.out.println("3 >>certificateDataPaser finish");
		return retVal;
	}

	private int serverKeyExchangeDataPaser(String serverKeyExchangeStr) throws IOException {
		int retVal = 0;
		InputStream recordIs = new ByteArrayInputStream(Util
				.hexStringToByteArray(serverKeyExchangeStr));
		short type = TlsUtils.readUint8(recordIs);// 指明当前是handshake/ALERT/
													// Change cipher specs
													// message/application data

		boolean flag = TlsUtils.checkVersion(recordIs);// 03 03
		if (!flag) {
			return 1;
		}
		int size = TlsUtils.readUint16(recordIs);// 2字节的长度，指明后续fragment长度，不超过2^14
													// + 2048

		byte[] fragmentData = new byte[size];
		
		TlsUtils.readFully(fragmentData, recordIs);
		System.out.println("receive serverKeyExchange >>"
				+ Util.byteArrayToHexString(fragmentData, ""));
		this.hash1.update(fragmentData, 0, size);
		this.hash2.update(fragmentData, 0, size);

		this.hash256_1.update(fragmentData, 0, size);
		this.hash256_2.update(fragmentData, 0, size);

		this.hash1_1.update(fragmentData, 0, size);
		this.hash1_2.update(fragmentData, 0, size);

		byte[] buf = decodeAndVerify(type, fragmentData, size, false);
		ByteArrayInputStream is = new ByteArrayInputStream(buf);
		
		TlsUtils.readUint8(is);// handshake层消息类型表示
		TlsUtils.readUint24(is);
		flag = TlsUtils.checkVersion(is);
		if (!flag) {
			return 11;
		}
		
		//Curve Type
		short curveType = TlsUtils.readUint8(is);
		switch (curveType) {
			case 3:
				int nameCurve = TlsUtils.readUint16(is);
				if (nameCurve == 23) {
					this.P="ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
					this.A="FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
					this.B="5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
					this.Gx="6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296";
					this.Gy="4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
					this.N="FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
					this.H="1";
				}
				break;
			default:
				break;
		}
		
		int pubLen = TlsUtils.readUint16(is);
		this.serverECDHEPublicKey = new byte[pubLen];
		TlsUtils.readFully(this.serverECDHEPublicKey, is);
		System.out.println("ECDHEPublicKey >>"
				+ Util.byteArrayToHexString(this.serverECDHEPublicKey, ""));

		int signLen = TlsUtils.readUint16(is);
		byte[] signData = new byte[signLen];
		TlsUtils.readFully(signData, is);
		System.out.println("signData >> "+Util.byteArrayToHexString(signData, ""));
		
		String strSign = Util.byteArrayToHexString(signData, "");
		String sR = strSign.substring(0, 64);
		String sS = strSign.substring(64);
		String sPublicKeyString = Util.byteArrayToHexString(this.ecdsaPublicKey, "");
				
		String sPax = sPublicKeyString.substring(2, 66);
		String sPay = sPublicKeyString.substring(66);
		
		StringBuilder sM = new StringBuilder().append(Util.byteArrayToHexString(this.clientRandom, "")).
				append(Util.byteArrayToHexString(this.serverRandom, "")).append("0017").append(Util.byteArrayToHexString(serverECDHEPublicKey, ""));
		
		System.out.println("sm >>" + sM);
		System.out.println("sR >>" + sR);
		System.out.println("sS >>" + sS);
		
		//验签
		boolean ret = ECCUtils.eccECKAVerify(P, A, B, Gx, Gy, N, H, sM.toString(), sPax, sPay, sR, sS);	
		if ( ret ) {
			System.out.println("Verify Success");
		}
		
		System.out.println("4 >>certificateDataPaser finish");		
		return retVal;
	}

	/**
	 * 客户端类TLS协议流程clientKeyExchange数据拼装
	 * @author Administrator
	 *
	 */
/*	private String clientKeyExchange() throws IOException {
		String vlauePskID = getPskKey(FileName_pskKeyIni, pskKeyIni_PSKID,
				"0140");
		String vlauePskKey = getPskKey(FileName_pskKeyIni, pskKeyIni_PSKKEY,
				"0140");

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		aPsk = new PSK();
		aPsk.setId(Util.hexStringToByteArray(vlauePskID));
		aPsk.setKeyValue(Util.hexStringToByteArray(vlauePskKey));

		byte[] pskIdentity = this.aPsk.getId();
		TlsUtils.writeUint8((short) 16, bos);
		TlsUtils.writeUint24(pskIdentity.length + 2, bos);
		TlsUtils.writeUint16(pskIdentity.length, bos);
		bos.write(pskIdentity);
		byte[] clientKeyExchange = bos.toByteArray();
		int size = clientKeyExchange.length;

		System.out.println("clientKeyExchange fragmentData >>"
				+ Util.byteArrayToHexString(clientKeyExchange, ""));

		this.hash1.update(clientKeyExchange, 0, size);
		this.hash2.update(clientKeyExchange, 0, size);
		this.hash256_1.update(clientKeyExchange, 0, size);
		this.hash256_2.update(clientKeyExchange, 0, size);
		this.hash1_1.update(clientKeyExchange, 0, size);
		this.hash1_2.update(clientKeyExchange, 0, size);

		ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
		TlsUtils.writeUint8((short) 22, bos2);
		TlsUtils.writeUint8(this.majorVersion, bos2);
		TlsUtils.writeUint8(this.minorVersion, bos2);
		TlsUtils.writeUint16(clientKeyExchange.length, bos2);
		bos2.write(clientKeyExchange);

		byte[] bos2message = bos2.toByteArray();

		byte[] psk = this.aPsk.getKeyValue();
		this.pms = TlsUtils.psk2pms(psk);
		this.ms = new byte[48];
		byte[] random = new byte[this.clientRandom.length
				+ this.serverRandom.length];
		System.arraycopy(this.clientRandom, 0, random, 0,
				this.clientRandom.length);
		System.arraycopy(this.serverRandom, 0, random,
				this.clientRandom.length, this.serverRandom.length);

		TlsUtils.PRF_sha256(this.pms, TlsUtils.toByteArray("master secret"), random,ms);
		System.out.println("4 >>clientKeyExchange finished");
		return Util.byteArrayToHexString(bos2message, "");
	}*/

	private String clientKeyExchange() throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //Vesion: TLS 1.2 (0x0303)
        TlsUtils.writeVersion(os, this.majorVersion, this.minorVersion);
      
        //Length:
        //EC Diffie-Hellman Client Params:
        //Pubkey Length:65
        TlsUtils.writeUint16(65, os);
        //PubKey:
		String keyPairs=ECCUtils.createECCKeyPair(P, A, B, Gx, Gy, N, H);
		if(StringUtils.isBlank(keyPairs)){
			throw new RuntimeException("生成密钥对算法出错");
		}
		String ecdhePrivateKey=keyPairs.substring(0, 64);
		System.out.println("ecdhePrivateKey >>"+ecdhePrivateKey);//临时私钥
				
		String ecdhePublicKey="04"+keyPairs.substring(64);//临时公钥
		System.out.println("ecdhePublicKey >>"+ecdhePublicKey);      	
		os.write(Util.hexStringToByteArray(ecdhePublicKey));//公钥
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short)16, bos);//(16)
        TlsUtils.writeUint24(os.size(), bos);//先把长度计算出来，拼装在字符串前面，LV结构
        bos.write(os.toByteArray()); 
        
		byte[] clientKeyExchange = bos.toByteArray();
		int size = clientKeyExchange.length;

		System.out.println("clientKeyExchange fragmentData >>"
				+ Util.byteArrayToHexString(clientKeyExchange, ""));

		this.hash1.update(clientKeyExchange, 0, size);
		this.hash2.update(clientKeyExchange, 0, size);
		this.hash256_1.update(clientKeyExchange, 0, size);
		this.hash256_2.update(clientKeyExchange, 0, size);
		this.hash1_1.update(clientKeyExchange, 0, size);
		this.hash1_2.update(clientKeyExchange, 0, size);

		ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
		TlsUtils.writeUint8((short) 22, bos2);
		TlsUtils.writeUint8(this.majorVersion, bos2);
		TlsUtils.writeUint8(this.minorVersion, bos2);
		TlsUtils.writeUint16(clientKeyExchange.length, bos2);
		bos2.write(clientKeyExchange);
        
		byte[] bos2message = bos2.toByteArray();
        //协商会话密钥
     	int Keylen=64;
     	String share="108810";
     	
     	String Q_ECASD_ECKA = Util.byteArrayToHexString(this.serverECDHEPublicKey, "");
     	String Qx_ECASD_ECKA = Q_ECASD_ECKA.substring(2, 66);
     	String Qy_ECASD_ECKA = Q_ECASD_ECKA.substring(66);     	
     	
     	System.out.println("*********************************************");
     	System.out.println("ecdhePrivateKey " + ecdhePrivateKey);
     	System.out.println("Qx_ECASD_ECKA " + Qx_ECASD_ECKA);
     	System.out.println("Qy_ECASD_ECKA " + Qy_ECASD_ECKA);
     	System.out.println("*********************************************");
		String key=ECCUtils.eccKeyAgreement(P, A, B, Gx, Gy, N, H, ecdhePrivateKey, Qx_ECASD_ECKA, Qy_ECASD_ECKA, share, Keylen);
		
		this.pms = Util.hexStringToByteArray(key);
		this.ms = new byte[48];
		byte[] random = new byte[this.clientRandom.length
				+ this.serverRandom.length];
		System.arraycopy(this.clientRandom, 0, random, 0,
				this.clientRandom.length);
		System.arraycopy(this.serverRandom, 0, random,
				this.clientRandom.length, this.serverRandom.length);

		TlsUtils.PRF_sha256(this.pms, TlsUtils.toByteArray("master secret"), random,ms);
        System.out.println("this.pms >>" + Util.byteArrayToHexString(this.pms,""));
        System.out.println("ms >>" + Util.byteArrayToHexString(ms,""));
		
		System.out.println("6 >>clientKeyExchange finished");
		return Util.byteArrayToHexString(bos2message, "");
	}
	
	/**
	 * 客户端类TLS协议流程clientChangeCipherSpec数据拼装
	 * @author Administrator
	 *
	 */
	private String clientChangeCipherSpec() throws IOException {
		byte[] cmessage = new byte[1];
		cmessage[0] = 1;

/*		this.writeSuiteClient = TlsCipherSuiteManager
				.getCipherSuite(this.numberCipher);
		this.writeSuiteClient.init(this.ms, this.clientRandom,
				this.serverRandom, this.majorVersion, this.minorVersion);*/
		System.out.println("7 >> clientChangeCipherSpec finished ");
		return writeMessage((short) 20, cmessage, 0, cmessage.length, false);
	}

	/**
	 * 客户端类TLS协议流程clientFinished数据拼装
	 * @author Administrator
	 *
	 */
	private String clientFinished() throws IOException {
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
		
		System.out.println("8 >> clientFinished finished");
		
		return writeMessage((short) 22, message, 0, message.length, true);
		// return writeMessage((short)22, message, 0, message.length);
	}

	/**
	 * 客户端类TLS协议流程服务器serverChangeCipherSpec解析
	 * @author Administrator
	 *
	 */
	private int serverChangeCipherSpecPaser(String hex) throws IOException {
		System.out.println(hex);
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

		System.out.println("7 >>serverChangeCipherSpecPaser finished");
		return 0;
	}

	/**
	 * 客户端类TLS协议流程服务器serverFinished解析
	 * @author Administrator
	 *
	 */
	private int serverFinishedPaser(String hex) throws IOException {
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

		System.out.println("buf >>" + Util.byteArrayToHexString(buf, ""));

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

		System.out.println("8 >>serverFinishedPaser finished");
		return 0;
	}

	/**
	 * 客户端类发送https加密数据
	 * @author Administrator
	 *
	 */
	public void sendEncryptData(Socket s,byte[] dataByte) throws IOException{
		out = new DataOutputStream(s.getOutputStream());
		String encryptData = encryptApplicationData(dataByte, 0, dataByte.length);	
		int length = encryptData.getBytes().length;
		out.writeInt(length);
		SocketUtils.writeBytes(out, encryptData.getBytes(), length);	
	}
	
	/**
	 * 客户端类接收https加密数据
	 * @author Administrator
	 *
	 */
	public int receiveServerData(Socket s) throws IOException{
		
		try {
			in = new DataInputStream(s.getInputStream());
			int length = in.readInt();
			in.skipBytes(4);
			byte[] serverResponse = SocketUtils.readBytes(in, length);
			String strserverResponse = new String(serverResponse);
			return decryptApplicationData(strserverResponse);
		} catch (Exception e) {
			e.printStackTrace();
		}	 
		return 0;
	}
	
	/**
	 * 客户端类对接收的https加密数据进行解密
	 * @author Administrator
	 *
	 */
	private int decryptApplicationData(String hex) throws IOException {
		System.out.println("decryptApplicationData");

		InputStream recordIs = new ByteArrayInputStream(Util
				.hexStringToByteArray(hex));
		short type = TlsUtils.readUint8(recordIs);
		if (((type != 23 ? 1 : 0) & (type != 21 ? 1 : 0)) != 0) {
			System.out.println("ContentType check error");
			System.out.println("actual type value=" + type);
			System.out.println("expect type value=23");
			return 4;
		}
		TlsUtils.checkVersion(recordIs, majorVersion, minorVersion);
		int size = TlsUtils.readUint16(recordIs);
		if (size > maxFragmentBytes) {
			System.out.println("MaxFragment check error");
			System.out.println("actual type value=" + size);
			System.out.println("expect type value=" + maxFragmentBytes);
			return 41;
		}
		byte[] applicationData = new byte[size];
		TlsUtils.readFully(applicationData, recordIs);
		byte[] buffer = decodeAndVerify(type, applicationData, size, true);
		System.out.println("buf >>" + Util.byteArrayToHexString(buffer, ""));
		
		String data = Util.byteArrayToHexString(buffer, "");
		String strData = data;
		int endOfDbl0D0A=data.indexOf("0D0A0D0A")+8;
		
		data = Util.hexToASCII(data.substring(0, endOfDbl0D0A)) + data.substring(endOfDbl0D0A);
		System.out.println("explain >> \r\n" + data);
		
		if (strData.length() == endOfDbl0D0A) {	
		} else {
			strData = strData.substring(endOfDbl0D0A+4, strData.length()-4);	
		}
		System.out.println("apdu >> " + strData);
		
		System.out.println("decryptApplicationData finished");
		return 0;
	}

	/**
	 * 客户端类对发送的https数据进行加密
	 * @author Administrator
	 *
	 */
	private String encryptApplicationData(byte[] message, int offset, int len)
			throws IOException {
		System.out.println("***********encryptApplicationData***********");
		short type = 23;
		byte[] ciphertext = this.writeSuiteClient.encodePlaintext(type,
				message, offset, len);
		byte[] writeMessage = new byte[ciphertext.length + 5];
		TlsUtils.writeUint8(type, writeMessage, 0);
		TlsUtils.writeUint8(majorVersion, writeMessage, 1);
		TlsUtils.writeUint8(minorVersion, writeMessage, 2);
		TlsUtils.writeUint16(ciphertext.length, writeMessage, 3);
		System.arraycopy(ciphertext, 0, writeMessage, 5, ciphertext.length);
		System.out.println("encryptApplicationData >> "
				+ Util.byteArrayToHexString(writeMessage, ""));
		System.out
				.println("***********encryptApplicationData finished***********");
		return Util.byteArrayToHexString(writeMessage, "");
	}

	/**
	 * 客户端类TLS协议流程包头信息拼装
	 * @author Administrator
	 *
	 */
	private String writeMessage(short type, byte[] message, int offset, int len,
			boolean flag) throws IOException {
		if (type == 22) {
			ByteArrayInputStream bis = new ByteArrayInputStream(message);
			short typeMessage = TlsUtils.readUint8(bis);
			 if (typeMessage != 20) {
				 System.out.println("add message >>>"+ Util.byteArrayToHexString(message, ""));
				 this.hash1.update(message, offset, len);
				 this.hash2.update(message, offset, len);
				 this.hash256_1.update(message, offset, len);
				 this.hash256_2.update(message, offset, len);
				 this.hash1_1.update(message, offset, len);
				 this.hash1_2.update(message, offset, len);
			 }
		}

		System.out.println("message  >>" + Util.byteArrayToHexString(message, ""));
		byte[] ciphertext;
		if (flag) {
			ciphertext = this.writeSuiteClient.encodePlaintext(type, message,
					offset, len, 0);
		} else {
			ciphertext = message;
		}
		System.out.println("ciphertext  >>" + Util.byteArrayToHexString(ciphertext, ""));
		
		byte[] writeMessage = new byte[ciphertext.length + 5];
		TlsUtils.writeUint8(type, writeMessage, 0);
		TlsUtils.writeUint8(this.majorVersion, writeMessage, 1);
		TlsUtils.writeUint8(this.minorVersion, writeMessage, 2);
		TlsUtils.writeUint16(ciphertext.length, writeMessage, 3);
		System.arraycopy(ciphertext, 0, writeMessage, 5, ciphertext.length);

		System.out.println("encode message >>>"
				+ Util.byteArrayToHexString(writeMessage, ""));

		return Util.byteArrayToHexString(writeMessage, "");
	}

	/**
	 * 获取psk信息
	 * @author Administrator
	 *
	 */
	private String getPskKey(String path, String section, String key) {
		String value = null;
		try {
			InputStream isInputStream = CTlsHankUtils.class.getClassLoader()
					.getResourceAsStream("pskKey.ini");
			IniFile iniFile = new IniFile();
			iniFile.load(isInputStream);
			value = iniFile.getKeyValue(section, key);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		}
		return value;
	}

	/**
	 * 数据解密
	 * @author Administrator
	 *
	 */
	private byte[] decodeAndVerify(short type, byte[] buf, int len, boolean flag)
			throws IOException {
		if (flag) {
			byte[] result = this.readSuiteClient.decodeCiphertext(type, buf, 0,
					buf.length);
			return result;
		}
		return buf;
	}
}
