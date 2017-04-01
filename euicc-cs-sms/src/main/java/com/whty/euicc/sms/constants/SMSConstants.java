package com.whty.euicc.sms.constants;

/**
 * 静态变量集合
 * @author Administrator
 *
 */
public abstract interface SMSConstants
{
  public static final String SMS_PP_DELIVER_TAG = "D1";
  public static final String DEVICE_IDENTITIES_TAG = "82";
  public static final String DEVICE_IDENTITIES_SRC = "83";
  public static final String DEVICE_IDENTITIES_DST = "81";
  public static final String TS_SCA_TAG = "06";
  public static final String TS_SCA_VALUE = "91947122720000";
  public static final String TPDU_TAG = "8B";
  public static final int MAX_UDL = 140;
  public static final String IEIa_NOTCON = "70";
  public static final String IEIDLa_NOTCON = "00";
  public static final String IEIa_CON = "00";
  public static final String IEIDLa_CON = "03";
  public static final String IEIb_CON = "70";
  public static final String IEIDLb_CON = "00";
  public static final String IEIa_08CON = "08";
  public static final String IEIDLa_08CON = "04";
  public static final String SMS_CB_TAG = "D2";
  public static final String CBP_TAG = "8C";
  public static final String CBP_SN = "A750";
  public static final String CBP_MID = "1080";
  public static final String CBP_DCS = "56";
  public static final String CBP_PP = "00";
  public static final String CBP_MID_FORMATTED_RANGE_L = "1080";
  public static final String CBP_MID_FORMATTED_RANGE_R = "109F";
  public static final String CBP_MID_UNFORMATTED_RANGE1_L = "0000";
  public static final String CBP_MID_UNFORMATTED_RANGE1_R = "03E7";
  public static final String CBP_MID_UNFORMATTED_RANGE2_L = "1000";
  public static final String CBP_MID_UNFORMATTED_RANGE2_R = "107F";
  public static final String CBP_MID_UNFORMATTED_RANGE3_L = "10A0";
  public static final String CBP_MID_UNFORMATTED_RANGE3_R = "10FF";
  public static final String DELIVER_MMRUS_FOR = "40";
  public static final String DELIVER_MMRUS_UNFOR = "00";
  public static final String DELIVER_OA = "0C91947122720802";
  public static final String DELIVER_PID = "7F";
  public static final String DELIVER_DCS = "F6";
  public static final String DELIVER_SCTS = "79204090750500";
  public static final String SUBMIT_MRVRUS = "41";
  public static final String SUBMIT_MR = "00";
  public static final String SUBMIT_DA = "0C91947122720802";
  public static final String SUBMIT_PID = "7F";
  public static final String SUBMIT_DCS = "F6";
  public static final int NO_RC_CC_DS = 0;
  public static final int NO_RC_CC_DS_LEN = 0;
  public static final int RC_CRC_16 = 1;
  public static final int RC_CRC_16_LEN = 2;
  public static final int RC_CRC_32 = 2;
  public static final int RC_CRC_32_LEN = 4;
  public static final int CC_SINGLE_DES_CBC = 3;
  public static final int CC_TRIPLE_DES_CBC_2KEY = 4;
  public static final int CC_TRIPLE_DES_CBC_3KEY = 5;
  public static final int CC_DES_LEN = 8;
  public static final int CIPHER_SINGLE_DES_CBC = 1;
  public static final int CIPHER_TRIPLE_DES_CBC_2KEY = 2;
  public static final int CIPHER_TRIPLE_DES_CBC_3KEY = 3;
  public static final int CIPHER_SINGLE_DES_ECB = 4;
  public static final String SMS_PP_UDP_STATUS = "03";
  public static final String SMS_PP_UPD_TSSCA_VALUE = "91947122720000";
  public static final int CASE_DELIVER_DOWNLOAD = 1;
  public static final int CASE_DELIVER_UPD = 2;
  
  public static final String ivString = "00000000000000000000000000000000";
  public static final String UDH = "7000";//短信中取固定值7000
  public static final String SPI_NO_POR = "1600";//不需要收条，加密并算mac时取值
  public static final String SPI_POR_ENC = "1639";//需要收条时取值
  public static final String SPI_POR = "1601";//需要收条，加密并算mac时取值
  public static final String KIC = "12";//前半字节表示密钥顺序，后半字节表示使用的加密算法（此处为AES-CBC）
  public static final String KID = "12";//前半字节表示密钥顺序，后半字节表示使用的加密算法（此处为AES-CMAC）
  public static final String TAR = "000001";//取值唯一标识一个应用
  public static final String CNTR = "0000000004";//计数器的值
  public static final int SPLIT_NUM = -1;//当其值大于1，表示短信为级联短信
}