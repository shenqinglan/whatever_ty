package com.whty.euicc.sms.parameter;


import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.util.StringUtil;

public class SubmitParameter
  implements SMSConstants
{
  private String MRVRUS = "";
  private String MR = "";
  private String DA = "";
  private String PID = "";
  private String DCS = "";
  private static SubmitParameter instance = null;

  public void clear() { instance = null; }

  public String toString() {
    this.MRVRUS = "41";
    this.MR = "00";
    this.DA = "0C91947122720802";
    this.PID = "7F";
    this.DCS = "F6";
    return this.MRVRUS + this.MR + this.DA + this.PID + this.DCS;
  }

  public void toObject(String spv)
  {
    this.MRVRUS = spv.substring(0, 2);

    this.MR = spv.substring(2, 4);

    int oaLength = StringUtil.hexToByte(spv.substring(4, 6));

    this.DA = spv.substring(4, oaLength + 8);
    this.PID = spv.substring(oaLength + 8, oaLength + 10);
    this.DCS = spv.substring(oaLength + 10, spv.length());
  }

  public static SubmitParameter getInstance() {
    if (instance == null) {
      instance = new SubmitParameter();
    }
    return instance;
  }

  public String getMRVRUS() {
    return this.MRVRUS;
  }

  public void setMRVRUS(String mRVRUS) {
    this.MRVRUS = mRVRUS;
  }

  public String getMR() {
    return this.MR;
  }

  public void setMR(String mR) {
    this.MR = mR;
  }

  public String getDA() {
    return this.DA;
  }

  public void setDA(String dA) {
    this.DA = dA;
  }

  public String getPID() {
    return this.PID;
  }

  public void setPID(String pID) {
    this.PID = pID;
  }

  public String getDCS() {
    return this.DCS;
  }

  public void setDCS(String dCS) {
    this.DCS = dCS;
  }
}