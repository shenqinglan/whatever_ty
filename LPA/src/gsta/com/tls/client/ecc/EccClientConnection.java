package gsta.com.tls.client.ecc;

import gsta.com.tls.client.ClientConnection;
import gsta.com.tls.util.ECCUtils;
import gsta.com.tls.util.TlsUtils;
import gsta.com.tls.util.Util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;

public class EccClientConnection extends ClientConnection {
	private byte[] ecdsaPublicKey;
	private byte[] serverECDHEPublicKey;
	private String P;
	private String A;
	private String B;
	private String Gx;
	private String Gy;
	private String N;
	private String H;
	
	@Override
	public String clientHello() throws IOException {
		this.hash1.reset();
		this.hash2.reset();
		this.hash256_1.reset();
		this.hash256_2.reset();
		this.hash1_1.reset();
		this.hash1_2.reset();

		this.clientRandom = new byte[32];
		this.random.nextBytes(this.clientRandom);
		TlsUtils.writeGMTUnixTime(this.clientRandom, 0);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		TlsUtils.writeVersion(os, this.majorVersion, this.minorVersion);
		
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

		return writeMessage((short) 22, message, 0, message.length, false,"clientHello");
	}

	@Override
	public int certificateDataPaser(String certificateStr) throws IOException {
		System.out.println("certificate >>" + certificateStr);
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
		//System.out.println("receive certificateData >>" + Util.byteArrayToHexString(fragmentData, ""));
		this.hash1.update(fragmentData, 0, size);
		this.hash2.update(fragmentData, 0, size);

		this.hash256_1.update(fragmentData, 0, size);
		this.hash256_2.update(fragmentData, 0, size);

		this.hash1_1.update(fragmentData, 0, size);
		this.hash1_2.update(fragmentData, 0, size);

		byte[] buf = decodeAndVerify(type, fragmentData, size, false);
		ByteArrayInputStream is = new ByteArrayInputStream(buf);
		TlsUtils.readUint8(is);// handshake层消息类型表示是 11表示证书
		
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

		System.out.println("*********certificateDataPaser finish*********");
		return retVal;
	}

	@Override
	public int serverKeyExchangeDataPaser(String serverKeyExchangeStr)
			throws IOException {
		System.out.println("serverKeyExchange >>" + serverKeyExchangeStr);
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
		//System.out.println("receive serverKeyExchange >>" + Util.byteArrayToHexString(fragmentData, ""));
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
		//System.out.println("ECDHEPublicKey >>"+ Util.byteArrayToHexString(this.serverECDHEPublicKey, ""));

		int signLen = TlsUtils.readUint16(is);
		byte[] signData = new byte[signLen];
		TlsUtils.readFully(signData, is);
		//System.out.println("signData >> "+Util.byteArrayToHexString(signData, ""));
		
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
		
		System.out.println("*********certificateDataPaser finish*********");		
		return retVal;
	}
	
	@Override
	public String clientKeyExchange() throws IOException {
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
        System.out.println("pms >>" + Util.byteArrayToHexString(this.pms,""));
        System.out.println("ms >>" + Util.byteArrayToHexString(ms,""));
		
		System.out.println("*********clientKeyExchange finished*********");
		return Util.byteArrayToHexString(bos2message, "");
	}
}
