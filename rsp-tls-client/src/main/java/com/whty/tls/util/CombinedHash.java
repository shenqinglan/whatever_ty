package com.whty.tls.util;

import com.whty.tls.crypto.Digest;
import com.whty.tls.crypto.digests.MD5Digest;
import com.whty.tls.crypto.digests.SHA1Digest;

public class CombinedHash implements Digest
{
	private Digest md5 = new MD5Digest();
	private Digest sha1 = new SHA1Digest();
  
	@Override
	public String getAlgorithmName()
	{
		return md5.getAlgorithmName() + " and " + sha1.getAlgorithmName() + " for TLS 1.0";
	}

	@Override
	public int getDigestSize()
	{
		return 36;
	}

	@Override
	public void update(byte in)
	{
		md5.update(in);
		sha1.update(in);
	}

	@Override
	public void update(byte[] in, int inOff, int len)
	{
		md5.update(in, inOff, len);
		sha1.update(in, inOff, len);
	}

	@Override
	public int doFinal(byte[] out, int outOff)
	{
		int i1 = md5.doFinal(out, outOff);
		int i2 = sha1.doFinal(out, outOff + 16);
		return i1 + i2;
	}

	@Override
	public void reset()
	{
		md5.reset();
		sha1.reset();
	}
}