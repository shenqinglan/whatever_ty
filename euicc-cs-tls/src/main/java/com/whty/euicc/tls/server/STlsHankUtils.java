package com.whty.euicc.tls.server;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;

import wq.app.crypto.Digest;
import wq.app.crypto.digests.SHA1Digest;
import wq.app.crypto.digests.SHA256Digest;

import com.telecom.http.tls.CombinedHash;
import com.telecom.http.tls.TlsUtils;
import com.telecom.http.tls.test.IniFile;
import com.telecom.http.tls.test.PSK;
import com.telecom.http.tls.test.Util;
import com.watchdata.framework.util.StringUtil;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.data.cache.PskCache;
import com.whty.euicc.tls.SocketUtils;


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
        
        System.out.println("clientRandom >>" + Util.byteArrayToHexString(this.clientRandom, ""));      
      
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
 
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        TlsUtils.writeVersion(os, this.majorVersion, this.minorVersion);
        this.serverRandom = Util.hexStringToByteArray("5555555555555555555555555555555555555555555555555555555555555555");
        System.out.println("serverRandom >>" + Util.byteArrayToHexString(this.serverRandom, ""));     
        os.write(this.serverRandom);
        
        byte[] sessionID = new byte[32];
        for (int i = 0; i < sessionID.length; i++) {
          sessionID[i] = 0;
        }
        sessionID[31] = 1;
        TlsUtils.writeUint8((short)sessionID.length, os);
        os.write(sessionID);
        
        ByteArrayInputStream isCipher = new ByteArrayInputStream(this.cipherSuites);
   
        int cipherSuitesNum = this.cipherSuites.length/2;
        System.out.println("cipherSuitesNum " + cipherSuitesNum);
        
        if (cipherSuitesNum == 1) {
            this.numberCipher = TlsUtils.readUint16(isCipher);
		} else {
	        this.numberCipher = 174;
		}

        this.chosenCipherSuite = TlsCipherSuiteManager.getCipherSuite(this.numberCipher);
        TlsCipherSuiteManager.getCipherSuiteName(this.numberCipher);
        
        TlsUtils.writeUint16(this.numberCipher, os);
        
        TlsUtils.writeUint8((short)0, os); 
        TlsUtils.writeUint16(5, os);
        TlsUtils.writeUint16(1, os);
        TlsUtils.writeUint16(1, os);
        TlsUtils.writeUint8(this.maxFragment, os);
        this.maxFragmentBytes = TlsCipherSuiteManager.getCipherSuiteMaxFragment(this.numberCipher, this.maxFragment);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short)2, bos);
        TlsUtils.writeUint24(os.size(), bos);
        bos.write(os.toByteArray());
        byte[] message = bos.toByteArray();
        bos.close();

        System.out.println("2 >>serverHelloSend finished");
        return writeMessage((short)22, message, 0, message.length, false);
    }

    public String serverHelloDoneSend() throws IOException
    {
    	byte[] serverHelloDone = { 14 ,00 ,00 ,00 };
    	System.out.println("3 >>serverHelloDoneSend finished");
    	return writeMessage((short)22, serverHelloDone, 0, serverHelloDone.length, false);
    }

    public void clientKeyExchangePaser(String hex) throws IOException {  
		/*String valuePskID = getPskKey(FileName_pskKeyIni, pskKeyIni_PSKID,"0140");
		String valuePskKey = getPskKey(FileName_pskKeyIni,pskKeyIni_PSKKEY, "0140");
		aPsk = new PSK();
		aPsk.setId(Util.hexStringToByteArray(valuePskID));
		aPsk.setKeyValue(Util.hexStringToByteArray(valuePskKey));*/
		
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
        
//      byte[] psk = this.aPsk.getKeyValue();
//        EuiccScp81Mapper scp81Mapper = SpringContextHolder.getBean(EuiccScp81Mapper.class);
//        String pskKey = scp81Mapper.selectById(pskId);
        String pskKey = PskCache.getPsk(pskId);
        byte[] psk = Util.hexStringToByteArray(pskKey);
        System.out.println("pskKey >>> "+pskKey);
        this.pms = TlsUtils.psk2pms(psk);   
        this.ms = new byte[48];
        byte[] random = new byte[this.clientRandom.length + this.serverRandom.length];
        System.arraycopy(this.clientRandom, 0, random, 0, this.clientRandom.length);
        System.arraycopy(this.serverRandom, 0, random, this.clientRandom.length, 
          this.serverRandom.length);
                
        TlsUtils.PRF_sha256(this.pms, TlsUtils.toByteArray("master secret"), random, ms);
   
        System.out.println("4 >>clientKeyExchangePaser finished");
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

          /*throw new RuntimeException("check error:actual value=" + 
            StringUtil.shortToHexWithoutHigh(type) + 
            "  ,expect value=" + 
            StringUtil.shortToHexWithoutHigh((short)20));*/
          throw new EuiccBusiException("0008", "非changeCipherSpec包");
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

        System.out.println("5 >>clientChangeCipherSpecPaser finished");
    }

    public void clientFinishedPaser(String hex) throws IOException
    {  
    	InputStream recordIs = new ByteArrayInputStream(Util.hexStringToByteArray(hex));
    	short type = TlsUtils.readUint8(recordIs);
    	if (type != 22) {
    		System.out.println("ContentType check error");
    		System.out.println("actual value=" + StringUtil.shortToHexWithoutHigh(type));

    		System.out.println("expect value=" + StringUtil.shortToHexWithoutHigh((short)22));

    		/*throw new RuntimeException("check error:actual value=" + 
    						StringUtil.shortToHexWithoutHigh(type) + 
    						"  ,expect value=" + 
    						StringUtil.shortToHexWithoutHigh((short)22));*/
    		throw new EuiccBusiException("0008", "非handshake包");
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
    	  
    		/*throw new RuntimeException("check error:actual value=" + 
    						StringUtil.shortToHexWithoutHigh(type) + 
    						"  ,expect value=" + 
    						StringUtil.shortToHexWithoutHigh((short)20));*/
    		throw new EuiccBusiException("0008", "非handshake包");
    	}

    	int verifyData = TlsUtils.readUint24(is);
    	if (verifyData != 12) {
    		System.out.println("verifyData length check error");
    		System.out.println("actual length=" + StringUtil.intToHexWithHigh(verifyData));
    		System.out.println("expect value=0x0C");
    	  
    		/*throw new RuntimeException("check error:actual length=" + 
    				StringUtil.intToHexWithHigh(verifyData) + "  ,expect value=" + "0x0C");*/
    		throw new EuiccBusiException("0008", "clientFinishedPaser的verify data长度不正确");
    	}

    	byte[] receivedChecksum = new byte[12];
    	TlsUtils.readFully(receivedChecksum, is);

    	byte[] checksum = new byte[12];
    	boolean re = false;

    	if (this.minorVersion == 3)
    	{
    		if ((this.numberCipher == 174) || 
    				(this.numberCipher == 176))
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

    		/*throw new RuntimeException("check error:actual value=" + 
    				Util.byteArrayToHexString(receivedChecksum, " ") + 
    				"  ,expect value=" + 
    				Util.byteArrayToHexString(checksum, " "));*/
    		throw new EuiccBusiException("0008", "clientFinishedPaser的verify data不正确");
    	}

    	System.out.println("6 >>clientFinishedPaser finished");
    }

    public String serverChangeCipherSpecSend() throws IOException
    {
    	byte[] cmessage = new byte[1];
    	cmessage[0] = 1;

    	System.out.println("7 >>serverChangeCipherSpecSend finished");
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
		
		System.out.println("8 >>serverFinishedSend finished");
		return writeMessage((short)22, message, 0, message.length, true);
	}
   
    public String postRequestPaser(String hex) throws IOException
    {
    	System.out.println("postRequestPaser >>" + hex);
    	return decryptApplicationDataByEuicc(Util.hexStringToByteArray(hex));
    }
    
    public String postRequestPaserByRsp(byte[] str) throws IOException
    {
    	return new String(decryptApplicationData(str));
    }
    
	public byte[] httpPostResponseByRsp (byte[] dataByte) throws IOException {  
		return encryptApplicationData(dataByte ,0, dataByte.length);
	}
	
	public String httpPostResponse (byte[] dataByte) throws IOException {  
		return encryptApplicationDataByEuicc(dataByte ,0, dataByte.length);
	}
	
	public void postRequestFinal (String hex) throws IOException {
    	System.out.println("postRequestFinal >>" + hex);
    	decryptApplicationData(Util.hexStringToByteArray(hex));	
	}

	public byte[] encryptApplicationData(byte[] message, int offset, int len) throws IOException
    {
    	System.out.println("***********encryptApplicationData***********");
	    short type = 23;    
	    byte[] ciphertext = this.writeSuite.encodePlaintext(type, message, offset, 
	    len,1);
	    byte[] writeMessage = new byte[ciphertext.length + 5];
	    TlsUtils.writeUint8(type, writeMessage, 0);
	    TlsUtils.writeUint8(majorVersion, writeMessage, 1);
	    TlsUtils.writeUint8(minorVersion, writeMessage, 2);
	    TlsUtils.writeUint16(ciphertext.length, writeMessage, 3);
		System.arraycopy(ciphertext, 0, writeMessage, 5, ciphertext.length);
	    return writeMessage;
    }
    
    public String encryptApplicationDataByEuicc(byte[] message, int offset, int len) throws IOException
    {
	    byte[] writeMessage = encryptApplicationData(message,offset,len);
	    return Util.byteArrayToHexString(writeMessage, ""); 
    }
    
    public byte[] decryptApplicationData(byte[] Buf) throws IOException
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
		return buffer;
		
	}
	
	public String decryptApplicationDataByEuicc(byte[] Buf) throws IOException
	{
		
		byte[] buffer = decryptApplicationData(Buf);
		System.out.println("buf >>" + Util.byteArrayToHexString(buffer, ""));
		
		String data = Util.byteArrayToHexString(buffer, "");
		//String strData = data;
		int endOfDbl0D0A=data.indexOf("0D0A0D0A")+"0D0A0D0A".length();
		return  Util.hexToASCII(data.substring(0, endOfDbl0D0A)) + data.substring(endOfDbl0D0A);
		
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

        byte[] ciphertext; 
        if (flag) {
            ciphertext = this.writeSuite.encodePlaintext(type, message, offset, len,1);  
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


