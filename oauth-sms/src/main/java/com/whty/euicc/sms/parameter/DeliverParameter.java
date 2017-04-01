package com.whty.euicc.sms.parameter;

import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.util.StringUtil;



public class DeliverParameter
  implements SMSConstants
{
  private String MMRUS;
  private String OA = "0C91947122720802";
  private String PID = "7F";
  private String DCS = "F6";
  private String SCTS = "79204090750500";
  private boolean isFormate = true;
  private boolean isCC = true;
  private static DeliverParameter instance = null;

  public String toString() {
    if (this.isCC)
      this.MMRUS = "40";
    else {
      this.MMRUS = "00";
    }
    return this.MMRUS + this.OA + this.PID + this.DCS + this.SCTS;
  }
  public void clear() {
    instance = null;
  }

  public void toObject(String dpv) {
    this.MMRUS = dpv.substring(0, 2);

    int oaLength = StringUtil.hexToByte(dpv.substring(2, 4));

    this.OA = dpv.substring(2, oaLength + 6);
    this.PID = dpv.substring(oaLength + 6, oaLength + 8);
    this.DCS = dpv.substring(oaLength + 8, oaLength + 10);
    this.SCTS = dpv.substring(oaLength + 10, dpv.length());
  }

  public static DeliverParameter getInstance() {
    if (instance == null) {
      instance = new DeliverParameter();
    }
    return instance;
  }

  public String getMMRUS() {
    return this.MMRUS;
  }

  public void setMMRUS(String mMRUS) {
    this.MMRUS = mMRUS;
  }

  public String getOA() {
    return this.OA;
  }

  public void setOA(String oA) {
    this.OA = oA;
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

  public String getSCTS() {
    return this.SCTS;
  }

  public void setSCTS(String sCTS) {
    this.SCTS = sCTS;
  }
  public boolean isFormate() {
    return this.isFormate;
  }
  public void setFormate(boolean isFormate) {
    this.isFormate = isFormate;
  }
  public boolean isCC() {
    return this.isCC;
  }
  public void setCC(boolean isCC) {
    this.isCC = isCC;
  }
}