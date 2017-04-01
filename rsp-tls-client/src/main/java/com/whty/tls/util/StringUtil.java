package com.whty.tls.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil
{
  static final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', 
    '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 
    'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 
    'z' };

  public static final String NEW_LINE = System.getProperty("line.separator");

  public static int arrayFill(byte[] abyte0, int offSet, int padLength, byte byte0)
  {
    while (padLength-- > 0) {
      abyte0[(offSet++)] = byte0;
    }
    return offSet;
  }

  public static byte[] asciiStringToByteArray(String s)
  {
    s = s.substring(0, s.length());
    return s.getBytes();
  }

  public static byte binaryToByte(String binary)
    throws StringFormatException
  {
    if (binary.length() != 8) {
      throw new StringFormatException("字符串长度不为8");
    }

    return (byte)Integer.parseInt(binary, 2);
  }

  public static String binaryToHex(String binary)
    throws StringFormatException
  {
    if (binary.length() % 8 != 0) {
      throw new StringFormatException("字符串长度不为8的整数倍");
    }
    int num = binary.length() / 8;
    StringBuilder res = new StringBuilder();

    for (int i = 0; i < num; i++) {
      String bin = binary.substring(i * 8, i * 8 + 8);
      byte b = binaryToByte(bin);
      String hex = byteToHex(b);
      res.append(hex);
    }
    return res.toString();
  }

  public static String bufferCharToHex(char[] buffer)
  {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < buffer.length; i++) {
      res.append(charToHex(buffer[i]));
    }
    return res.toString();
  }

  public static String bufferCharToHexWithOutHigh(char[] buffer)
  {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < buffer.length; i++) {
      res.append(charToHexWithoutHigh(buffer[i]));
    }
    return res.toString();
  }

  public static String bufferIntToHex(int[] buffer)
  {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < buffer.length; i++) {
      res.append(intToHex(buffer[i]));
    }
    return res.toString();
  }

  public static String bufferIntToHexWithOutHigh(int[] buffer)
  {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < buffer.length; i++) {
      res.append(intToHexWithHigh(buffer[i]));
    }
    return res.toString();
  }

  public static String bufferShortToHex(short[] buffer, int start, int end)
  {
    StringBuilder res = new StringBuilder();
    for (int i = start; i < end; i++) {
      res.append(shortToHex(buffer[i]));
    }
    return res.toString();
  }

  public static String bufferShortToHexWithOutHigh(short[] buffer, int start, int end)
  {
    StringBuilder res = new StringBuilder();
    for (int i = start; i < end; i++) {
      res.append(shortToHexWithoutHigh(buffer[i]));
    }
    return res.toString();
  }

  public static String bufferToHex(byte[] buffer)
  {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < buffer.length; i++) {
      res.append(byteToHex(buffer[i]));
    }
    return res.toString();
  }

  public static String bufferToHex(byte[] buffer, int start, int end)
  {
    StringBuilder res = new StringBuilder();
    for (int i = start; i < end; i++) {
      res.append(byteToHex(buffer[i]));
    }
    return res.toString();
  }

  public static String byteArrayToReadableString(byte[] array)
  {
    if (array == null) {
      return "NULL";
    }
    StringBuffer s = new StringBuffer();
    for (int i = 0; i < array.length; i++) {
      char c = (char)array[i];
      s.append((c >= ' ') && (c < '') ? Character.valueOf(c) : ".");
    }
    return "|" + s + "|";
  }

  public static char[] bytesToCharsWithHigh00(byte[] bytes)
  {
    char[] chars = new char[bytes.length];
    for (int i = 0; i < bytes.length; i++) {
      chars[i] = ((char)(bytes[i] & 0xFF));
    }
    return chars;
  }

  public static String byteToBinary(byte byteValue)
  {
    char[] buf = new char[8];
    int charPos = 8;
    byte mask = 1;
    do
    {
      buf[(--charPos)] = digits[(byteValue & mask)];
      byteValue = (byte)((byteValue & 0xFF) >>> 1);
    }while (byteValue != 0);

    return new String(buf, charPos, 8 - charPos);
  }

  public static String byteToHex(byte byteValue)
  {
    String hs = "";
    String tmp = "";

    tmp = Integer.toHexString(byteValue & 0xFF);
    if (tmp.length() == 1)
      hs = hs + "0" + tmp;
    else {
      hs = hs + tmp;
    }
    tmp = null;
    return hs.toUpperCase();
  }

  public static byte[] charsToBytes(char[] chars)
  {
    byte[] bytes = new byte[chars.length];
    for (int i = 0; i < chars.length; i++) {
      bytes[i] = ((byte)(chars[i] & 0xFF));
    }
    return bytes;
  }

  public static String charToHex(char s)
  {
    StringBuilder res = new StringBuilder();
    byte b = (byte)((s & 0xFF00) >>> '\b');
    res.append(byteToHex(b));
    b = (byte)(s & 0xFF);
    res.append(byteToHex(b));
    return res.toString();
  }

  public static String charToHexWithoutHigh(char s)
  {
    StringBuilder res = new StringBuilder();
    byte b = (byte)((s & 0xFF00) >>> '\b');
    b = (byte)(s & 0xFF);
    res.append(byteToHex(b));
    return res.toString();
  }

  public static int cmp(byte[] abyte0, byte[] abyte1)
  {
    if (abyte0.length != abyte1.length)
      return 1;
    for (int i = 0; i < abyte0.length; i++) {
      if (abyte0[i] != abyte1[i])
        return 1;
    }
    return 0;
  }

  public static int cmp(byte[] abyte0, int arrayOneStart, byte[] abyte1, int arrayTwoStart, int arrayLength)
  {
    int l = 0;
    int i1 = Math.min(abyte0.length - arrayOneStart, abyte1.length - 
      arrayTwoStart);
    if (i1 < arrayLength) {
      l = abyte0.length - abyte1.length;
      arrayLength = i1;
    }
    do arrayLength--; while ((arrayLength >= 0) && 
      ((i1 = abyte0[(arrayOneStart + arrayLength)] - 
      abyte1[(arrayTwoStart + arrayLength)]) == 0));

    return i1 != 0 ? i1 : l;
  }

  public static int cmp(char[] char0, char[] char1)
  {
    if (char0.length != char1.length)
      return 1;
    for (int i = 0; i < char0.length; i++) {
      if (char0[i] != char1[i])
        return 1;
    }
    return 0;
  }

  public static short getShort(byte[] buffer, int offset)
  {
    short high = (short)((short)(0xFF & buffer[offset]) << 8);
    short low = (short)(0xFF & buffer[(offset + 1)]);

    return (short)(high ^ low);
  }

  public static String hexAdd(String hex1, String hex2)
    throws StringFormatException
  {
    if ((hex1.length() > 4) || (hex2.length() > 4)) {
      throw new StringFormatException("字符串参数的长度不能大于4");
    }
    int value1 = Integer.parseInt(hex1, 16);
    int value2 = Integer.parseInt(hex2, 16);

    int resValue = value1 + value2;

    return intToHex(resValue);
  }

  public static String hexAddLow(String hex1, String hex2)
    throws StringFormatException
  {
    String resValue = hexAdd(hex1, hex2);
    resValue = resValue.substring(resValue.length() - 2, resValue.length());
    return resValue;
  }

  public static String hexDataPad(String hexData, String pad, int radix, boolean backward)
  {
    if (hexData.length() % radix != 0) {
      if (backward)
        hexData = hexData + pad.substring(0, radix - hexData.length() % radix);
      else
        hexData = pad.substring(0, radix - hexData.length() % radix) + 
          hexData;
    }
    return hexData;
  }

  public static String hexToBinary(String hex)
  {
    hex = hex.toUpperCase();
    StringBuilder result = new StringBuilder();
    int max = hex.length();
    for (int i = 0; i < max; i++) {
      char c = hex.charAt(i);
      switch (c) {
      case '0':
        result.append("0000");
        break;
      case '1':
        result.append("0001");
        break;
      case '2':
        result.append("0010");
        break;
      case '3':
        result.append("0011");
        break;
      case '4':
        result.append("0100");
        break;
      case '5':
        result.append("0101");
        break;
      case '6':
        result.append("0110");
        break;
      case '7':
        result.append("0111");
        break;
      case '8':
        result.append("1000");
        break;
      case '9':
        result.append("1001");
        break;
      case 'A':
        result.append("1010");
        break;
      case 'B':
        result.append("1011");
        break;
      case 'C':
        result.append("1100");
        break;
      case 'D':
        result.append("1101");
        break;
      case 'E':
        result.append("1110");
        break;
      case 'F':
        result.append("1111");
      case ':':
      case ';':
      case '<':
      case '=':
      case '>':
      case '?':
      case '@': }  } return result.toString();
  }

  public static byte[] hexToBuffer(String hex)
    throws StringFormatException
  {
    if ((hex == null) || ("".equals(hex))) {
      return new byte[0];
    }
    if (hex.length() % 2 != 0) {
      throw new StringFormatException("字符串的长度不为偶数：" + hex);
    }
    int len = hex.length() / 2;
    byte[] byteArray = new byte[len];
    int index = 0;
    for (int i = 0; i < len; i++) {
      byteArray[i] = ((byte)Integer.parseInt(
        hex.substring(index, index + 2), 16));
      index += 2;
    }
    return byteArray;
  }

  public static byte hexToByte(String hex)
    throws StringFormatException
  {
    byte res = 0;

    if (hex.length() != 2) {
      throw new StringFormatException("字符串长度不为2");
    }
    res = (byte)Integer.parseInt(hex, 16);

    return res;
  }

  public static char[] hexToCharBuffer(String hex)
    throws StringFormatException
  {
    if (hex == null) {
      return null;
    }
    if ("".equals(hex)) {
      return new char[0];
    }

    if (hex.length() % 2 != 0) {
      throw new StringFormatException("字符串的长度不为偶数：" + hex);
    }
    int len = hex.length() / 2;
    char[] byteArray = new char[len];
    int index = 0;
    for (int i = 0; i < len; i++) {
      byteArray[i] = ((char)Integer.parseInt(
        hex.substring(index, index + 2), 16));
      index += 2;
    }
    return byteArray;
  }

  public static int hexToInt(String hex)
    throws StringFormatException
  {
    if (hex.length() != 8) {
      throw new StringFormatException("长度不为8");
    }

    int high = Integer.parseInt(hex.substring(0, 4), 16);

    int low = Integer.parseInt(hex.substring(4, 8), 16);

    int res = high << 16 ^ low;
    return res;
  }

  public static short hexToShort(byte[] hex)
    throws StringFormatException
  {
    return hexToShort(bufferToHex(hex));
  }

  public static short hexToShort(char[] hex)
    throws StringFormatException
  {
    return hexToShort(bufferCharToHexWithOutHigh(hex));
  }

  public static short hexToShort(String hex)
    throws StringFormatException
  {
    if (hex.length() != 4) {
      throw new StringFormatException("字符串长度不为4");
    }
    short res = 0;
    res = (short)Integer.parseInt(hex, 16);
    return res;
  }

  public static short[] hexToShortBuffer(String hex)
    throws StringFormatException
  {
    if ((hex == null) || ("".equals(hex))) {
      return new short[0];
    }
    if (hex.length() % 2 != 0) {
      throw new StringFormatException("字符串的长度不为偶数：" + hex);
    }
    int len = hex.length() / 2;
    short[] byteArray = new short[len];
    int index = 0;
    for (int i = 0; i < len; i++) {
      byteArray[i] = ((short)Integer.parseInt(
        hex.substring(index, index + 2), 16));
      index += 2;
    }
    return byteArray;
  }

  public static byte[] intToBytes(int value)
  {
    byte[] result = new byte[2];
    result[1] = ((byte)value);
    result[0] = ((byte)(value >> 8));
    return result;
  }

  public static char[] intToChars(int value)
  {
    char[] result = new char[2];
    result[1] = ((char)value);
    result[0] = ((char)(value >> 8));
    return result;
  }

  public static String intToHex(int value)
  {
    byte[] b = new byte[4];

    b[0] = ((byte)((value & 0xFF000000) >>> 24));
    b[1] = ((byte)((value & 0xFF0000) >>> 16));
    b[2] = ((byte)((value & 0xFF00) >>> 8));
    b[3] = ((byte)(value & 0xFF));

    return bufferToHex(b);
  }

  public static String intToHexWithHigh(int value)
  {
    byte b = (byte)(value & 0xFF);

    return byteToHex(b);
  }

  public static String intToHexWithoutHigh(int s)
  {
    StringBuilder res = new StringBuilder();
    byte b = (byte)((s & 0xFF00) >>> 8);

    b = (byte)(s & 0xFF);
    res.append(byteToHex(b));
    return res.toString();
  }

  public static boolean isHexStr(String str)
  {
    if (str == null) {
      return false;
    }
    String regEx = "\\b([0-9a-fA-F][0-9a-fA-F])*";
    Pattern p = Pattern.compile(regEx);
    Matcher matcher = p.matcher(str);
    return matcher.matches();
  }

  public static byte[] joinBytes(byte[] b1, byte[] b2)
  {
    int length = b1.length + b2.length;
    byte[] newer = new byte[length];
    for (int i = 0; i < b1.length; i++) {
      newer[i] = b1[i];
    }
    for (int i = 0; i < b2.length; i++) {
      newer[(i + b1.length)] = b2[i];
    }
    return newer;
  }

  public static char[] joinChars(char[] b1, char[] b2)
  {
    int length = b1.length + b2.length;
    char[] newer = new char[length];
    for (int i = 0; i < b1.length; i++) {
      newer[i] = b1[i];
    }
    for (int i = 0; i < b2.length; i++) {
      newer[(i + b1.length)] = b2[i];
    }
    return newer;
  }

  public static String ltrimStr(String content, String s)
  {
    int i = 0;
    int pos = 0;
    while (true) {
      int x = content.indexOf(s, i);
      if ((x != 0) && ((x == -1) || (x - pos != s.length())))
        break;
      i = x + s.length();
      pos = x;
    }
    return content.substring(i);
  }

  public static short makeShort(byte firstByte, byte secondByte)
  {
    short high = (short)((short)(0xFF & firstByte) << 8);
    short low = (short)(secondByte & 0xFF);
    short res = (short)(high ^ low);
    return res;
  }

  public static short makeShort(byte[] bytes)
  {
    short high = (short)((short)(0xFF & bytes[0]) << 8);
    short low = (short)(bytes[1] & 0xFF);
    short res = (short)(high ^ low);
    return res;
  }

  public static short makeShort(char firstByte, char secondByte)
  {
    short high = (short)((short)(0xFF & firstByte) << 8);
    short low = (short)(secondByte & 0xFF);
    short res = (short)(high ^ low);
    return res;
  }

  public static short makeShort(char[] bytes)
  {
    short high = (short)((short)(0xFF & bytes[0]) << 8);
    short low = (short)(bytes[1] & 0xFF);
    short res = (short)(high ^ low);
    return res;
  }

  public static String printHexString(byte[] input)
  {
    StringBuffer hext = new StringBuffer();
    for (int i = 0; i < input.length; i++)
    {
      String hex = Integer.toHexString(input[i] & 0xFF);
      if (hex.length() == 1) {
        hex = '0' + hex;
      }

      hext.append(hex.toLowerCase());
    }
    return hext.toString();
  }

  public static byte[] readableStringToByteArray(String s)
  {
    if ((!s.startsWith("|")) && (!s.endsWith("|")))
      return null;
    s = s.substring(1, s.length() - 1);
    return s.getBytes();
  }

  public static String rtrimStr(String content, String s)
  {
    int i = content.length();
    int pos = i;
    while (true) {
      int x = content.lastIndexOf(s, i - s.length());
      if ((x == -1) || (pos - x != s.length()))
        break;
      i = x;
      pos = x;
    }
    return content.substring(0, i);
  }

  public static String shortBufferToByteHex(short[] buffer)
  {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < buffer.length; i++) {
      res.append(shortToHex(buffer[i]).subSequence(1, 3));
    }
    return res.toString();
  }

  public static String shortBufferToByteHex(short[] buffer, int start, int end)
  {
    StringBuilder res = new StringBuilder();
    for (int i = start; i < end; i++) {
      res.append(shortToHex(buffer[i]).subSequence(1, 3));
    }
    return res.toString();
  }

  public static String shortBufferToHex(short[] buffer)
  {
    StringBuilder res = new StringBuilder();
    for (int i = 0; i < buffer.length; i++) {
      res.append(shortToHex(buffer[i]));
    }
    return res.toString();
  }

  public static String shortToHex(short s)
  {
    StringBuilder res = new StringBuilder();
    byte b = (byte)((s & 0xFF00) >>> 8);
    res.append(byteToHex(b));
    b = (byte)(s & 0xFF);
    res.append(byteToHex(b));
    return res.toString();
  }

  public static String shortToHexWithoutHigh(short s)
  {
    StringBuilder res = new StringBuilder();
    byte b = (byte)((s & 0xFF00) >>> 8);

    b = (byte)(s & 0xFF);
    res.append(byteToHex(b));
    return res.toString();
  }

  public static String strArrayToString(String[] strArray)
  {
    StringBuffer combinedString = new StringBuffer();
    if (strArray != null) {
      int i = 0;
      for (i = 0; i < strArray.length - 1; i++) {
        combinedString.append(strArray[i] + " ");
      }
      combinedString.append(strArray[i]);
    }
    return combinedString.toString();
  }

  public static char[][] stringsToChars(String[] strings)
  {
    if (strings == null) {
      return null;
    }
    char[][] data = new char[strings.length][];
    for (int i = 0; i < strings.length; i++) {
      data[i] = hexToCharBuffer(strings[i]);
    }

    return data;
  }

  public static byte[] stringToByteArray(String s)
  {
    Vector v = new Vector();
    String operate = s;
    operate = operate.replaceAll(" ", "");
    operate = operate.replaceAll("\t", "");
    operate = operate.replaceAll("\n", "");
    if (operate.endsWith(";"))
      operate = operate.substring(0, operate.length() - 1);
    if (operate.length() % 2 != 0)
      return null;
    int num = 0;
    while (operate.length() > 0) {
      try {
        num = Integer.parseInt(operate.substring(0, 2), 16);
      } catch (NumberFormatException nfe) {
        return null;
      }
      v.add(Integer.valueOf(num));
      operate = operate.substring(2);
    }
    byte[] result = new byte[v.size()];
    Iterator it = v.iterator();
    int i = 0;
    while (it.hasNext())
      result[(i++)] = ((Integer)it.next()).byteValue();
    return result;
  }

  public static byte[] toByteArray(String hexString)
  {
    try
    {
      if (hexString.length() % 2 == 0) {
        int len = hexString.length() / 2;
        byte[] byteArray = new byte[len];
        int index = 0;
        for (int i = 0; i < len; i++) {
          try {
            byteArray[i] = ((byte)Integer.parseInt(
              hexString.substring(index, index + 2), 16));
            index += 2;
          } catch (NumberFormatException e) {
            return null;
          }
        }
        return byteArray;
      }
      return null; } catch (NullPointerException e) {
    }
    return null;
  }

  public static String toString(byte[] a)
  {
    return toString(a, 0, a.length);
  }

  public static String toString(byte[] a, int format) {
    return toString(a, 0, a.length, format);
  }

  public static String toString(byte[] a, int off, int len) {
    return toString(a, off, len, 0);
  }

  public static String toString(byte[] a, int off, int len, int format) {
    if (a == null)
      return "";
    if (format == 4) {
      StringBuffer buf = new StringBuffer();
      buf.append('|');
      for (int i = 0; i < len; i++) {
        byte b = a[(i + off)];
        if ((b < 32) || (b > 126) || (b == 124)) {
          format = 0;
          break;
        }
        buf.append((char)b);
      }

      return buf.toString();
    }
    StringBuffer b = new StringBuffer();
    for (int i = 0; i < len; i++) {
      if (format == 2)
        b.append("0x");
      if ((format != 1) || ((a[(i + off)] & 0xF0) != 0))
        b.append(Integer.toHexString((a[(i + off)] & 0xF0) >> 4));
      b.append(Integer.toHexString(a[(i + off)] & 0xF));
      if ((format == 1) || ((format == 2) && (i < len - 1)))
        b.append(':');
      else if ((format == 3) && (i < len - 1)) {
        b.append(' ');
      }
    }
    return b.toString();
  }

  public static String[] splitStrWithLength(String dealStr, int splitNum)
  {
    int index = 0;
    String tempStr = "";
    List tempList = new ArrayList();
    try {
      while (true) {
        tempStr = dealStr.substring(index, index + splitNum * 2);
        index += splitNum * 2;
        tempList.add(tempStr);
      }
    } catch (Exception e) { tempStr = dealStr.substring(index, dealStr.length());
      tempList.add(tempStr);

      String[] resultList = new String[tempList.size()];
      tempList.toArray(resultList);
      return resultList;
    }
  }
}
