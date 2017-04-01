package com.whty.euicc.cipher;

import java.util.zip.CRC32;

public class CRC
{
  public static String CRC16(byte[] data)
  {
    int crc = 0;
    int carry = 0;
    String temp = "";
    crc = 65535;
    for (int i = 0; i < data.length; i++) {
      for (int bit = 0; bit < 8; bit++) {
        carry = crc & 0x1;
        crc >>= 1;
        if ((carry ^ data[i] >> bit & 0x1) != 0) {
          crc ^= 33800;
        }
      }
    }
    temp = Integer.toHexString((short)(crc ^ 0xFFFFFFFF));
    if (temp.length() % 2 != 0) {
      temp = "0" + temp;
    }
    temp = temp.substring(temp.length() - 4, temp.length());
    return temp;
  }

  public static String CRC32(byte[] data)
  {
    CRC32 crc32 = new CRC32();
    crc32.update(data);
    return Long.toHexString(crc32.getValue());
  }
}