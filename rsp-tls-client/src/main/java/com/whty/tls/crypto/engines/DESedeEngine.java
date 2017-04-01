package com.whty.tls.crypto.engines;


import com.whty.tls.crypto.CipherParameters;
import com.whty.tls.crypto.DataLengthException;
import com.whty.tls.crypto.params.KeyParameter;


public class DESedeEngine extends DESEngine
{
  protected static final int BLOCK_SIZE = 8;
  private int[] workingKey1 = null;
  private int[] workingKey2 = null;
  private int[] workingKey3 = null;
  private boolean forEncryption;

  @Override
public void init(boolean encrypting, CipherParameters params)
  {
    if (!(params instanceof KeyParameter))
    {
      throw new IllegalArgumentException("invalid parameter passed to DESede init - " + params.getClass().getName());
    }

    byte[] keyMaster = ((KeyParameter)params).getKey();
    byte[] key1 = new byte[8]; byte[] key2 = new byte[8]; byte[] key3 = new byte[8];

    if (keyMaster.length > 24)
    {
      throw new IllegalArgumentException("key size greater than 24 bytes");
    }

    this.forEncryption = encrypting;

    if (keyMaster.length == 24)
    {
      System.arraycopy(keyMaster, 0, key1, 0, key1.length);
      System.arraycopy(keyMaster, 8, key2, 0, key2.length);
      System.arraycopy(keyMaster, 16, key3, 0, key3.length);

      this.workingKey1 = generateWorkingKey(encrypting, key1);
      this.workingKey2 = generateWorkingKey(!encrypting, key2);
      this.workingKey3 = generateWorkingKey(encrypting, key3);
    }
    else
    {
      System.arraycopy(keyMaster, 0, key1, 0, key1.length);
      System.arraycopy(keyMaster, 8, key2, 0, key2.length);

      this.workingKey1 = generateWorkingKey(encrypting, key1);
      this.workingKey2 = generateWorkingKey(!encrypting, key2);
      this.workingKey3 = this.workingKey1;
    }
  }

  @Override
public String getAlgorithmName()
  {
    return "DESede";
  }

  @Override
public int getBlockSize()
  {
    return 8;
  }

  @Override
public int processBlock(byte[] in, int inOff, byte[] out, int outOff)
  {
    if (this.workingKey1 == null)
    {
      throw new IllegalStateException("DESede engine not initialised");
    }

    if (inOff + 8 > in.length)
    {
      throw new DataLengthException("input buffer too short");
    }

    if (outOff + 8 > out.length)
    {
      throw new DataLengthException("output buffer too short");
    }

    if (this.forEncryption)
    {
      desFunc(this.workingKey1, in, inOff, out, outOff);
      desFunc(this.workingKey2, out, outOff, out, outOff);
      desFunc(this.workingKey3, out, outOff, out, outOff);
    }
    else
    {
      desFunc(this.workingKey3, in, inOff, out, outOff);
      desFunc(this.workingKey2, out, outOff, out, outOff);
      desFunc(this.workingKey1, out, outOff, out, outOff);
    }

    return 8;
  }

  @Override
public void reset()
  {
  }
}