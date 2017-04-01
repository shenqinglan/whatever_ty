package com.whty.tls.util;

import java.io.File;
import java.io.UnsupportedEncodingException;

public  class Util
{
  public static String byteToHexString(byte b)
  {
    StringBuffer s = new StringBuffer();

    if ((b & 0xFF) < 16)
      s.append("0");
    s.append(Integer.toHexString(b & 0xFF).toUpperCase());
    return s.toString();
  }

  public static String byteArrayToHexString(byte[] buffer, String separator)
  {
    StringBuffer s = new StringBuffer();

    int i = 0;
    for (i = 0; i < buffer.length - 1; i++)
      s.append(byteToHexString(buffer[i]) + separator);
    
    if (i >= 0)
      s.append(byteToHexString(buffer[i]));

    return s.toString();
  }

  public static byte[] hexStringToByteArray(String s)
  {
    if (s == null)
      return null;
    s = s.replaceAll(" ", "").replaceAll(":", "").replaceAll("0x", "").replaceAll("0X", "");
    if (s.length() % 2 != 0)
      throw new IllegalArgumentException("The length cannot be odd.");
    byte[] output = new byte[s.length() / 2];
    for (int i = 0; i < s.length(); i += 2)
      output[(i / 2)] = ((byte)Integer.parseInt(s.substring(i, i + 2), 16));
    return output;
  }

  public static String hexToASCII(String s)
    throws UnsupportedEncodingException
  {
    byte[] buffer = hexStringToByteArray(s);
    return new String(buffer, "ASCII");
  }

  public static String ASCIIToHex(String s)
  {
    byte[] buffer = s.getBytes();
    return byteArrayToHexString(buffer, ":");
  }

//  public static String Url2Path(URL url, boolean isJar, File output)
//    throws IOException, FileNotFoundException
//  {
//    if ((url.openConnection() instanceof JarURLConnection))
//    {
//      JarFile jarFile = ((JarURLConnection)url.openConnection()).getJarFile();
//      JarEntry fileInJarFile = (JarEntry)jarFile.getEntry(
//        url.getFile().substring(url.getFile().indexOf("!") + 2));
//      InputStream in = jarFile.getInputStream(fileInJarFile);
//
//      if (isJar) {
//        in = new JarInputStream(in);
//      }
//      String fileInJarName = fileInJarFile.getName();
//      fileInJarName = fileInJarName.substring(fileInJarName.lastIndexOf("/") + 1);
//      int n = fileInJarName.lastIndexOf('.');
//      File fileInJarCopy = null;
//
//      if (output != null) {
//        fileInJarCopy = File.createTempFile(fileInJarName.substring(0, n), 
//          fileInJarName.substring(n), output);
//      }
//      else
//      {
//        fileInJarCopy = File.createTempFile(fileInJarName.substring(0, n), 
//          fileInJarName.substring(n));
//      }
//      fileInJarCopy.deleteOnExit();
//
//      OutputStream out = new FileOutputStream(fileInJarCopy);
//
//      if (isJar) {
//        out = new JarOutputStream(out);
//        byte[] buffer;
//        int length;
//        JarEntry jarEntry;
//        for (; (jarEntry = ((JarInputStream)in).getNextJarEntry()) != null; 
//          (length = in.read(buffer)) > 0)
//        {
//          JarEntry jarEntry;
//          long size = jarEntry.getSize();
//          byte[] buffer;
//          if (size == -1L)
//            buffer = new byte[2048];
//          else
//            buffer = new byte[(int)jarEntry.getSize()];
//          ((JarOutputStream)out).putNextEntry(new JarEntry(jarEntry));
//          continue;
//          int length;
//          out.write(buffer, 0, length);
//        }
//
//      }
//      else
//      {
//        byte[] buffer = new byte[in.available()];
//        int length;
//        while ((length = in.read(buffer)) > 0)
//        {
//          int length;
//          out.write(buffer, 0, length);
//        }
//      }
//      in.close();
//      out.close();
//
//      return fileInJarCopy.getPath();
//    }
//
//    return URLDecoder.decode(url.getPath(), "UTF-8");
//  }

  public static void delete(File f)
  {
    if (f.isDirectory()) {
      File[] children = f.listFiles();
      for (int i = 0; i < children.length; i++)
        delete(children[i]);
    }
    f.delete();
  }

  public static String leng(String hexStr) {
    byte[] leng = hexStringToByteArray(hexStr);
    int l = leng.length;
    String hexLen = byteToHexString(intToByte(l)[0]);
    return hexLen;
  }

  public static byte[] longToByte(long number)
  {
    long temp = number;
    byte[] b = new byte[8];
    for (int i = 0; i < b.length; i++) {
      b[i] = new Long(temp & 0xFF).byteValue();
      temp >>= 8;
    }
    return b;
  }

  public static long byteToLong(byte[] b)
  {
    long s = 0L;
    long s0 = b[0] & 0xFF;
    long s1 = b[1] & 0xFF;
    long s2 = b[2] & 0xFF;
    long s3 = b[3] & 0xFF;
    long s4 = b[4] & 0xFF;
    long s5 = b[5] & 0xFF;
    long s6 = b[6] & 0xFF;
    long s7 = b[7] & 0xFF;

    s1 <<= 8;
    s2 <<= 16;
    s3 <<= 24;
    s4 <<= 32;
    s5 <<= 40;
    s6 <<= 48;
    s7 <<= 56;
    s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
    return s;
  }

  public static byte[] intToByte(int number)
  {
    int temp = number;
    byte[] b = new byte[4];
    for (int i = 0; i < b.length; i++) {
      b[i] = new Integer(temp & 0xFF).byteValue();
      temp >>= 8;
    }
    return b;
  }

  public static int byteToInt(byte[] b)
  {
    int s = 0;
    int s0 = b[0] & 0xFF;
    int s1 = b[1] & 0xFF;
    int s2 = b[2] & 0xFF;
    int s3 = b[3] & 0xFF;
    s3 <<= 24;
    s2 <<= 16;
    s1 <<= 8;
    s = s0 | s1 | s2 | s3;
    return s;
  }

  public static byte[] shortToByte(short number)
  {
    int temp = number;
    byte[] b = new byte[2];
    for (int i = 0; i < b.length; i++) {
      b[i] = new Integer(temp & 0xFF).byteValue();
      temp >>= 8;
    }
    return b;
  }

  public static String shortToHexStr(short number) {
    byte[] temp = shortToByte(number);
    String hexStr = byteArrayToHexString(temp, "");
    return hexStr;
  }

  public static short byteToShort(byte[] b)
  {
    short s = 0;
    short s0 = (short)(b[0] & 0xFF);
    short s1 = (short)(b[1] & 0xFF);
    s1 = (short)(s1 << 8);
    s = (short)(s0 | s1);
    return s;
  }
  public static byte[] toByteArray(String str) {
    char[] chars = str.toCharArray();
    byte[] bytes = new byte[chars.length];

    for (int i = 0; i != bytes.length; i++) {
      bytes[i] = ((byte)chars[i]);
    }

    return bytes;
  }
}
