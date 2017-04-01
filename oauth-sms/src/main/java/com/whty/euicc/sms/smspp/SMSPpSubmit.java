package com.whty.euicc.sms.smspp;


import java.util.ArrayList;
import java.util.List;

import com.whty.euicc.util.StringUtil;

public class SMSPpSubmit
{
  private static SMSPpSubmit instance = null;
  private static SMSPpResponse response = null;
  private String MMRUS = "";
  private String DA = "";

  public void clear() { response.clear();
    instance = null; }

  public static SMSPpSubmit getInstance() {
    if (instance == null) {
      instance = new SMSPpSubmit();
    }
    return instance;
  }

  public void toObject(String submitv) {
    String dealedStr = getSubmitValueOfTLV(submitv);
    response = SMSPpResponse.getInstance();
    this.MMRUS = dealedStr.substring(0, 2);
    int daLength = StringUtil.hexToByte(dealedStr.substring(4, 6));
    this.DA = dealedStr.substring(4, daLength + 8);
    response.toObject(dealedStr.substring(daLength + 8, dealedStr.length()));
  }

  private static String getSubmitValueOfTLV(String data)
  {
    List firstLayoutValue = new ArrayList();
    List otherLayoutValue = new ArrayList();
    String result = "";
    firstLayoutValue = anySMSTLV("D0", data);

    for (int i = 0; i < firstLayoutValue.size(); i++) {
      List tempValueList = anySMSTLV("8B", (String)firstLayoutValue.get(i));
      for (int j = 0; j < tempValueList.size(); j++) {
        otherLayoutValue.add((String)tempValueList.get(j));
      }
    }
    String str1 = (String)otherLayoutValue.get(0);
    if (1 == otherLayoutValue.size()) {
      result = str1;
    }
    else {
      int offsetLength = Integer.parseInt(str1.substring(4, 6), 16);
      offsetLength += 14;

      String subHead = str1.substring(0, offsetLength);
      String subValue = "";
      for (int k = 0; k < otherLayoutValue.size(); k++) {
        subValue = subValue + ((String)otherLayoutValue.get(k)).substring(offsetLength + 12, ((String)otherLayoutValue.get(k)).length());
      }
      result = subHead + "02" + subValue;
    }
    return result;
  }

  private static List<String> anySMSTLV(String tag, String data) {
    String tempTag = "";
    String tempLength = "";
    String tempValue = "";
    int partLength = 0;
    int startIndex = 0;
    List valueList = new ArrayList();
    while (partLength < data.length())
    {
      tempTag = data.substring(startIndex, startIndex + 2);
      tempLength = data.substring(startIndex + 2, startIndex + 4);
      if (tempLength.equals("81")) {
        tempLength = data.substring(startIndex + 4, startIndex + 6);
        tempValue = data.substring(startIndex + 6, startIndex + 6 + Integer.parseInt(tempLength, 16) * 2);
        partLength += (tempTag + "81" + tempLength + tempValue).length();
      } else {
        tempValue = data.substring(startIndex + 4, startIndex + 4 + Integer.parseInt(tempLength, 16) * 2);
        partLength += (tempTag + tempLength + tempValue).length();
      }
      startIndex = partLength;
      if (tempTag.equals(tag)) {
        valueList.add(tempValue);
      }
    }
    return valueList;
  }

  public String getMMRUS() {
    return this.MMRUS;
  }

  public String getDA() {
    return this.DA;
  }
}