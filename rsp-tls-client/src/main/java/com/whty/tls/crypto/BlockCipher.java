package com.whty.tls.crypto;

import com.whty.tls.crypto.CipherParameters;
import com.whty.tls.crypto.DataLengthException;

public abstract interface BlockCipher
{
  public abstract void init(boolean paramBoolean, CipherParameters paramCipherParameters)
    throws IllegalArgumentException;
  
  public abstract String getAlgorithmName();
  
  public abstract int getBlockSize();
  
  public abstract int processBlock(byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
    throws DataLengthException, IllegalStateException;
  
  public abstract void reset();
}

