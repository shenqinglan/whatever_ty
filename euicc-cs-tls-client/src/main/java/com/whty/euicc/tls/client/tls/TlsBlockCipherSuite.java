package com.whty.euicc.tls.client.tls;
           
import com.whty.euicc.tls.client.crypto.BlockCipher;
import com.whty.euicc.tls.client.crypto.Digest;
import com.whty.euicc.tls.client.crypto.digests.SHA1Digest;
import com.whty.euicc.tls.client.crypto.digests.SHA256Digest;
import com.whty.euicc.tls.client.crypto.macs.HMac;
import com.whty.euicc.tls.client.crypto.params.KeyParameter;
import com.whty.euicc.tls.client.crypto.params.ParametersWithIV;
import com.whty.euicc.tls.client.tls.TlsFatalAlert;
import java.io.ByteArrayInputStream;
import java.io.IOException;

           
public class TlsBlockCipherSuite extends TlsCipherSuite
{
	private BlockCipher encryptCipher;
	private BlockCipher decryptCipher;
	private Digest writeDigest;
	private Digest readDigest;
	private int cipherKeySize;
	private short keyExchange;
	private TlsMac writeMac;
	private TlsMac readMac;
	private KeyParameter key_parameterEn;
	private KeyParameter key_parameterDe;
	private short majorVer;
	private short minorVer;
	private String SHA256_AlgorithmName = "SHA-256";
	
	private String SHA1_AlgorithmName = "SHA-1";
	
	private byte[] ClientWriteIV;
	
	private byte[] ServerWriteIV;
	
	
	public TlsBlockCipherSuite(){
	}
	
	public TlsBlockCipherSuite(BlockCipher encrypt, BlockCipher decrypt, Digest writeDigest, Digest readDigest, int cipherKeySize, short keyExchange)
	{
		encryptCipher = encrypt;
		decryptCipher = decrypt;
		this.writeDigest = writeDigest;
		this.readDigest = readDigest;
		this.cipherKeySize = cipherKeySize;
		this.keyExchange = keyExchange;
	}
	         
	public TlsBlockCipherSuite(Digest writeDigest, Digest readDigest, short keyExchange)
	{
	this.writeDigest = writeDigest;
	this.readDigest = readDigest;
	this.keyExchange = keyExchange;
	}
	         
	public void init(byte[] ms, byte[] cr, byte[] sr, short majorVersion, short minorVersion)
	{
		majorVer = majorVersion;
		minorVer = minorVersion;
		int prfSize = 0;
		if (encryptCipher != null) {
		  prfSize = 2 * cipherKeySize + 2 * writeDigest.getDigestSize() + 
		    2 * encryptCipher.getBlockSize();
		          } else {
		  prfSize = 0 + 2 * writeDigest.getDigestSize() + 0;
		          }
		          
		byte[] key_block = new byte[prfSize];
		byte[] random = new byte[cr.length + sr.length];
		System.arraycopy(cr, 0, random, sr.length, cr.length);
		System.arraycopy(sr, 0, random, 0, sr.length);
		if (minorVer == 3) {
			System.out.println("Version is tls1.2");
			if (writeDigest.getAlgorithmName().equals(SHA256_AlgorithmName)) {
		  	System.out.println("DigestAlgorithmName=" + SHA256_AlgorithmName);
				TlsUtils.PRF_hash(ms, TlsUtils.toByteArray("key expansion"), random, 
		    	key_block, new SHA256Digest());
		  } else {
		  	System.out.println("DigestAlgorithmName=" + SHA1_AlgorithmName);
		  	TlsUtils.PRF_hash(ms, TlsUtils.toByteArray("key expansion"), random, 
		    key_block, new SHA1Digest());
		  }
		}
		else {
			System.out.println("Version is tls1.1 or　tls1.0");
			System.out.println("DigestAlgorithmName=MD5 and SHA1 combination");
			TlsUtils.PRF(ms, TlsUtils.toByteArray("key expansion"), random, 
	     key_block);
		}
	           
		System.out.println("TY ms=" + Util.byteArrayToHexString(ms, " "));
		System.out.println("TY cr=" + Util.byteArrayToHexString(cr, " "));
		System.out.println("TY sr=" + Util.byteArrayToHexString(sr, " "));
		System.out.println("TY keyblock=" + Util.byteArrayToHexString(key_block, " "));
		int offset = 0;
		          
		      
		writeMac = new TlsMac(writeDigest, key_block, offset, writeDigest
		  .getDigestSize(), majorVersion, minorVersion);
		          
		byte[] writeMacByte = new byte[writeDigest.getDigestSize()];
		System.arraycopy(key_block, 0, writeMacByte, 0, writeMacByte.length);
		System.out.println("TY writeMacBytelen=" + StringUtil.shortToHexWithoutHigh((short)writeDigest.getDigestSize()));
		System.out.println("TY writeMacByte   =" + Util.byteArrayToHexString(writeMacByte, " "));
		          
		      
		offset += writeDigest.getDigestSize();
		readMac = new TlsMac(readDigest, key_block, offset, readDigest
		  .getDigestSize(), majorVersion, minorVersion);
		          
		byte[] readMacByte = new byte[writeDigest.getDigestSize()];
		System.arraycopy(key_block, offset, readMacByte, 0, writeMacByte.length);
		System.out.println("TY readMacBytelen=" + StringUtil.shortToHexWithoutHigh((short)writeDigest.getDigestSize()));
		System.out.println("TY readMacByte   =" + Util.byteArrayToHexString(readMacByte, " "));
		 
		offset += readDigest.getDigestSize();
		 
		if (encryptCipher == null) {
		  return;
		 }
		 
		initCipher(true, encryptCipher, key_block, cipherKeySize, offset, 
		  offset + cipherKeySize * 2);
		 
		byte[] encryptCipherByte = new byte[cipherKeySize];
		System.arraycopy(key_block, offset, encryptCipherByte, 0, cipherKeySize);
		System.out.println("TY encryptCipherBytelen=" + StringUtil.shortToHexWithoutHigh((short)cipherKeySize));
		System.out.println("TY encryptCipherByte   =" + Util.byteArrayToHexString(encryptCipherByte, " "));
		 
		
		offset += cipherKeySize;
		initCipher(false, decryptCipher, key_block, cipherKeySize, offset, 
		  offset + cipherKeySize + decryptCipher.getBlockSize());
		 
		byte[] decryptCipherByte = new byte[cipherKeySize];
		System.arraycopy(key_block, offset, decryptCipherByte, 0, cipherKeySize);
		System.out.println("TY decryptCipherBytelen=" + StringUtil.shortToHexWithoutHigh((short)cipherKeySize));
		System.out.println("TY decryptCipherByte   =" + Util.byteArrayToHexString(decryptCipherByte, " "));
	}
	                
	public void initServer(byte[] ms, byte[] cr, byte[] sr, short majorVersion, short minorVersion)
	{
		majorVer = majorVersion;
		minorVer = minorVersion;
		int prfSize = 0;
		if (encryptCipher != null) {
		  prfSize = 2 * cipherKeySize + 2 * writeDigest.getDigestSize() + 
		    2 * encryptCipher.getBlockSize();
		 } else {
		  prfSize = 0 + 2 * writeDigest.getDigestSize() + 0;
		 }
		 
		byte[] key_block = new byte[prfSize];
		byte[] random = new byte[cr.length + sr.length];
		System.arraycopy(cr, 0, random, sr.length, cr.length);
		System.arraycopy(sr, 0, random, 0, sr.length);
	 
		if (minorVer == 3) {
				System.out.println("Version is tls1.2");
				if (writeDigest.getAlgorithmName().equals(SHA256_AlgorithmName)) {
				  System.out.println("DigestAlgorithmName=" + SHA256_AlgorithmName);
				  TlsUtils.PRF_hash(ms, TlsUtils.toByteArray("key expansion"), random, 
				    key_block, new SHA256Digest());
				  System.out.println("Key Material Block:" + Util.byteArrayToHexString(key_block, " "));
				  
				  ByteArrayInputStream bais=new ByteArrayInputStream(key_block);
				  byte[] rand = new byte[cr.length + sr.length];
					System.arraycopy(cr, 0, rand, 0, cr.length);
					System.arraycopy(sr, 0, rand, cr.length,sr.length);
				  byte[] clientWriteMacSecret=null;
				  byte[] serverWriteMacSecret=null;
				  byte[] clientWriteKey=null;
				  byte[] serverWriteKey=null;
				  byte[] client_write_IV=null;
				  byte[] server_write_IV=null;
				  
				  clientWriteMacSecret=new byte[new HMac(writeDigest).getMacSize()];
					serverWriteMacSecret=new byte[new HMac(writeDigest).getMacSize()];
				  if(encryptCipher!=null){
				  	clientWriteKey=new byte[encryptCipher.getBlockSize()];
				      serverWriteKey=new byte[encryptCipher.getBlockSize()];
				      client_write_IV=new byte[encryptCipher.getBlockSize()];
				      server_write_IV=new byte[encryptCipher.getBlockSize()];
				  }
				  try {	
							TlsUtils.readFully(clientWriteMacSecret, bais);
							TlsUtils.readFully(serverWriteMacSecret, bais);
							if(encryptCipher!=null){
								TlsUtils.readFully(clientWriteKey, bais);
								TlsUtils.readFully(serverWriteKey, bais);
								TlsUtils.readFully(client_write_IV, bais);
								TlsUtils.readFully(server_write_IV, bais);
							}
							
							
							System.out.println("clientWriteMacSecret>>"+(Util.byteArrayToHexString(clientWriteMacSecret, "")));
							System.out.println("serverWriteMacSecret>>"+(Util.byteArrayToHexString(serverWriteMacSecret, "")));
							System.out.println("clientWriteKey>>"+(clientWriteKey!=null?Util.byteArrayToHexString(clientWriteKey, ""):""));
							System.out.println("serverWriteKey>>"+(serverWriteKey!=null?Util.byteArrayToHexString(serverWriteKey, ""):""));
							System.out.println("clientWriteIV>>"+(client_write_IV!=null?Util.byteArrayToHexString(client_write_IV, ""):""));
							System.out.println("serverWriteIV>>"+(server_write_IV!=null?Util.byteArrayToHexString(server_write_IV, ""):""));			
					} catch (IOException e) {
						e.printStackTrace();
						throw new RuntimeException();
					}
				 } else {
				  System.out.println("DigestAlgorithmName=" + SHA1_AlgorithmName);
				  TlsUtils.PRF_hash(ms, TlsUtils.toByteArray("key expansion"), random, 
				    key_block, new SHA1Digest());
				  System.out.println("Key Material Block:" + Util.byteArrayToHexString(key_block, " "));
				
				 }
		}		
	 	else {
			System.out.println("Version is tls1.1 or　tls1.0");
	  	System.out.println("DigestAlgorithmName=MD5 and SHA1 combination");
	  	TlsUtils.PRF(ms, TlsUtils.toByteArray("key expansion"), random, 
	    	key_block);
	  	System.out.println("Key Material Block:" + Util.byteArrayToHexString(key_block, " "));
   	}
               
		int offset = 0;
		
		
		readMac = new TlsMac(readDigest, key_block, offset, readDigest
		  .getDigestSize(), majorVersion, minorVersion);
		offset += readDigest.getDigestSize();
		writeMac = new TlsMac(writeDigest, key_block, offset, writeDigest
		  .getDigestSize(), majorVersion, minorVersion);
		offset += writeDigest.getDigestSize();
		 
		if (encryptCipher == null) {
		  return;
		 }
		 
		ClientWriteIV = new byte[decryptCipher.getBlockSize()];
		ServerWriteIV = new byte[encryptCipher.getBlockSize()];
		 
		initCipher(false, decryptCipher, key_block, cipherKeySize, offset, 
		  offset + cipherKeySize * 2);
		 
		System.arraycopy(key_block, offset + cipherKeySize * 2, ClientWriteIV, 0, (short)ClientWriteIV.length);
		 
		offset += cipherKeySize;
		initCipher(true, encryptCipher, key_block, cipherKeySize, offset, 
		  offset + cipherKeySize + decryptCipher.getBlockSize());
		 
		
		System.arraycopy(key_block, offset + cipherKeySize + decryptCipher.getBlockSize(), ServerWriteIV, 0, (short)ServerWriteIV.length);
	}

	private void initCipher(boolean forEncryption, BlockCipher cipher, byte[] key_block, int key_size, int key_offset, int iv_offset)
	{
		if (minorVer == 1) {
			KeyParameter key_parameter = new KeyParameter(key_block, key_offset, 
	     key_size);
	   	ParametersWithIV parameters_with_iv = new ParametersWithIV(
	     key_parameter, key_block, iv_offset, cipher.getBlockSize());
	   	cipher.init(forEncryption, parameters_with_iv);
	   	return;
	  }
	  
	
		if (forEncryption) {
			key_parameterEn = new KeyParameter(key_block, key_offset, 
				key_size);
		} else {
			key_parameterDe = new KeyParameter(key_block, key_offset, 
				key_size);
		}
	}
 
	public byte[] encodePlaintext(short type, byte[] plaintext, int offset, int len)
	{
		if (encryptCipher != null)
		{
			int blocksize = encryptCipher.getBlockSize();
			System.out.println("blocksize >>" + blocksize);
			int paddingsize = blocksize - 
			(len + writeMac.getSize() + 1) % blocksize;
			System.out.println("padding=" + StringUtil.shortToHexWithoutHigh((short)paddingsize));
		   
			int totalsize = len + writeMac.getSize() + paddingsize + 1;
			byte[] outbuf = new byte[totalsize];
			System.arraycopy(plaintext, offset, outbuf, 0, len);
			byte[] mac = writeMac.calculateMac(type, plaintext, offset, len);
			System.out.println("mac     =" + Util.byteArrayToHexString(mac, " "));
			System.arraycopy(mac, 0, outbuf, len, mac.length);
			int paddoffset = len + mac.length;
			for (int i = 0; i <= paddingsize; i++) {
				outbuf[(i + paddoffset)] = ((byte)paddingsize);
			}
			System.out.println("plaintext=" + Util.byteArrayToHexString(outbuf, " "));
	
			System.out.println("*******************");
		  
			if (minorVer == 1) {
				for (int i = 0; i < totalsize; i += blocksize) {
				  encryptCipher.processBlock(outbuf, i, outbuf, i);
				}
				return outbuf;
			}
        
			byte[] ivRandom = new byte[blocksize];
			SecureRandom random = new SecureRandom();
			random.nextBytes(ivRandom);
			
			ParametersWithIV parameters_with_iv = new ParametersWithIV(
					key_parameterEn, ivRandom, 0, blocksize);
    
			System.out.println("ivRandom >>" + Util.byteArrayToHexString( ivRandom, ""));
			System.out.println("iv >>" + Util.byteArrayToHexString( parameters_with_iv.getIV(), ""));
			encryptCipher.init(true, parameters_with_iv);
        
			for (int i = 0; i < totalsize; i += blocksize) {
				encryptCipher.processBlock(outbuf, i, outbuf, i);
			}
        
			byte[] enOutBuf = new byte[blocksize + totalsize];
			System.arraycopy(ivRandom, 0, enOutBuf, 0, blocksize);
			System.arraycopy(outbuf, 0, enOutBuf, blocksize, totalsize);
        
			return enOutBuf;
		}
		
		System.out.println("===============encodePlaintext无算法==============");
		byte[] outbuf = new byte[len + writeMac.getSize()];
		System.arraycopy(plaintext, offset, outbuf, 0, len);
		byte[] mac = writeMac.calculateMac(type, plaintext, offset, len);
		System.out.println("mac     =" + Util.byteArrayToHexString(mac, " "));
		System.arraycopy(mac, 0, outbuf, len, mac.length);
		return outbuf;
	}
	
	public byte[] encodePlaintext(short type, byte[] plaintext, int offset, int len ,int serverOrClient)
	{
		if (encryptCipher != null)
		{
			int blocksize = encryptCipher.getBlockSize();
			System.out.println("blocksize >>" + blocksize);
			int paddingsize = blocksize - 
			(len + writeMac.getSize() + 1) % blocksize;
			System.out.println("padding=" + StringUtil.shortToHexWithoutHigh((short)paddingsize));
		   
			int totalsize = len + writeMac.getSize() + paddingsize + 1;
			byte[] outbuf = new byte[totalsize];
			System.arraycopy(plaintext, offset, outbuf, 0, len);
			byte[] mac = writeMac.calculateMac(type, plaintext, offset, len);
			System.out.println("mac     =" + Util.byteArrayToHexString(mac, " "));
			System.arraycopy(mac, 0, outbuf, len, mac.length);
			int paddoffset = len + mac.length;
			for (int i = 0; i <= paddingsize; i++) {
				outbuf[(i + paddoffset)] = ((byte)paddingsize);
			}
			System.out.println("plaintext=" + Util.byteArrayToHexString(outbuf, " "));
	
			System.out.println("*******************");
		  
			if (minorVer == 1) {
				for (int i = 0; i < totalsize; i += blocksize) {
				  encryptCipher.processBlock(outbuf, i, outbuf, i);
				}
				return outbuf;
			}
        
			byte[] ivRandom = new byte[blocksize];
			SecureRandom random = new SecureRandom();
			random.nextBytes(ivRandom);
			
			//LIYJ
			String tmpRandom = "";
			if (serverOrClient == 0)
			{
			  tmpRandom = "000102030405060708090A0B0C0D0E0F";	
			} else if (serverOrClient == 1){
			  tmpRandom = "00010203040506070809AABBCCDDEEFF";
			}
			
			ivRandom = Util.hexStringToByteArray(tmpRandom);
			
			ParametersWithIV parameters_with_iv = new ParametersWithIV(
					key_parameterEn, ivRandom, 0, blocksize);
    
			System.out.println("ivRandom >>" + Util.byteArrayToHexString( ivRandom, ""));
			System.out.println("iv >>" + Util.byteArrayToHexString( parameters_with_iv.getIV(), ""));
			encryptCipher.init(true, parameters_with_iv);
        
			for (int i = 0; i < totalsize; i += blocksize) {
				encryptCipher.processBlock(outbuf, i, outbuf, i);
			}
        
			byte[] enOutBuf = new byte[blocksize + totalsize];
			System.arraycopy(ivRandom, 0, enOutBuf, 0, blocksize);
			System.arraycopy(outbuf, 0, enOutBuf, blocksize, totalsize);
        
			return enOutBuf;
		}
		
		System.out.println("===============encodePlaintext无算法==============");
		byte[] outbuf = new byte[len + writeMac.getSize()];
		System.arraycopy(plaintext, offset, outbuf, 0, len);
		byte[] mac = writeMac.calculateMac(type, plaintext, offset, len);
		System.out.println("mac     =" + Util.byteArrayToHexString(mac, " "));
		System.arraycopy(mac, 0, outbuf, len, mac.length);
		return outbuf;
	}
              
	public byte[] encodePlaintextError(short type, byte[] plaintext, int offset, int len, byte macError, int paddingError, byte encodeError, byte encodeLenError)
	{
		if (encryptCipher != null) {
			int blocksize = encryptCipher.getBlockSize();
			int paddingsize = blocksize - 
			  (len + writeMac.getSize() + 1) % blocksize;
			 
			int totalsize = len + writeMac.getSize() + paddingsize + 1;
			byte[] outbuf = new byte[totalsize];
			System.arraycopy(plaintext, offset, outbuf, 0, len);
			byte[] mac = writeMac.calculateMac(type, plaintext, offset, len);
			if (macError != 0) {
			  mac[0] = ((byte)(mac[0] ^ macError));
			  System.out.println("test error mac value=" + Util.byteArrayToHexString(mac, " "));
			 }
			System.arraycopy(mac, 0, outbuf, len, mac.length);
			int paddoffset = len + mac.length;
			int errorPaddingsize = 0;
			if (paddingError != -1) {
			  errorPaddingsize = paddingsize + paddingError;
			  System.out.println("test error paddingsize value=" + errorPaddingsize + " ");
			 }
			for (int i = 0; i <= paddingsize; i++) {
			  outbuf[(i + paddoffset)] = ((byte)errorPaddingsize);
			 }
			 
			
			if (minorVer != 3) {
			  for (int i = 0; i < totalsize; i += blocksize) {
			    encryptCipher.processBlock(outbuf, i, outbuf, i);
			   }
			  if (encodeError != 0) {
			    outbuf[0] = ((byte)(outbuf[0] ^ encodeError));
			    System.out.println("test error encrypt value=" + Util.byteArrayToHexString(outbuf, " "));
			   }
			  return outbuf;
			 }
			 
			
			byte[] ivRandom = new byte[blocksize];
			SecureRandom random = new SecureRandom();
			random.nextBytes(ivRandom);
			ParametersWithIV parameters_with_iv = new ParametersWithIV(
			  key_parameterEn, ivRandom, 0, blocksize);
			encryptCipher.init(true, parameters_with_iv);
			 
			for (int i = 0; i < totalsize; i += blocksize) {
			  encryptCipher.processBlock(outbuf, i, outbuf, i);
			 }
			if (encodeError != 0) {
			  outbuf[0] = ((byte)(outbuf[0] ^ encodeError));
			  System.out.println("test error encrypt value=" + Util.byteArrayToHexString(outbuf, " "));
			 }
			 
			
			if (encodeLenError != 0) {
			  byte[] enOutBuf = new byte[blocksize + totalsize + 1];
			  byte[] outBuffError = new byte[totalsize + 1];
			  outBuffError[(outBuffError.length - 1)] = encodeLenError;
			  System.arraycopy(ivRandom, 0, enOutBuf, 0, blocksize);
			  System.arraycopy(outbuf, 0, outBuffError, 0, outbuf.length);
			  System.arraycopy(outBuffError, 0, enOutBuf, blocksize, totalsize + 1);
			  return enOutBuf;
			 }
			byte[] enOutBuf = new byte[blocksize + totalsize];
			System.arraycopy(ivRandom, 0, enOutBuf, 0, blocksize);
			System.arraycopy(outbuf, 0, enOutBuf, blocksize, totalsize);
			return enOutBuf;
		}	
      
		System.out.println("＝＝＝＝＝＝＝encodePlaintext无算法＝＝＝＝＝＝＝");
		byte[] outbuf = new byte[len + writeMac.getSize()];
		System.arraycopy(plaintext, offset, outbuf, 0, len);
		byte[] mac = writeMac.calculateMac(type, plaintext, offset, len);
		mac[0] = ((byte)(mac[0] & macError));
		System.arraycopy(mac, 0, outbuf, len, mac.length);
		return outbuf;
	}
  
	public byte[] decodeCiphertext(short type, byte[] ciphertext, int offset, int len, TlsProtocolHandler handler)
      throws IOException
	{
		if (decryptCipher != null) {
			int blocksize = decryptCipher.getBlockSize();
			boolean decrypterror = false;
			
			for (int i = 0; i < len; i += blocksize) {
				decryptCipher.processBlock(ciphertext, i + offset, ciphertext, i + offset);
            }
     
			int paddingsize = ciphertext[(offset + len - 1)];
			if (offset + len - 1 - paddingsize < 0)
			{
				decrypterror = true;
				paddingsize = 0;
			}
			else
			{
				for (int i = 0; i <= paddingsize; i++) {
					if (ciphertext[(offset + len - 1 - i)] != paddingsize)
					{
						decrypterror = true;
					}
				}
			}

			int plaintextlength = len - readMac.getSize() - paddingsize - 1;
			byte[] calculatedMac = readMac.calculateMac(type, ciphertext, 
					offset, plaintextlength);

			for (int i = 0; i < calculatedMac.length; i++) {
				if (ciphertext[(offset + plaintextlength + i)] != calculatedMac[i]) {
					decrypterror = true;
				}
			}

			if (decrypterror) {
				throw new TlsFatalAlert((short)20);
			}
        
			byte[] plaintext = new byte[plaintextlength];
			System.arraycopy(ciphertext, offset, plaintext, 0, plaintextlength);
			return plaintext;
		}
		System.out.println("===============decodeCiphertext无算法==============");
		boolean decrypterror = false;
      
		int plaintextlength = len - readMac.getSize();
		byte[] calculatedMac = readMac.calculateMac(type, ciphertext, 
				offset, plaintextlength);

		for (int i = 0; i < calculatedMac.length; i++) {
			if (ciphertext[(offset + plaintextlength + i)] != calculatedMac[i]) {
				decrypterror = true;
			}
		}

		if (decrypterror) {
			throw new TlsFatalAlert((short)20);
		}
      
		byte[] plaintext = new byte[plaintextlength];
		System.arraycopy(ciphertext, offset, plaintext, 0, plaintextlength);
		return plaintext;
    }

	public short getKeyExchangeAlgorithm()
    {
		return keyExchange;
    }
             
	public byte[] decodeCiphertext(short type, byte[] ivAndCiphertext, int offset, int len)
		throws IOException
	{
		if (decryptCipher != null) {
			int blocksize = decryptCipher.getBlockSize();
			boolean decrypterror = false;
			byte[] ciphertext = null;
        
			if ((minorVer == 3) || (minorVer == 2)) {
				byte[] ivRandom = new byte[blocksize];
          
				System.arraycopy(ivAndCiphertext, offset, ivRandom, 0, blocksize);
				ParametersWithIV parameters_with_iv = new ParametersWithIV(
						key_parameterDe, ivRandom, 0, blocksize);
				decryptCipher.init(false, parameters_with_iv);
          
				int ciphertextLen = len - blocksize;
				ciphertext = new byte[ciphertextLen];
				System.arraycopy(ivAndCiphertext, blocksize, ciphertext, 0, ciphertextLen);
         len = ciphertextLen;
        }
		else {
			ciphertext = ivAndCiphertext;
        }

		System.out.println(
				"解密前的数据=" + Util.byteArrayToHexString(ciphertext, " "));
 
		for (int i = 0; i < len; i += blocksize) {
         decryptCipher.processBlock(ciphertext, i + offset, ciphertext, 
           i + offset);
		}
        
		System.out.println(
         "解密完成后的数据=" + Util.byteArrayToHexString(ciphertext, " "));
           
		int paddingsize = ciphertext[(offset + len - 1)];
		System.out.println("期望的填充大小=" + StringUtil.shortToHexWithoutHigh((short)paddingsize));
		if (offset + len - 1 - paddingsize < 0)
		{
			System.out.println(
    			   "填充不正确：" + (offset + len - 1 - paddingsize) + "小于0");
			decrypterror = true;
			paddingsize = 0;
			throw new RuntimeException("check error:填充不正确：" + (offset + len - 1 - paddingsize) + "小于0");
		}

		for (int i = 0; i <= paddingsize; i++) {
			if (ciphertext[(offset + len - 1 - i)] != paddingsize)
			{
				System.out.println(
    				   "期望的填充:padding[" + 
    				   i + 
    				   "]=" + 
    				   StringUtil.shortToHexWithoutHigh((short)paddingsize));
    		   System.out.println(
    				   "实际的填充:padding[" + 
    				   i + 
    				   "]=" + 
    				   StringUtil.byteToHex(ciphertext[(offset + len - 1 - i)]));
    		   decrypterror = true;
    		   throw new RuntimeException("check error:padding error");
    	   }
       }
       int plaintextlength = len - readMac.getSize() - paddingsize - 1;
        
       byte[] calculatedMac = readMac.calculateMac(type, ciphertext, 
         offset, plaintextlength);
       System.out.println(
         "期望的MAC=" + Util.byteArrayToHexString(calculatedMac, " "));

       for (int i = 0; i < calculatedMac.length; i++) {
    	   if (ciphertext[(offset + plaintextlength + i)] != calculatedMac[i]) {
    		   System.out.println(
    				   "期望的MAC:calculatedMac[" + 
    				   i + 
    				   "]=" + 
    				   StringUtil.byteToHex(calculatedMac[i]));
    		   System.out.println(
    				   "实际的MAC:ciphertext[" + (
    						   offset + 
    						   plaintextlength + 
    						   i) + 
    						   "]=" + 
    						   StringUtil.byteToHex(ciphertext[
    						        (offset + plaintextlength + i)]));
    		   decrypterror = true;
    		   throw new RuntimeException("check error:MAC error");
          }
       }
       
       if (decrypterror) {
         return null;
        }

       byte[] plaintext = new byte[plaintextlength];
       System.arraycopy(ciphertext, offset, plaintext, 0, plaintextlength);
       return plaintext;
		}
		
		System.out.println("＝＝＝＝＝＝＝decodeCiphertext无算法＝＝＝＝＝＝＝");
		boolean decrypterror = false;
      
  
		byte[] ciphertext = ivAndCiphertext;
      
		int plaintextlength = len - readMac.getSize();
		byte[] calculatedMac = readMac.calculateMac(type, ciphertext, offset, plaintextlength);
		System.out.println(
				"无算法时的实际的MAC=" + 
				Util.byteArrayToHexString(calculatedMac, " "));
   
		for (int i = 0; i < calculatedMac.length; i++) {
	       if (ciphertext[(offset + plaintextlength + i)] != calculatedMac[i]) {
	    	   System.out.println(
	    			   "无算法时期望的MAC:ciphertext[" + 
	    			   i + 
	    			   "]=" + 
	    			   StringUtil.byteToHex(calculatedMac[i]));
	    	   System.out.println(
	    			   "无算法时实际的MAC:ciphertext[" + (
	    					   offset + 
	    					   plaintextlength + 
	    					   i) + 
	    					   "]=" + 
	    					   StringUtil.byteToHex(ciphertext[(offset + plaintextlength + i)]));
	    	   decrypterror = true;
	    	   throw new RuntimeException("check error:MAC error");
	       }
		}
     
		if (decrypterror) {
			return null;
		}
 
		byte[] plaintext = new byte[plaintextlength];
		System.arraycopy(ciphertext, offset, plaintext, 0, plaintextlength);
		return plaintext;
	}      
}
