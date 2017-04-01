package gsta.com.tls.util;

import gsta.com.tls.crypto.Digest;
import gsta.com.tls.crypto.digests.MD5Digest;
import gsta.com.tls.crypto.digests.SHA1Digest;

public class CombinedHash implements Digest
{
	private Digest md5 = new MD5Digest();
	private Digest sha1 = new SHA1Digest();
  
	public String getAlgorithmName()
	{
		return md5.getAlgorithmName() + " and " + sha1.getAlgorithmName() + " for TLS 1.0";
	}

	public int getDigestSize()
	{
		return 36;
	}

	public void update(byte in)
	{
		md5.update(in);
		sha1.update(in);
	}

	public void update(byte[] in, int inOff, int len)
	{
		md5.update(in, inOff, len);
		sha1.update(in, inOff, len);
	}

	public int doFinal(byte[] out, int outOff)
	{
		int i1 = md5.doFinal(out, outOff);
		int i2 = sha1.doFinal(out, outOff + 16);
		return i1 + i2;
	}

	public void reset()
	{
		md5.reset();
		sha1.reset();
	}
}