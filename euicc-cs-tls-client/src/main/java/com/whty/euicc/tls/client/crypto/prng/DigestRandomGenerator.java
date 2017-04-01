package com.whty.euicc.tls.client.crypto.prng;

import com.whty.euicc.tls.client.crypto.Digest;

public class DigestRandomGenerator
  implements RandomGenerator
{
  private static long CYCLE_COUNT = 10L;
  private long stateCounter;
  private long seedCounter;
  private Digest digest;
  private byte[] state;
  private byte[] seed;

  public DigestRandomGenerator(Digest digest)
  {
    this.digest = digest;

    this.seed = new byte[digest.getDigestSize()];
    this.seedCounter = 1L;

    this.state = new byte[digest.getDigestSize()];
    this.stateCounter = 1L;
  }

  public void addSeedMaterial(byte[] inSeed)
  {
    synchronized (this)
    {
      digestUpdate(inSeed);
      digestUpdate(this.seed);
      digestDoFinal(this.seed);
    }
  }

  public void addSeedMaterial(long rSeed)
  {
    synchronized (this)
    {
      digestAddCounter(rSeed);
      digestUpdate(this.seed);

      digestDoFinal(this.seed);
    }
  }

  public void nextBytes(byte[] bytes)
  {
    nextBytes(bytes, 0, bytes.length);
  }

  public void nextBytes(byte[] bytes, int start, int len)
  {
    synchronized (this)
    {
      int stateOff = 0;

      generateState();

      int end = start + len;
      for (int i = start; i != end; i++)
      {
        if (stateOff == this.state.length)
        {
          generateState();
          stateOff = 0;
        }
        bytes[i] = this.state[(stateOff++)];
      }
    }
  }

  private void cycleSeed()
  {
    digestUpdate(this.seed);
    digestAddCounter(this.seedCounter++);

    digestDoFinal(this.seed);
  }

  private void generateState()
  {
    digestAddCounter(this.stateCounter++);
    digestUpdate(this.state);
    digestUpdate(this.seed);

    digestDoFinal(this.state);

    if (this.stateCounter % CYCLE_COUNT == 0L)
    {
      cycleSeed();
    }
  }

  private void digestAddCounter(long seed)
  {
    for (int i = 0; i != 8; i++)
    {
      this.digest.update((byte)(int)seed);
      seed >>>= 8;
    }
  }

  private void digestUpdate(byte[] inSeed)
  {
    this.digest.update(inSeed, 0, inSeed.length);
  }

  private void digestDoFinal(byte[] result)
  {
    this.digest.doFinal(result, 0);
  }
}