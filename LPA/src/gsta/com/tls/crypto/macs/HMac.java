package gsta.com.tls.crypto.macs;

import gsta.com.tls.crypto.CipherParameters;
import gsta.com.tls.crypto.Digest;
import gsta.com.tls.crypto.ExtendedDigest;
import gsta.com.tls.crypto.Mac;
import gsta.com.tls.crypto.params.KeyParameter;

import java.util.Hashtable;


public class HMac implements Mac
{
  private static final byte IPAD = 54;
  private static final byte OPAD = 92;
  private Digest digest;
  private int digestSize;
  private int blockLength;
  private byte[] inputPad;
  private byte[] outputPad;
  private static Hashtable blockLengths = new Hashtable();

  static { blockLengths.put("GOST3411", new Integer(32));

    blockLengths.put("MD2", new Integer(16));
    blockLengths.put("MD4", new Integer(64));
    blockLengths.put("MD5", new Integer(64));

    blockLengths.put("RIPEMD128", new Integer(64));
    blockLengths.put("RIPEMD160", new Integer(64));

    blockLengths.put("SHA-1", new Integer(64));
    blockLengths.put("SHA-224", new Integer(64));
    blockLengths.put("SHA-256", new Integer(64));
    blockLengths.put("SHA-384", new Integer(128));
    blockLengths.put("SHA-512", new Integer(128));

    blockLengths.put("Tiger", new Integer(64));
    blockLengths.put("Whirlpool", new Integer(64));
  }

  private static int getByteLength(Digest digest)
  {
    if ((digest instanceof ExtendedDigest))
    {
      return ((ExtendedDigest)digest).getByteLength();
    }

    Integer b = (Integer)blockLengths.get(digest.getAlgorithmName());

    if (b == null)
    {
      throw new IllegalArgumentException("unknown digest passed: " + digest.getAlgorithmName());
    }

    return b.intValue();
  }

  public HMac(Digest digest)
  {
    this(digest, getByteLength(digest));
  }

  private HMac(Digest digest, int byteLength)
  {
    this.digest = digest;
    this.digestSize = digest.getDigestSize();

    this.blockLength = byteLength;

    this.inputPad = new byte[this.blockLength];
    this.outputPad = new byte[this.blockLength];
  }

  public String getAlgorithmName()
  {
    return this.digest.getAlgorithmName() + "/HMAC";
  }

  public Digest getUnderlyingDigest()
  {
    return this.digest;
  }

  public void init(CipherParameters params)
  {
    this.digest.reset();

    byte[] key = ((KeyParameter)params).getKey();

    if (key.length > this.blockLength)
    {
      this.digest.update(key, 0, key.length);
      this.digest.doFinal(this.inputPad, 0);
      for (int i = this.digestSize; i < this.inputPad.length; i++)
      {
        this.inputPad[i] = 0;
      }
    }
    else
    {
      System.arraycopy(key, 0, this.inputPad, 0, key.length);
      for (int i = key.length; i < this.inputPad.length; i++)
      {
        this.inputPad[i] = 0;
      }
    }

    this.outputPad = new byte[this.inputPad.length];
    System.arraycopy(this.inputPad, 0, this.outputPad, 0, this.inputPad.length);

    for (int i = 0; i < this.inputPad.length; i++)
    {
      int tmp160_159 = i;
      byte[] tmp160_156 = this.inputPad; tmp160_156[tmp160_159] = ((byte)(tmp160_156[tmp160_159] ^ 0x36));
    }

    for (int i = 0; i < this.outputPad.length; i++)
    {
      int tmp189_188 = i;
      byte[] tmp189_185 = this.outputPad; tmp189_185[tmp189_188] = ((byte)(tmp189_185[tmp189_188] ^ 0x5C));
    }

    this.digest.update(this.inputPad, 0, this.inputPad.length);
  }

  public int getMacSize()
  {
    return this.digestSize;
  }

  public void update(byte in)
  {
    this.digest.update(in);
  }

  public void update(byte[] in, int inOff, int len)
  {
    this.digest.update(in, inOff, len);
  }

  public int doFinal(byte[] out, int outOff)
  {
    byte[] tmp = new byte[this.digestSize];
    this.digest.doFinal(tmp, 0);

    this.digest.update(this.outputPad, 0, this.outputPad.length);
    this.digest.update(tmp, 0, tmp.length);

    int len = this.digest.doFinal(out, outOff);

    reset();

    return len;
  }

  public void reset()
  {
    this.digest.reset();

    this.digest.update(this.inputPad, 0, this.inputPad.length);
  }
}