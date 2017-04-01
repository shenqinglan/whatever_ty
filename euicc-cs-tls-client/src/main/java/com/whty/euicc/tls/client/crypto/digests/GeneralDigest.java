package com.whty.euicc.tls.client.crypto.digests;

import com.whty.euicc.tls.client.crypto.ExtendedDigest;

public abstract class GeneralDigest implements ExtendedDigest
{
	private static final int BYTE_LENGTH = 64;
	private byte[] xBuf;
	private int xBufOff;
	private long byteCount;
  
	protected GeneralDigest()
	{
		xBuf = new byte[4];
		xBufOff = 0;
	}

	protected GeneralDigest(GeneralDigest t)
	{
		xBuf = new byte[xBuf.length];
		System.arraycopy(xBuf, 0, xBuf, 0, xBuf.length);
    
		xBufOff = xBufOff;
		byteCount = byteCount;
	}

	public void update(byte in)
	{
		xBuf[(xBufOff++)] = in;

		if (xBufOff == xBuf.length)
		{
			processWord(xBuf, 0);
			xBufOff = 0;
		}
    
		byteCount += 1L;
	}

	public void update(byte[] in, int inOff, int len)
	{
		do
		{
			update(in[inOff]);
      
			inOff++;
			len--;
			if (xBufOff == 0) break; 
		} while (len > 0);

		while (len > xBuf.length)
		{
			processWord(in, inOff);
      
			inOff += xBuf.length;
			len -= xBuf.length;
			byteCount += xBuf.length;
		}

		while (len > 0)
		{
			update(in[inOff]);
      
			inOff++;
			len--;
		}
	}
  
	public void finish()
	{
		long bitLength = byteCount << 3;

		update((byte)Byte.MIN_VALUE);
    
		while (xBufOff != 0)
		{
			update((byte)0);
		}
    
		processLength(bitLength);
    
		processBlock();
	}
  
	public void reset()
	{
		byteCount = 0L;
    
		xBufOff = 0;
		for (int i = 0; i < xBuf.length; i++)
		{
			xBuf[i] = 0;
		}
	}
  
	public int getByteLength()
	{
		return 64;
	}
  
	protected abstract void processWord(byte[] paramArrayOfByte, int paramInt);
  
	protected abstract void processLength(long paramLong);
  
	protected abstract void processBlock();
}

