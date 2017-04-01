package com.whty.euicc.tls.client.crypto.prng;

public class ThreadedSeedGenerator
{
  private class SeedGenerator
    implements Runnable
  {
    private volatile int counter = 0;
    private volatile boolean stop = false;

    private SeedGenerator() {  } 
    public void run() { while (!this.stop)
        this.counter += 1;
    }

    public byte[] generateSeed(int numbytes, boolean fast)
    {
      Thread t = new Thread(this);
      byte[] result = new byte[numbytes];
      this.counter = 0;
      this.stop = false;
      int last = 0;

      t.start();
      int end;
      if (fast)
        end = numbytes;
      else {
        end = numbytes * 8;
      }
      for (int i = 0; i < end; i++) {
        while (this.counter == last)
          try {
            Thread.sleep(1L);
          }
          catch (InterruptedException localInterruptedException)
          {
          }
        last = this.counter;
        if (fast) {
          result[i] = ((byte)(last & 0xFF));
        } else {
          int bytepos = i / 8;
          result[bytepos] = ((byte)(result[bytepos] << 1 | last & 0x1));
        }
      }

      this.stop = true;
      return result;
    }
  }
  
  public byte[] generateSeed(int numBytes, boolean fast)
  {
    SeedGenerator gen = new SeedGenerator();

    return gen.generateSeed(numBytes, fast);
  }
}
