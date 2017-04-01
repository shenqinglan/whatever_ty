package gsta.com.tls.util;

import java.io.IOException;

public abstract class TlsCipherSuite
{
  public static final short KE_RSA = 1;
  public static final short KE_RSA_EXPORT = 2;
  public static final short KE_DHE_DSS = 3;
  public static final short KE_DHE_DSS_EXPORT = 4;
  public static final short KE_DHE_RSA = 5;
  public static final short KE_DHE_RSA_EXPORT = 6;
  public static final short KE_DH_DSS = 7;
  public static final short KE_DH_RSA = 8;
  public static final short KE_DH_anon = 9;
  protected static final short KE_PSK = 10;
  
  public abstract void init(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, short paramShort1, short paramShort2);
  
  public abstract void initServer(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, short paramShort1, short paramShort2);
  
  public abstract byte[] encodePlaintext(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2,int serverOrClient);
 
  public abstract byte[] encodePlaintext(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  
  public abstract byte[] encodePlaintextError(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2, byte paramByte1, int paramInt3, byte paramByte2, byte paramByte3);
 
  public abstract byte[] decodeCiphertext(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2, TlsProtocolHandler paramTlsProtocolHandler)
  throws IOException;
  
  public abstract byte[] decodeCiphertext(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
  
  public abstract short getKeyExchangeAlgorithm();
}