package com.whty.tls.crypto.modes;

import com.whty.tls.crypto.BlockCipher;
import com.whty.tls.crypto.CipherParameters;
import com.whty.tls.crypto.DataLengthException;
import com.whty.tls.crypto.params.ParametersWithIV;

public class CBCBlockCipher implements BlockCipher
{
  private byte[] IV;
  private byte[] cbcV;
  private byte[] cbcNextV;
  private int blockSize;
  private BlockCipher cipher = null;
  private boolean encrypting;

  public CBCBlockCipher(BlockCipher cipher)
  {
    this.cipher = cipher;
    this.blockSize = cipher.getBlockSize();

    this.IV = new byte[this.blockSize];
    this.cbcV = new byte[this.blockSize];
    this.cbcNextV = new byte[this.blockSize];
  }

  public BlockCipher getUnderlyingCipher()
  {
    return this.cipher;
  }

  @Override
public void init(boolean encrypting, CipherParameters params)
    throws IllegalArgumentException
  {
    this.encrypting = encrypting;

    if ((params instanceof ParametersWithIV))
    {
      ParametersWithIV ivParam = (ParametersWithIV)params;
      byte[] iv = ivParam.getIV();

      if (iv.length != this.blockSize)
      {
        throw new IllegalArgumentException("initialisation vector must be the same length as block size");
      }

      System.arraycopy(iv, 0, this.IV, 0, iv.length);

      reset();

      this.cipher.init(encrypting, ivParam.getParameters());
    }
    else
    {
      reset();

      this.cipher.init(encrypting, params);
    }
  }

  @Override
public String getAlgorithmName()
  {
    return this.cipher.getAlgorithmName() + "/CBC";
  }

  @Override
public int getBlockSize()
  {
    return this.cipher.getBlockSize();
  }

  @Override
public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    return this.encrypting ? encryptBlock(in, inOff, out, outOff) : decryptBlock(in, inOff, out, outOff);
  }

  @Override
public void reset()
  {
    System.arraycopy(this.IV, 0, this.cbcV, 0, this.IV.length);

    this.cipher.reset();
  }

  private int encryptBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (inOff + this.blockSize > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }

    for (int i = 0; i < this.blockSize; i++)
    {
      int tmp33_31 = i;
      byte[] tmp33_28 = this.cbcV; tmp33_28[tmp33_31] = ((byte)(tmp33_28[tmp33_31] ^ in[(inOff + i)]));
    }

    int length = this.cipher.processBlock(this.cbcV, 0, out, outOff);

    System.arraycopy(out, outOff, this.cbcV, 0, this.cbcV.length);

    return length;
  }

  private int decryptBlock(byte[] in, int inOff, byte[] out, int outOff)
    throws DataLengthException, IllegalStateException
  {
    if (inOff + this.blockSize > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }

    System.arraycopy(in, inOff, this.cbcNextV, 0, this.blockSize);

    int length = this.cipher.processBlock(in, inOff, out, outOff);

    for (int i = 0; i < this.blockSize; i++)
    {
      int tmp63_62 = (outOff + i);
      byte[] tmp63_57 = out; tmp63_57[tmp63_62] = ((byte)(tmp63_57[tmp63_62] ^ this.cbcV[i]));
    }

    byte[] tmp = this.cbcV;
    this.cbcV = this.cbcNextV;
    this.cbcNextV = tmp;

    return length;
  }
}
