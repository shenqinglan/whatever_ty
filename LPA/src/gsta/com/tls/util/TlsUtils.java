package gsta.com.tls.util;

import gsta.com.tls.crypto.Digest;
import gsta.com.tls.crypto.digests.MD5Digest;
import gsta.com.tls.crypto.digests.SHA1Digest;
import gsta.com.tls.crypto.digests.SHA256Digest;
import gsta.com.tls.crypto.macs.HMac;
import gsta.com.tls.crypto.params.KeyParameter;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class TlsUtils
{
  public static byte[] toByteArray(String str)
  {
    char[] chars = str.toCharArray();
    byte[] bytes = new byte[chars.length];
    
    for (int i = 0; i != bytes.length; i++) {
      bytes[i] = ((byte)chars[i]);
    }
    
    return bytes;
  }
  
  public static void writeUint8(short i, OutputStream os) throws IOException {
    os.write(i);
  }
  
  public static void writeUint8(short i, byte[] buf, int offset) {
    buf[offset] = ((byte)i);
  }
  
  public static void writeUint16(int i, OutputStream os) throws IOException {
    os.write(i >> 8);
    os.write(i);
  }
  
  public static void writeUint16(int i, byte[] buf, int offset) {
    buf[offset] = ((byte)(i >> 8));
    buf[(offset + 1)] = ((byte)i);
  }
  
  public static void writeUint24(int i, OutputStream os) throws IOException {
    os.write(i >> 16);
    os.write(i >> 8);
    os.write(i);
  }
  
  public static void writeUint24(int i, byte[] buf, int offset) {
    buf[offset] = ((byte)(i >> 16));
    buf[(offset + 1)] = ((byte)(i >> 8));
    buf[(offset + 2)] = ((byte)i);
  }
  
  public static void writeUint32(long i, OutputStream os) throws IOException {
    os.write((int)(i >> 24));
    os.write((int)(i >> 16));
    os.write((int)(i >> 8));
    os.write((int)i);
  }
  
  public static void writeUint32(long i, byte[] buf, int offset) {
    buf[offset] = ((byte)(int)(i >> 24));
    buf[(offset + 1)] = ((byte)(int)(i >> 16));
    buf[(offset + 2)] = ((byte)(int)(i >> 8));
    buf[(offset + 3)] = ((byte)(int)i);
  }
  
  public static void writeUint64(long i, OutputStream os) throws IOException {
    os.write((int)(i >> 56));
    os.write((int)(i >> 48));
    os.write((int)(i >> 40));
    os.write((int)(i >> 32));
    os.write((int)(i >> 24));
    os.write((int)(i >> 16));
    os.write((int)(i >> 8));
    os.write((int)i);
  }
  
  public static void writeUint64(long i, byte[] buf, int offset) {
    buf[offset] = ((byte)(int)(i >> 56));
    buf[(offset + 1)] = ((byte)(int)(i >> 48));
    buf[(offset + 2)] = ((byte)(int)(i >> 40));
    buf[(offset + 3)] = ((byte)(int)(i >> 32));
    buf[(offset + 4)] = ((byte)(int)(i >> 24));
    buf[(offset + 5)] = ((byte)(int)(i >> 16));
    buf[(offset + 6)] = ((byte)(int)(i >> 8));
    buf[(offset + 7)] = ((byte)(int)i);
  }
  
  public static short readUint8(InputStream is) throws IOException {
    int i = is.read();
    if (i == -1) {
      throw new EOFException();
    }
    return (short)i;
  }
  
  public static int readUint16(InputStream is) throws IOException {
    int i1 = is.read();
    int i2 = is.read();
    if ((i1 | i2) < 0) {
      throw new EOFException();
    }
    return i1 << 8 | i2;
  }
  
  public static int readUint24(InputStream is) throws IOException {
    int i1 = is.read();
    int i2 = is.read();
    int i3 = is.read();
    if ((i1 | i2 | i3) < 0) {
      throw new EOFException();
    }
    return i1 << 16 | i2 << 8 | i3;
  }
  
  public static long readUint32(InputStream is) throws IOException {
    int i1 = is.read();
    int i2 = is.read();
    int i3 = is.read();
    int i4 = is.read();
    if ((i1 | i2 | i3 | i4) < 0) {
      throw new EOFException();
    }
    return i1 << 24 | i2 << 16 | i3 << 8 | 
      i4;
  }
  
  public static void readFully(byte[] buf, InputStream is) throws IOException {
    int read = 0;
    int i = 0;
    while (read != buf.length) {
      i = is.read(buf, read, buf.length - read);
      if (i == -1) {
        throw new EOFException();
      }
      read += i;
    }
  }
  
  public static void checkVersion(byte[] readVersion, TlsProtocolHandler handler) throws IOException
  {
    if ((readVersion[0] != 3) || (readVersion[1] != 1)) {
      handler.failWithError((short)2, 
        (short)70);
    }
  }
  
  public static void checkVersion(InputStream is, TlsProtocolHandler handler) throws IOException
  {
    int i1 = is.read();
    int i2 = is.read();
    if ((i1 != 3) || (i2 != 1)) {
      handler.failWithError((short)2, 
        (short)70);
    }
  }
  
  public static boolean checkVersion(InputStream is) throws IOException {
    int i1 = is.read();
    int i2 = is.read();
    if ((i1 != 3) || (
    

      (i1 != 3) || ((i2 != 1) && (i2 != 2) && (i2 != 3)))) {
      System.out.println("版本号不正确");
      System.out.println("actual value=" + i1 + "." + i2);
      System.out.println("expect value=Tls1.0或Tls1.1或Tls1.2");
      throw new RuntimeException("check error:actual value=" + i1 + "." + i2 + "  ,expect value=" + "Tls1.0或Tls1.1或Tls1.2");
    }
    
    return true;
  }
  
  public static boolean checkVersion(InputStream is, short majorVersion, short minorVersion) throws IOException
  {
    int i1 = is.read();
    int i2 = is.read();
    if ((i1 != majorVersion) || (i2 != minorVersion)) {
    	System.out.println("版本号不正确");
    	System.out.println("actual value=" + i1 + "." + i2);
    	System.out.println("expect value=" + majorVersion + "." + minorVersion);
      throw new RuntimeException("check error:actual value=majorVersion:" + i1 + "." + "minorVersion:" + i2 + "  ,expect value=" + majorVersion + "." + minorVersion);
    }
    
    return true;
  }
  
  public static void writeVersion(OutputStream os) throws IOException
  {
    os.write(3);
    os.write(1);
  }
  
  public static void writeVersion_1_1(OutputStream os) throws IOException {
    os.write(3);
    os.write(2);
  }
  
  public static void writeVersion_1_2(OutputStream os) throws IOException {
    os.write(3);
    os.write(3);
  }
  
  public static void writeVersion(OutputStream os, short majorVersion, short minorVersion) throws IOException
  {
    os.write(majorVersion);
    os.write(minorVersion);
  }
  
  private static void hmac_hash(Digest digest, byte[] secret, byte[] seed, byte[] out)
  {
    HMac mac = new HMac(digest);
    KeyParameter param = new KeyParameter(secret);
    byte[] a = seed;
    int size = digest.getDigestSize();
    int iterations = (out.length + size - 1) / size;
    byte[] buf = new byte[mac.getMacSize()];
    byte[] buf2 = new byte[mac.getMacSize()];
    for (int i = 0; i < iterations; i++) {
      mac.init(param);
      mac.update(a, 0, a.length);
      mac.doFinal(buf, 0);
      a = buf;
      mac.init(param);
      mac.update(a, 0, a.length);
      mac.update(seed, 0, seed.length);
      mac.doFinal(buf2, 0);
      System.arraycopy(buf2, 0, out, size * i, Math.min(size, 
        out.length - size * i));
    }
  }
  
  public static void PRF(byte[] secret, byte[] label, byte[] seed, byte[] buf) {
    int s_half = (secret.length + 1) / 2;
    byte[] s1 = new byte[s_half];
    byte[] s2 = new byte[s_half];
    System.arraycopy(secret, 0, s1, 0, s_half);
    System.arraycopy(secret, secret.length - s_half, s2, 0, s_half);
    
    byte[] ls = new byte[label.length + seed.length];
    System.arraycopy(label, 0, ls, 0, label.length);
    System.arraycopy(seed, 0, ls, label.length, seed.length);
    
    byte[] prf = new byte[buf.length];
    hmac_hash(new MD5Digest(), s1, ls, prf);
    hmac_hash(new SHA1Digest(), s2, ls, buf);
    for (int i = 0; i < buf.length; i++) {
      int tmp120_118 = i; byte[] tmp120_117 = buf;tmp120_117[tmp120_118] = ((byte)(tmp120_117[tmp120_118] ^ prf[i]));
    }
  }
  

  public static void PRF_hash(byte[] secret, byte[] label, byte[] seed, byte[] buf, Digest digest)
  {
    byte[] ls = new byte[label.length + seed.length];
    System.arraycopy(label, 0, ls, 0, label.length);
    System.arraycopy(seed, 0, ls, label.length, seed.length);
    hmac_hash(digest, secret, ls, buf);
  }
  

  public static void PRF_sha256(byte[] secret, byte[] label, byte[] seed, byte[] buf)
  {
    byte[] ls = new byte[label.length + seed.length];
    System.arraycopy(label, 0, ls, 0, label.length);
    System.arraycopy(seed, 0, ls, label.length, seed.length);
    hmac_hash(new SHA256Digest(), secret, ls, buf);
  }
  

  public static void PRF_sha1(byte[] secret, byte[] label, byte[] seed, byte[] buf)
  {
    byte[] ls = new byte[label.length + seed.length];
    System.arraycopy(label, 0, ls, 0, label.length);
    System.arraycopy(seed, 0, ls, label.length, seed.length);
    hmac_hash(new SHA1Digest(), secret, ls, buf);
  }
  
  public static byte[] psk2pms(byte[] psk) throws IOException
  {
    byte[] pms = new byte[(psk.length + 2) * 2];
    ByteArrayOutputStream bosPms = new ByteArrayOutputStream();
    writeUint16(psk.length, bosPms);
    byte[] padding0 = new byte[psk.length];
    bosPms.write(padding0);
    writeUint16(psk.length, bosPms);
    bosPms.write(psk);
    pms = bosPms.toByteArray();
    return pms;
  }
  
  public static void writeGMTUnixTime(byte[] buf, int offset) {
    int t = (int)(System.currentTimeMillis() / 1000L);
    buf[offset] = ((byte)(t >> 24));
    buf[(offset + 1)] = ((byte)(t >> 16));
    buf[(offset + 2)] = ((byte)(t >> 8));
    buf[(offset + 3)] = ((byte)t);
  }
  
  public static int maxFragmentBytes(short maxFragment)
  {
    switch (maxFragment) {
    case 1: 
      return 512;
    case 2: 
      return 1024;
    case 3: 
      return 2048;
    case 4: 
      return 4096;
    }
    
    return 16385;
  }
  

  public static byte[] concat(byte[] a, byte[] b)
  {
    byte[] c = new byte[a.length + b.length];
    System.arraycopy(a, 0, c, 0, a.length);
    System.arraycopy(b, 0, c, a.length, b.length);
    return c;
  }
  
  public static boolean compareByteArray(byte[] a, byte[] b) {
    if (a.length != b.length)
    {
      return false;
    }
    for (int i = 0; i < a.length; i++) {
      if (a[i] != b[i])
      {


        return false;
      }
    }
    return true;
  }

  public static boolean check(String a, String b)
  {
    if (a.length() != b.length()) {
    	System.out.println("actual value=" + a);
    	System.out.println("expect value=" + b);
      throw new RuntimeException("check error:actual value=" + a + "  ,expect value=" + b);
    }
    
    String aUpper = a.toUpperCase();
    String bUpper = b.toUpperCase().replaceAll("XX", "..");
    boolean flag = aUpper.matches(bUpper);
    if (!flag) {
    	System.out.println("actual value=" + a);
    	System.out.println("expect value=" + b);
      throw new RuntimeException("check error:actual value=" + a + "  ,expect value=" + b);
    }
    return flag;
  }
}
