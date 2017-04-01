package com.whty.euicc.tls.client;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

import org.apache.commons.lang3.StringUtils;

import com.whty.euicc.common.spring.SpringContextHolder;
import com.whty.euicc.data.cache.PskCache;
import com.whty.euicc.data.dao.EuiccScp81Mapper;
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


public class STlsHankUtils {
	PSK aPsk;
    private byte[] clientRandom;
    private byte[] serverRandom;
    private byte[] cipherSuites;
	private short maxFragment = 2;
    private CombinedHash hash1 = new CombinedHash();
    private CombinedHash hash2 = new CombinedHash();
    private Digest hash256_1 = new SHA256Digest();
    private Digest hash256_2 = new SHA256Digest();
    private Digest hash1_1 = new SHA1Digest();
    private Digest hash1_2 = new SHA1Digest();
    private TlsCipherSuite readSuite = new TlsNullCipherSuite();
    private TlsCipherSuite writeSuite = new TlsNullCipherSuite();
    private SecureRandom random = new SecureRandom();
    private TlsCipherSuite chosenCipherSuite = null;
    public short majorVersion = 3;
    public short minorVersion = 3;
    int maxFragmentBytes = 1024;
	public static final String FileName_pskKeyIni = "pskKey.ini";
	public static final String pskKeyIni_PSKKEY = "PSKKEY";
	public static final String pskKeyIni_PSKID = "PSKID";
    private byte[] pms;
    private byte[] ms;
    private int numberCipher;
	public static String nonSegRAM1="80E60200110CF0000000005744020300060100000000";

	private final String P="ffffffff00000001000000000000000000000000ffffffffffffffffffffffff";
	private final String A="FFFFFFFF00000001000000000000000000000000FFFFFFFFFFFFFFFFFFFFFFFC";
	private final String B="5ac635d8aa3a93e7b3ebbd55769886bc651d06b0cc53b0f63bce3c3e27d2604b";
	private final String Gx="6b17d1f2e12c4247f8bce6e563a440f277037d812deb33a0f4a13945d898c296";
	private final String Gy="4fe342e2fe1a7f9b8ee7eb4a7c0f9e162bce33576b315ececbb6406837bf51f5";
	private final String N="FFFFFFFF00000000FFFFFFFFFFFFFFFFBCE6FAADA7179E84F3B9CAC2FC632551";
	private final String H="1";
	//从脚本扣出的密钥，无法完成签名验签，需要自己生成一对公司密钥
	//定义一组CI的ECC公私密钥（用于签名验签）
	//private final String Q_CI_ECDSA="043F157A6242EC54888EB50967BD84D1A885E51D66B2F6CD135354724C91D790EDB48B015F6B272DF50E49EAB2E1383BF0EEB0E9848543B971397D274D88E8EA77";
	//private final String D_CI_ECDSA="CCF97608A5081B8F478FBAB00CFE6F5A50B1C23C4B42E95EFFDDFB2DD1AD6676";
	//private  String D_CI_ECDSA;
	//private final String Qx_CI_ECDSA=Q_CI_ECDSA.substring(0, 64);
	//private final String Qy_CI_ECDSA=Q_CI_ECDSA.substring(64);
	//private final int Keylen=64;
	private final String share="108810";
	private String certificatePublicKey;//证书公钥
	private String certificatePrivateKey;//证书私钥
	private String ecdhePrivateKey;//ECDHE私钥
	
    public void clientHelloPaser(String hex) throws IOException{
    	InputStream recordIs = new ByteArrayInputStream(Util.hexStringToByteArray(hex));
        
        short type = TlsUtils.readUint8(recordIs);
        TlsUtils.checkVersion(recordIs);
        int size = TlsUtils.readUint16(recordIs);
        byte[] clientHello = new byte[size];
        TlsUtils.readFully(clientHello, recordIs); 

        this.hash1.reset();
        this.hash2.reset();
        this.hash1.update(clientHello, 0, size);
        this.hash2.update(clientHello, 0, size);

        this.hash256_1.reset();
        this.hash256_2.reset();
        this.hash256_1.update(clientHello, 0, size);
        this.hash256_2.update(clientHello, 0, size);

        this.hash1_1.reset();
        this.hash1_2.reset();
        this.hash1_1.update(clientHello, 0, size);
        this.hash1_2.update(clientHello, 0, size);

        byte[] buf = decodeAndVerify(type, clientHello, size, false);

        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        TlsUtils.readUint8(is);
        TlsUtils.readUint24(is);
        TlsUtils.checkVersion(is);

        this.clientRandom = new byte[32];
        TlsUtils.readFully(this.clientRandom, is); 
        //System.out.println("clientRandom >>" + Util.byteArrayToHexString(this.clientRandom, ""));      
      
        short sessionIdLength = TlsUtils.readUint8(is);
        byte[] sessionID = new byte[sessionIdLength];
        TlsUtils.readFully(sessionID, is);
        
        int cipherSuiteLength = TlsUtils.readUint16(is);
        this.cipherSuites = new byte[cipherSuiteLength];
        TlsUtils.readFully(this.cipherSuites, is);

        short compressionMethodLength = TlsUtils.readUint8(is);
        byte[] compressionMethod = new byte[compressionMethodLength];
        TlsUtils.readFully(compressionMethod, is);

        int extensionLen = TlsUtils.readUint16(is);
        int extensionType = TlsUtils.readUint16(is);
        int extensionDataLen = TlsUtils.readUint16(is);
        this.maxFragment = TlsUtils.readUint8(is);
		System.out.println(extensionLen+extensionType+extensionDataLen);
        System.out.println("1 >>clientHelloPaser finished");
	}

	public String serverHelloSend() throws IOException {
        this.serverRandom = new byte[32];
        this.random.nextBytes(this.serverRandom);
        TlsUtils.writeGMTUnixTime(this.serverRandom, 0);
        //System.out.println("serverRandom >>" + Util.byteArrayToHexString(this.serverRandom, ""));      
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TlsUtils.writeVersion(os, this.majorVersion, this.minorVersion);
        
        //this.serverRandom = Util.hexStringToByteArray("5555555555555555555555555555555555555555555555555555555555555555");
        //System.out.println("serverRandom >>" + Util.byteArrayToHexString(this.serverRandom, ""));       
        os.write(this.serverRandom);
        
/*        byte[] sessionID = new byte[32];
        for (int i = 0; i < sessionID.length; i++) {
          sessionID[i] = 0;
        }
        sessionID[31] = 1;
        TlsUtils.writeUint8((short)sessionID.length, os);
        os.write(sessionID);*/
		//sessionID为0
		TlsUtils.writeUint8((short) 0, os);
		
        ByteArrayInputStream isCipher = new ByteArrayInputStream(this.cipherSuites);
   
        int cipherSuitesNum = this.cipherSuites.length/2;
        System.out.println("cipherSuitesNum " + cipherSuitesNum);
       
        if (cipherSuitesNum == 1) {
            this.numberCipher = TlsUtils.readUint16(isCipher);
		} else {
	        //this.numberCipher = 174; 预置密钥模式TLS_PSK_WITH_AES_128_CBC_SHA256 = {0x00,0xAE}
			//CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256 = {0xC0,0x23};
			this.numberCipher = 49187;
		}

        this.chosenCipherSuite = TlsCipherSuiteManager.getCipherSuite(49187);
        TlsCipherSuiteManager.getCipherSuite(this.numberCipher);
        TlsCipherSuiteManager.getCipherSuiteName(this.numberCipher);
        
        //Cipher Suites:
        TlsUtils.writeUint16(this.numberCipher, os);
        
        //Compression Method:
        TlsUtils.writeUint8((short)0, os); 
		//Extension client_hello_extension_list
		//Extensions Length:
        TlsUtils.writeUint16(11, os);
        
		//Extension:  
        //Type: max_fragment_length(0x0001)
        TlsUtils.writeUint16(1, os);
        //Length:
        TlsUtils.writeUint16(1, os);
        TlsUtils.writeUint8(this.maxFragment, os);
        this.maxFragmentBytes = TlsCipherSuiteManager.getCipherSuiteMaxFragment(this.numberCipher, this.maxFragment);

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
		
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short)2, bos);
        TlsUtils.writeUint24(os.size(), bos);
        bos.write(os.toByteArray());
        byte[] message = bos.toByteArray();
        bos.close();

        System.out.println("2 >>serverHelloSend finished");
        return writeMessage((short)22, message, 0, message.length, false);
    }

	/**
	 * 服务器证书
	 * @return
	 * @throws IOException
	 */
	public String certificateSend() throws IOException {
		/*This message is used to authentically convey the server’s static
		public key to the client. The following table shows the server
		certificate type appropriate for each key exchange algorithm. ECC
		public keys MUST be encoded in certificates as described in
		Section 5.9  
		
		X.509 certificates containing ECC public keys or signed using ECDSA
		MUST comply with [14] or another RFC that replaces or extends it.
		Clients SHOULD use the elliptic curve domain parameters recommended
		in ANSI X9.62 [7], FIPS 186-2 [11], and SEC 2 [13]*/
		
		//目前我们没有CI，没有证书结构，暂时发送公私密钥对	
		//生成一对用来签名验签
		String keyPairs=ECCUtils.createECCKeyPair(P, A, B, Gx, Gy, N, H);
		certificatePrivateKey = keyPairs.substring(0, 64);//私钥
		certificatePublicKey = "04" + keyPairs.substring(64);
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
        TlsUtils.writeVersion(os, this.majorVersion, this.minorVersion);
   
        int publicKeyLen = certificatePublicKey.length()/2;
		TlsUtils.writeUint8((short) publicKeyLen, os);
		os.write(Util.hexStringToByteArray(certificatePublicKey));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short)11, bos);//Certificate(11)
        TlsUtils.writeUint24(os.size(), bos);
        bos.write(os.toByteArray());
        byte[] message = bos.toByteArray();
        bos.close();

        System.out.println("3 >>certificateSend finished");
        return writeMessage((short)22, message, 0, message.length, false);
	}


	/**
	 * 服务端生成 ECDH 临时公钥，同时回复 server_key_exchange，包含三部分重要内容：
		a)ECC 相关的参数。
		b)ECDH 临时公钥。
		c)ECC 参数和公钥生成的签名值，用于客户端校验。
	 * @return
	 * @throws IOException
	 */
	public String serverKeyExchangeSend() throws IOException {
		/*
		struct {
				ECParameters curve_params;
				ECPoint public;
		} ServerECDHParams;
		
		select (SignatureAlgorithm) {
				case ecdsa:
					digitally-signed struct {
					opaque sha_hash[sha_size];
				};
		} Signature;
		
		
		select (KeyExchangeAlgorithm) {
				case ec_diffie_hellman:
						ServerECDHParams params;
						Signature signed_params;
		} ServerKeyExchange;
		
		ServerKeyExchange.signed_params.sha_hash
		SHA(ClientHello.random + ServerHello.random + ServerKeyExchange.params)
		*/
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //Vesion: TLS 1.2 (0x0303)
        TlsUtils.writeVersion(os, this.majorVersion, this.minorVersion);
      
        //Length:
        //EC Diffie-Hellman Server Params:
        //Curve Type: named_curve(0x03)
        TlsUtils.writeUint8((short)3, os);
        //Name Curve: secp256r1(0x0017)指定使用的曲线名称
        TlsUtils.writeUint16(23, os);
        //Publickey Length:65
        TlsUtils.writeUint16(65, os);
        
        //PublicKey:
		String keyPairs=ECCUtils.createECCKeyPair(P, A, B, Gx, Gy, N, H);
		if(StringUtils.isBlank(keyPairs)){
			throw new RuntimeException("生成密钥对算法出错");
		}
		
		ecdhePrivateKey=keyPairs.substring(0, 64);
		System.out.println("ecdhePrivateKey >>"+ ecdhePrivateKey);//临时私钥	
		String ecdhePublicKey="04"+keyPairs.substring(64);//临时公钥
		System.out.println("ecdhePublicKey >>"+ ecdhePublicKey);      
		
		os.write(Util.hexStringToByteArray(ecdhePublicKey));//公钥	
		TlsUtils.writeUint16(64, os);//签名数据长度为64个字节	
		
		StringBuilder data_sign = new StringBuilder().append(Util.byteArrayToHexString(this.clientRandom, ""))
				.append(Util.byteArrayToHexString(this.serverRandom, "")).append("0017").append(ecdhePublicKey);	
		System.out.println("data_sign>>"+data_sign.toString());   
		
		String data_sign_res=ECCUtils.eccECKASign(P, A, B, Gx, Gy, N, H, data_sign.toString(), certificatePrivateKey);
		System.out.println("data_sign_res>>"+data_sign_res);   
		
        os.write(Util.hexStringToByteArray(data_sign_res));//签名数据
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short)12, bos);//(12)
        TlsUtils.writeUint24(os.size(), bos);//先把长度计算出来，拼装在字符串前面，LV结构
        bos.write(os.toByteArray()); 
        byte[] message = bos.toByteArray();
        bos.close();

        System.out.println("4 >>serverKeyExchangeSend finished");
        return writeMessage((short)22, message, 0, message.length, false);
	}
	
    public String serverHelloDoneSend() throws IOException
    {
    	byte[] serverHelloDone = { 14 ,00 ,00 ,00 };
    	System.out.println("5 >>serverHelloDoneSend finished");
    	return writeMessage((short)22, serverHelloDone, 0, serverHelloDone.length, false);
    }

/*    public void clientKeyExchangePaser(String hex) throws IOException {  
		String vlauePskID = getPskKey(FileName_pskKeyIni, pskKeyIni_PSKID,"0140");
		String vlauePskKey = getPskKey(FileName_pskKeyIni,pskKeyIni_PSKKEY, "0140");
		
		aPsk = new PSK();
		aPsk.setId(Util.hexStringToByteArray(vlauePskID));
		aPsk.setKeyValue(Util.hexStringToByteArray(vlauePskKey));
		
        InputStream recordIs = new ByteArrayInputStream(Util.hexStringToByteArray(hex));
        short type = TlsUtils.readUint8(recordIs);
        TlsUtils.checkVersion(recordIs, this.majorVersion, this.minorVersion);
        int size = TlsUtils.readUint16(recordIs); //00 1A
        byte[] clientKeyExchange = new byte[size];
        TlsUtils.readFully(clientKeyExchange, recordIs);
        System.out.println("clientKeyExchange fragmentData >>" + Util.byteArrayToHexString(clientKeyExchange, ""));
        
        this.hash1.update(clientKeyExchange, 0, size);
        this.hash2.update(clientKeyExchange, 0, size);
  
        this.hash256_1.update(clientKeyExchange, 0, size);
        this.hash256_2.update(clientKeyExchange, 0, size);
    
        this.hash1_1.update(clientKeyExchange, 0, size);
        this.hash1_2.update(clientKeyExchange, 0, size);
       
        byte[] buf = decodeAndVerify(type, clientKeyExchange, size, false);
        
        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        TlsUtils.readUint8(is);
        TlsUtils.readUint24(is);

        int pskIdentityLength = TlsUtils.readUint16(is);
        byte[] receivedPskIdentity = new byte[pskIdentityLength];
        TlsUtils.readFully(receivedPskIdentity, is);
        
        
        String pskId = Util.byteArrayToHexString(receivedPskIdentity, "");
        System.out.println("receivedPskIdentity >>> "+pskId);
        

//        EuiccScp81Mapper scp81Mapper = SpringContextHolder.getBean(EuiccScp81Mapper.class);
//        String pskKey = scp81Mapper.selectById(pskId);
//        byte[] psk = Util.hexStringToByteArray(pskKey);
        
 //       String pskKey = PskCache.getPsk(pskId);
        
//        System.out.println("pskKey >>> "+pskKey);
//        byte[] psk = Util.hexStringToByteArray(pskKey);
        
        byte[] psk = this.aPsk.getKeyValue();
        this.pms = TlsUtils.psk2pms(psk);   
        this.ms = new byte[48];
        byte[] random = new byte[this.clientRandom.length + this.serverRandom.length];
        System.arraycopy(this.clientRandom, 0, random, 0, this.clientRandom.length);
        System.arraycopy(this.serverRandom, 0, random, this.clientRandom.length, 
          this.serverRandom.length);
        

        TlsUtils.PRF_sha256(this.pms, TlsUtils.toByteArray("master secret"), random, ms);
   
        
        System.out.println("this.pms >>" + Util.byteArrayToHexString(this.pms,""));
        System.out.println("ms >>" + Util.byteArrayToHexString(ms,""));
        
        System.out.println("4 >>clientKeyExchangePaser finished");
      }*/
    
    public void clientKeyExchangePaser(String hex) throws IOException {  
		System.out.println("clientKeyExchangePaser   >> " + hex);
        InputStream recordIs = new ByteArrayInputStream(Util.hexStringToByteArray(hex));
        short type = TlsUtils.readUint8(recordIs);
        TlsUtils.checkVersion(recordIs, this.majorVersion, this.minorVersion);
        int size = TlsUtils.readUint16(recordIs); //00 1A
        byte[] clientKeyExchange = new byte[size];
        TlsUtils.readFully(clientKeyExchange, recordIs);
        System.out.println("clientKeyExchange fragmentData >>" + Util.byteArrayToHexString(clientKeyExchange, ""));
        
        this.hash1.update(clientKeyExchange, 0, size);
        this.hash2.update(clientKeyExchange, 0, size);
  
        this.hash256_1.update(clientKeyExchange, 0, size);
        this.hash256_2.update(clientKeyExchange, 0, size);
    
        this.hash1_1.update(clientKeyExchange, 0, size);
        this.hash1_2.update(clientKeyExchange, 0, size);
       
        byte[] buf = decodeAndVerify(type, clientKeyExchange, size, false);
        
        ByteArrayInputStream is = new ByteArrayInputStream(buf);
        TlsUtils.readUint8(is);
        TlsUtils.readUint24(is);
        TlsUtils.checkVersion(is);
        
        int publicKeyLen = TlsUtils.readUint16(is);
        byte[] receivedPKey = new byte[publicKeyLen];
        TlsUtils.readFully(receivedPKey, is);
        
        String clientECDHEPublicKey = Util.byteArrayToHexString(receivedPKey,"");
        System.out.println("receivedPKey >>> "+ Util.byteArrayToHexString(receivedPKey,""));

        //开始协商密钥pms
        //协商会话密钥
     	int Keylen=64;
     	String eSK_DP = ecdhePrivateKey;
     	String Qx_ECASD_ECKA = clientECDHEPublicKey.substring(2, 66);
     	String Qy_ECASD_ECKA = clientECDHEPublicKey.substring(66);     	
     	
		String key=ECCUtils.eccKeyAgreement(P, A, B, Gx, Gy, N, H, eSK_DP, Qx_ECASD_ECKA, Qy_ECASD_ECKA, share, Keylen);
        this.pms = Util.hexStringToByteArray(key);
        this.ms = new byte[48];
        byte[] random = new byte[this.clientRandom.length + this.serverRandom.length];
        System.arraycopy(this.clientRandom, 0, random, 0, this.clientRandom.length);
        System.arraycopy(this.serverRandom, 0, random, this.clientRandom.length, 
          this.serverRandom.length);

        TlsUtils.PRF_sha256(this.pms, TlsUtils.toByteArray("master secret"), random, ms);
   
        
        System.out.println("this.pms >>" + Util.byteArrayToHexString(this.pms,""));
        System.out.println("ms >>" + Util.byteArrayToHexString(ms,""));
        
        System.out.println("6 >>clientKeyExchangePaser finished");
    }
    
    public void clientChangeCipherSpecPaser(String hex) throws IOException
    {	
    	System.out.println("clientChangeCipherSpecPaser " + hex);
    	this.readSuite = this.chosenCipherSuite;
    	this.readSuite.initServer(this.ms, this.clientRandom, this.serverRandom, 
    			this.majorVersion, this.minorVersion);
    	
        InputStream recordIs = new ByteArrayInputStream(Util.hexStringToByteArray(hex));
        short type = TlsUtils.readUint8(recordIs);
        if (type != 20) {
      	  System.out.println("ContentType check error");
      	  System.out.println("actual value=" + StringUtil.shortToHexWithoutHigh(type));

      	  System.out.println("expect value=" + StringUtil.shortToHexWithoutHigh((short)20));

          throw new RuntimeException("check error:actual value=" + 
            StringUtil.shortToHexWithoutHigh(type) + 
            "  ,expect value=" + 
            StringUtil.shortToHexWithoutHigh((short)20));
        }

        if (!TlsUtils.checkVersion(recordIs, this.majorVersion, this.minorVersion)) {
          return ;
        }

        int size = TlsUtils.readUint16(recordIs); 
        byte[] clientChangeCipherSpec = new byte[size];

        TlsUtils.readFully(clientChangeCipherSpec, recordIs);
        System.out.println("clientChangeCipherSpec fragmentData >>" + Util.byteArrayToHexString(clientChangeCipherSpec, ""));

        byte[] buf = decodeAndVerify(type, clientChangeCipherSpec, size, false);
        if (buf == null) {
          return ;
        }

        byte[] cmessage = new byte[1];
        cmessage[0] = 1;
      
        boolean flag = TlsUtils.compareByteArray(buf,cmessage);
        if (!flag) {
        	System.out.println("clientChangeCipherSpecPaser error");
        	return;
        }

        System.out.println("7 >>clientChangeCipherSpecPaser finished");
    }

    public void clientFinishedPaser(String hex) throws IOException
    {  
    	InputStream recordIs = new ByteArrayInputStream(Util.hexStringToByteArray(hex));
    	short type = TlsUtils.readUint8(recordIs);
    	if (type != 22) {
    		System.out.println("ContentType check error");
    		System.out.println("actual value=" + StringUtil.shortToHexWithoutHigh(type));

    		System.out.println("expect value=" + StringUtil.shortToHexWithoutHigh((short)22));

    		throw new RuntimeException("check error:actual value=" + 
    						StringUtil.shortToHexWithoutHigh(type) + 
    						"  ,expect value=" + 
    						StringUtil.shortToHexWithoutHigh((short)22));
    	}

    	if (!TlsUtils.checkVersion(recordIs, this.majorVersion, this.minorVersion)) {
    		return ;
    	}

    	int size = TlsUtils.readUint16(recordIs);
    	byte[] clientFinished = new byte[size];

    	TlsUtils.readFully(clientFinished, recordIs);
    	
    	byte[] buf = decodeAndVerify(type, clientFinished, size, true);
    	if (buf == null) {
    		return ;
    	}
      
    	this.hash2.update(buf, 0, 16);
    	this.hash256_2.update(buf, 0, 16);
    	this.hash1_2.update(buf, 0, 16);

    	ByteArrayInputStream is = new ByteArrayInputStream(buf);

    	type = TlsUtils.readUint8(is);
    	if (type != 20) {
    		System.out.println("HandshakeType check error");
    		System.out.println("actual value=" + StringUtil.shortToHexWithoutHigh(type));
    		System.out.println("expect value=" + StringUtil.shortToHexWithoutHigh((short)20));
    	  
    		throw new RuntimeException("check error:actual value=" + 
    						StringUtil.shortToHexWithoutHigh(type) + 
    						"  ,expect value=" + 
    						StringUtil.shortToHexWithoutHigh((short)20));
    	}

    	int verifyData = TlsUtils.readUint24(is);
    	if (verifyData != 12) {
    		System.out.println("verifyData length check error");
    		System.out.println("actual length=" + StringUtil.intToHexWithHigh(verifyData));
    		System.out.println("expect value=0x0C");
    	  
    		throw new RuntimeException("check error:actual length=" + 
    				StringUtil.intToHexWithHigh(verifyData) + "  ,expect value=" + "0x0C");
    	}

    	byte[] receivedChecksum = new byte[12];
    	TlsUtils.readFully(receivedChecksum, is);

    	byte[] checksum = new byte[12];
    	boolean re = false;

    	if (this.minorVersion == 3)
    	{
    		if ((this.numberCipher == 174) || 
    				(this.numberCipher == 176) || (this.numberCipher == 49187))
    		{
    			byte[] sha256 = new byte[32];
    			this.hash256_1.doFinal(sha256, 0);
  
    			System.out.println("************");
    			System.out.println("clientFinished sha256 >>" + Util.byteArrayToHexString(sha256, ""));
    			System.out.println("************");
    			TlsUtils.PRF_hash(this.ms, TlsUtils.toByteArray("client finished"), sha256, 
    					checksum, new SHA256Digest());
    		} else {
    			byte[] sha1 = new byte[20];
    			this.hash1_1.doFinal(sha1, 0);
    			TlsUtils.PRF_hash(this.ms, TlsUtils.toByteArray("client finished"), sha1,
    					checksum, new SHA1Digest());
    		}

    		re = TlsUtils.compareByteArray(receivedChecksum, checksum);
    	}
    	else {
    		byte[] md5andsha1 = new byte[36];
    		this.hash1.doFinal(md5andsha1, 0);
    		TlsUtils.PRF(this.ms, TlsUtils.toByteArray("client finished"), 
    					md5andsha1, checksum);

    		re = TlsUtils.compareByteArray(receivedChecksum, checksum);
    	}

    	if (!re) {
    		System.out.println("Finished的verify data不正确");
    		System.out.println("verify data actual value=" + Util.byteArrayToHexString(receivedChecksum, " "));
    		System.out.println("verify data expect value=" + Util.byteArrayToHexString(checksum, " "));

    		throw new RuntimeException("check error:actual value=" + 
    				Util.byteArrayToHexString(receivedChecksum, " ") + 
    				"  ,expect value=" + 
    				Util.byteArrayToHexString(checksum, " "));
    	}

    	System.out.println("8 >>clientFinishedPaser finished");
    }

    public String serverChangeCipherSpecSend() throws IOException
    {
    	byte[] cmessage = new byte[1];
    	cmessage[0] = 1;

    	System.out.println("9 >>serverChangeCipherSpecSend finished");
    	return  writeMessage((short)20, cmessage, 0, cmessage.length, false);
    }
   
	public String serverFinishedSend() throws IOException {	
		this.writeSuite = this.readSuite;
		byte[] checksumServer = new byte[12];
		
		if (this.minorVersion == 3)
		{
			if ((this.numberCipher == 174) || 
					(this.numberCipher == 176)) {
				byte[] sha256 = new byte[32];
				this.hash256_2.doFinal(sha256, 0);
				TlsUtils.PRF_hash(this.ms, TlsUtils.toByteArray("server finished"), sha256,  
						checksumServer, new SHA256Digest());
			} else {
				byte[] sha1 = new byte[20];
				this.hash1_2.doFinal(sha1, 0);
				TlsUtils.PRF_hash(this.ms, TlsUtils.toByteArray("server finished"), sha1, 
						checksumServer, new SHA1Digest());
			}
		}
		else {
			byte[] md5andsha1Server = new byte[36];
			this.hash2.doFinal(md5andsha1Server, 0);
			TlsUtils.PRF(this.ms, TlsUtils.toByteArray("server finished"), 
					md5andsha1Server, checksumServer);
		}
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		TlsUtils.writeUint8((short)20, bos);
		TlsUtils.writeUint24(12, bos);
		bos.write(checksumServer);
		byte[] message = bos.toByteArray();
		
		System.out.println("10 >>serverFinishedSend finished");
		return writeMessage((short)22, message, 0, message.length, true);
	}
   
    public void postRequestPaser(String hex) throws IOException
    {
    	System.out.println("postRequestPaser >>" + hex);
    	decryptApplicationData(Util.hexStringToByteArray(hex));
    }

	public String httpPostResponse () throws IOException {  
		String data="HTTP/1.1 200 OK \r\n"+
        "X-admin-protocol: globalplatform-remote-admin/1.0 \r\n"+
        "X-Admin-Next-URI: /server/adminagent?cmd=2 \r\n"+
        "content-type: application/vnd.globalplatform.card-content-mgt;version=1.0 \r\n"+
        "content-length: 26 \r\n"+
        "\r\n";
			
		data=Util.byteArrayToHexString(Util.toByteArray(data), "");
		
		data += "AE80"+ nonSegRAM1 + "0000";
		System.out.println("data " + data);
      
		byte dataByte[] = Util.hexStringToByteArray(data);
		return encryptApplicationData(dataByte ,0, dataByte.length);
		
	}

	public String httpPostResponseFinal () throws IOException {  
		String data="HTTP/1.1 204 No Content \r\n"+
        "X-Admin-Protocol: globalplatform-remote-admin/1.0 \r\n"+
        "\r\n";
			
		data=Util.byteArrayToHexString(Util.toByteArray(data), "");
		System.out.println("data " + data);
      
		byte dataByte[] = Util.hexStringToByteArray(data);
		return encryptApplicationData(dataByte ,0, dataByte.length);	
	}
	
	public void postRequestFinal (String hex) throws IOException {
    	System.out.println("postRequestFinal >>" + hex);
    	decryptApplicationData(Util.hexStringToByteArray(hex));	
	}

    public String encryptApplicationData(byte[] message, int offset, int len) throws IOException
    {
    	System.out.println("***********encryptApplicationData***********");
	    short type = 23;    
	    byte[] ciphertext = this.writeSuite.encodePlaintext(type, message, offset, 
	    len, 1);
	    byte[] writeMessage = new byte[ciphertext.length + 5];
	    TlsUtils.writeUint8(type, writeMessage, 0);
	    TlsUtils.writeUint8(majorVersion, writeMessage, 1);
	    TlsUtils.writeUint8(minorVersion, writeMessage, 2);
	    TlsUtils.writeUint16(ciphertext.length, writeMessage, 3);
	    System.arraycopy(ciphertext, 0, writeMessage, 5, ciphertext.length);
	    System.out.println("encryptApplicationData >> " + Util.byteArrayToHexString(writeMessage, ""));
	    System.out.println("***********encryptApplicationData finished***********");
	    return Util.byteArrayToHexString(writeMessage, ""); 
    }
    
	public String decryptApplicationData(byte[] Buf) throws IOException
	{
		System.out.println("decryptApplicationData");

		InputStream recordIs = new ByteArrayInputStream(Buf);
		short type = TlsUtils.readUint8(recordIs);
		if (((type != 23 ? 1 : 0) & (type != 21 ? 1 : 0)) != 0) {
			System.out.println("ContentType check error");
			System.out.println("actual type value=" + type);
			System.out.println("expect type value=23");
			return null;
		}
		TlsUtils.checkVersion(recordIs, majorVersion, minorVersion);
		int size = TlsUtils.readUint16(recordIs);
		if (size > maxFragmentBytes) {
			System.out.println("MaxFragment check error");
			System.out.println("actual type value=" + size);
			System.out.println("expect type value=" + maxFragmentBytes);
			return null;
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
			return strData;
		}
		strData = strData.substring(endOfDbl0D0A+4, strData.length()-4);
		System.out.println("apdu >> " + strData);
		
		System.out.println("decryptApplicationData finished");
		return strData;
	}

    public  byte[] decodeAndVerify(short type, byte[] buf, int len, boolean flag)  throws  IOException{
    	if (flag) {
        	byte[] result = this.readSuite.decodeCiphertext(type, buf, 0, buf.length);
        	return result;	
		} 
			return buf;
    }
    
    public String writeMessage(short type, byte[] message, int offset, int len, boolean flag) throws IOException
    {
    	if (type == 22)
    	{
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

    	System.out.println("message  >>" + Util.byteArrayToHexString(message, ""));
    	
        byte[] ciphertext; 
        if (flag) {
            ciphertext = this.writeSuite.encodePlaintext(type, message, offset, len, 1);  
        } else {
        	ciphertext = message;
        }
        
        byte[] writeMessage = new byte[ciphertext.length + 5];
        TlsUtils.writeUint8(type, writeMessage, 0);
        TlsUtils.writeUint8(this.majorVersion, writeMessage, 1);
        TlsUtils.writeUint8(this.minorVersion, writeMessage, 2);
        TlsUtils.writeUint16(ciphertext.length, writeMessage, 3);
        System.arraycopy(ciphertext, 0, writeMessage, 5, ciphertext.length);

        return Util.byteArrayToHexString(writeMessage, "");
    }
    
	public  String getPskKey(String path, String section, String key) {
		String value = null;
		try {
			InputStream isInputStream = STlsHankUtils.class.getClassLoader().getResourceAsStream("pskKey.ini");
			IniFile iniFile = new IniFile();
			iniFile.load(isInputStream);
			value = iniFile.getKeyValue(section, key);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		return value;
	}
}

