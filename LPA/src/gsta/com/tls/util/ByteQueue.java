package gsta.com.tls.util;

public class ByteQueue
{
  private static final int INITBUFSIZE = 1024;

  public static final int nextTwoPow(int i)
  {
    i |= i >> 1;
    i |= i >> 2;
    i |= i >> 4;
    i |= i >> 8;
    i |= i >> 16;
    return i + 1;
  }

  private byte[] databuf = new byte['?'];

  private int skipped = 0;

  private int available = 0;

  public void read(byte[] buf, int offset, int len, int skip)
  {
    if (available - skip < len) {
      throw new TlsRuntimeException("Not enough data to read");
    }
    if (buf.length - offset < len) {
      throw new TlsRuntimeException("Buffer size of " + buf.length + 
        " is too small for a read of " + len + " bytes");
    }
    System.arraycopy(databuf, skipped + skip, buf, offset, len);
  }

  public void addData(byte[] data, int offset, int len)
  {
    if (skipped + available + len > databuf.length) {
      byte[] tmp = new byte[nextTwoPow(data.length)];
      System.arraycopy(databuf, skipped, tmp, 0, available);
      skipped = 0;
      databuf = tmp;
    }
    System.arraycopy(data, offset, databuf, skipped + available, len);
    available += len;
  }

  public void removeData(int i)
  {
    if (i > available) {
      throw new TlsRuntimeException("Cannot remove " + i + 
        " bytes, only got " + available);
    }

    available -= i;
    skipped += i;

    if (skipped > databuf.length / 2) {
      System.arraycopy(databuf, skipped, databuf, 0, available);
      skipped = 0;
    }
  }

  public int size()
  {
    return available;
  }
}


