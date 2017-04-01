package com.whty.euicc.tls.server;

import java.io.IOException;
import java.io.OutputStream;
import com.telecom.http.tls.TlsFatalAlert;
import com.telecom.http.tls.TlsProtocolHandler;
import com.telecom.http.tls.TlsUtils;

import wq.app.crypto.digests.SHA1Digest;
import wq.app.crypto.digests.SHA256Digest;
import wq.app.crypto.engines.AESFastEngine;
import wq.app.crypto.engines.DESedeEngine;
import wq.app.crypto.modes.CBCBlockCipher;
 
public class TlsCipherSuiteManager
{
	public static short MaxFragment_512 = 1;
	public static short MaxFragment_1024 = 2;
	public static short MaxFragment_2048 = 3;
	public static short MaxFragment_4096 = 4;
  
	public static final int TLS_PSK_WITH_3DES_EDE_CBC_SHA = 139;
 
	public static final int TLS_PSK_WITH_AES_128_CBC_SHA = 140;

	public static final int TLS_PSK_WITH_NULL_SHA = 44;
	public static final int TLS_PSK_WITH_AES_128_CBC_SHA256 = 174;
	public static final int TLS_PSK_WITH_NULL_SHA256 = 176;
	public static int cipherSuiteNumber = 176;
 
	public static void writeCipherSuites(OutputStream os) throws IOException {
		TlsUtils.writeUint16(10, os);
		TlsUtils.writeUint16(139, os);
		TlsUtils.writeUint16(140, os);
		TlsUtils.writeUint16(44, os);
 
		TlsUtils.writeUint16(174, os);
		TlsUtils.writeUint16(176, os);
	}

	public static void choseCipherSuites(OutputStream os)
		throws IOException{
		TlsUtils.writeUint16(cipherSuiteNumber, os);
	}
 
	public static String getCipherSuiteName(int number) {
		switch (number) {
			case 139: 
				return "TLS_PSK_WITH_3DES_EDE_CBC_SHA";
			case 140: 
				return "TLS_PSK_WITH_AES_128_CBC_SHA";
			case 44: 
				return "TLS_PSK_WITH_NULL_SHA";
			case 174: 
				return "TLS_PSK_WITH_AES_128_CBC_SHA256";
			case 176: 
				return "TLS_PSK_WITH_NULL_SHA256";
		}
  
		return null;
	}

	public static int getCipherSuiteMaxFragment(int number, short maxFragment)
	{
		switch (number) {
			case 139: 
				return TlsUtils.maxFragmentBytes(maxFragment) + 20 + 256;
			case 140: 
				return TlsUtils.maxFragmentBytes(maxFragment) + 20 + 256;
			case 44: 
				return TlsUtils.maxFragmentBytes(maxFragment) + 20;
			case 174: 
				return TlsUtils.maxFragmentBytes(maxFragment) + 32 + 256;
			case 176: 
				return TlsUtils.maxFragmentBytes(maxFragment) + 32;
		}
	     
		return TlsUtils.maxFragmentBytes(maxFragment) + 2048;
	}

	public static TlsCipherSuite getCipherSuite(int number, TlsProtocolHandler handler)
		throws IOException
	{
		switch (number) {
			case 139: 
				return new TlsBlockCipherSuite(new CBCBlockCipher(
						new DESedeEngine()), 
						new CBCBlockCipher(new DESedeEngine()), new SHA1Digest(), 
						new SHA1Digest(), 24, (short)10);
     
			case 140: 
				return new TlsBlockCipherSuite(new CBCBlockCipher(
						new AESFastEngine()), new CBCBlockCipher(
								new AESFastEngine()), new SHA1Digest(), new SHA1Digest(), 
								16, (short)10);
 
			case 44: 
				return new TlsBlockCipherSuite(new SHA1Digest(), new SHA1Digest(), 
						(short)10);

			case 174: 
				return new TlsBlockCipherSuite(new CBCBlockCipher(
						new AESFastEngine()), new CBCBlockCipher(
								new AESFastEngine()), new SHA256Digest(), new SHA256Digest(), 
								16, (short)10);

			case 176: 
				return new TlsBlockCipherSuite(new SHA256Digest(), new SHA256Digest(), 
						(short)10);
		}
   
		throw new TlsFatalAlert((short)40);
	}

	public static TlsCipherSuite getCipherSuite(int number)
		throws IOException
	{
		switch (number) {
			case 139: 
				return new TlsBlockCipherSuite(new CBCBlockCipher(
						new DESedeEngine()), 
						new CBCBlockCipher(new DESedeEngine()), new SHA1Digest(), 
						new SHA1Digest(), 24, (short)10);

			case 140: 
				return new TlsBlockCipherSuite(new CBCBlockCipher(
						new AESFastEngine()), new CBCBlockCipher(
									new AESFastEngine()), new SHA1Digest(), new SHA1Digest(), 
									16, (short)10);
   
			case 44: 
				return new TlsBlockCipherSuite(new SHA1Digest(), new SHA1Digest(), 
						(short)10);
  
			case 174: 
				return new TlsBlockCipherSuite(new CBCBlockCipher(
						new AESFastEngine()), new CBCBlockCipher(
								new AESFastEngine()), new SHA256Digest(), new SHA256Digest(), 
								16, (short)10);

			case 176: 
				return new TlsBlockCipherSuite(new SHA256Digest(), new SHA256Digest(), 
						(short)10);
		}
  
		throw new TlsFatalAlert((short)40);
	}
}