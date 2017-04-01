package gsta.com.tls.util;

import gsta.com.tls.crypto.digests.SHA1Digest;
import gsta.com.tls.crypto.digests.SHA256Digest;
import gsta.com.tls.crypto.prng.DigestRandomGenerator;
import gsta.com.tls.crypto.prng.RandomGenerator;

import java.util.Random;

public class SecureRandom extends Random
{
  private static SecureRandom rand = new SecureRandom(new DigestRandomGenerator(new SHA1Digest()));
  protected RandomGenerator generator;

  public SecureRandom()
  {
    super(0L);
    this.generator = new DigestRandomGenerator(new SHA1Digest());
    setSeed(System.currentTimeMillis());
  }

  public SecureRandom(byte[] inSeed)
  {
    super(0L);
    this.generator = new DigestRandomGenerator(new SHA1Digest());
    setSeed(inSeed);
  }

  protected SecureRandom(RandomGenerator generator)
  {
    super(0L);
    this.generator = generator;
  }

  public static SecureRandom getInstance(String algorithm)
  {
    if (algorithm.equals("SHA1PRNG"))
    {
      return new SecureRandom(new DigestRandomGenerator(new SHA1Digest()));
    }
    if (algorithm.equals("SHA256PRNG"))
    {
      return new SecureRandom(new DigestRandomGenerator(new SHA256Digest()));
    }
    return new SecureRandom();
  }

  public static SecureRandom getInstance(String algorithm, String provider)
  {
    return getInstance(algorithm);
  }

  public static byte[] getSeed(int numBytes)
  {
    byte[] rv = new byte[numBytes];

    rand.setSeed(System.currentTimeMillis());
    rand.nextBytes(rv);

    return rv;
  }

  public byte[] generateSeed(int numBytes)
  {
    byte[] rv = new byte[numBytes];

    nextBytes(rv);

    return rv;
  }

  public void setSeed(byte[] inSeed)
  {
    this.generator.addSeedMaterial(inSeed);
  }

  public void nextBytes(byte[] bytes)
  {
    this.generator.nextBytes(bytes);
  }

  public void setSeed(long rSeed)
  {
    if (rSeed != 0L)
    {
      this.generator.addSeedMaterial(rSeed);
    }
  }

  public int nextInt()
  {
    byte[] intBytes = new byte[4];

    nextBytes(intBytes);

    int result = 0;

    for (int i = 0; i < 4; i++)
    {
      result = (result << 8) + (intBytes[i] & 0xFF);
    }

    return result;
  }

  protected final int next(int numBits)
  {
    int size = (numBits + 7) / 8;
    byte[] bytes = new byte[size];

    nextBytes(bytes);

    int result = 0;

    for (int i = 0; i < size; i++)
    {
      result = (result << 8) + (bytes[i] & 0xFF);
    }

    return result & (1 << numBits) - 1;
  }
}