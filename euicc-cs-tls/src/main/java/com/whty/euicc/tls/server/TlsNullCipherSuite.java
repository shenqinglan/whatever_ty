package com.whty.euicc.tls.server;

import java.io.IOException;

public class TlsNullCipherSuite
  extends TlsCipherSuite
{
  public void init(byte[] ms, byte[] cr, byte[] sr, short majorVersion, short minorVersion)
  {
	  
  }
  
  public void initServer(byte[] ms, byte[] cr, byte[] sr, short majorVersion, short minorVersion) {
	  
  }
  
  public byte[] encodePlaintext(short type, byte[] plaintext, int offset, int len , int serverOrClient) {
    byte[] result = new byte[len];
    System.arraycopy(plaintext, offset, result, 0, len);
    return result;
  }
  
  public byte[] encodePlaintextError(short type, byte[] plaintext, int offset, int len, byte macError, int paddingError, byte encryptError, byte encryptLenError) {
    return null;
  }
  
  public short getKeyExchangeAlgorithm() {
    return 0;
  }
  

  public byte[] decodeCiphertext(short type, byte[] plaintext, int offset, int len)
    throws IOException
  {
    byte[] result = new byte[len];
    System.arraycopy(plaintext, offset, result, 0, len);
    return result;
  }
}
