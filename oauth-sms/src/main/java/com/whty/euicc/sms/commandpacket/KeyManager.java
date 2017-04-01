package com.whty.euicc.sms.commandpacket;

/**
 * 密钥临时存储
 * @author Administrator
 *
 */
public class KeyManager
{
  public static String[] kicKey = { 
    "", 
    "11223344556677889910111213141516",
    "D9BE655005BEB775324D53DEA699F13A", 
    "D9BE655005BEB775324D53DEA699F13A", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "A1A2A3A4A5A6A7A8", 
    "01230123012301233210321032103210", 
    "111111111111111122222222222222223333333333333333", 
    "01230123012301233210321032103210", 
    "111111111111111122222222222222223333333333333333", 
    "01230123012301233210321032103210", 
    "111111111111111122222222222222223333333333333333", 
    "AAAAAAAAAAAAAAAA" };

  public static String[] dekKey = { 
    "", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "B1B2B3B4B5B6B7B8", 
    "32103210321032100123012301230123", 
    "010101010101010102020202020202020303030303030303", 
    "32103210321032100123012301230123", 
    "010101010101010102020202020202020303030303030303", 
    "32103210321032100123012301230123", 
    "010101010101010102020202020202020303030303030303", 
    "EEEEEEEEEEEEEEEE" };

  public static String[] kidKey = { 
    "", 
    "11223344556677889910111213141516",
    "52DABF691AFD0E2B540CA6A09DEE3BF9", 
    "52DABF691AFD0E2B540CA6A09DEE3BF9", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "0123456789ABCDEF", 
    "B1B2B3B4B5B6B7B8", 
    "32103210321032100123012301230123", 
    "010101010101010102020202020202020303030303030303", 
    "32103210321032100123012301230123", 
    "010101010101010102020202020202020303030303030303", 
    "32103210321032100123012301230123", 
    "010101010101010102020202020202020303030303030303", 
    "EEEEEEEEEEEEEEEE" };

  public static String[] getKicKey()
  {
    return kicKey;
  }

  public static void setKicKey(String[] kicKey)
  {
    kicKey = kicKey;
  }

  public static String[] getKidKey()
  {
    return kidKey;
  }

  public static void setKidKey(String[] kidKey)
  {
    kidKey = kidKey;
  }

  public static String getKicKey(int keyId)
  {
    return kicKey[keyId];
  }

  public static void setKicKey(int keyId, String keyValue)
  {
    kicKey[keyId] = keyValue;
  }

  public static String getKidKey(int keyId)
  {
    return kidKey[keyId];
  }

  public static void setKidKey(int keyId, String keyValue)
  {
    kidKey[keyId] = keyValue;
  }

  public static String[] getDekKey() {
    return dekKey;
  }

  public static void setDekKey(String[] dekKey) {
    dekKey = dekKey;
  }
}