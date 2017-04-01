package com.whty.euicc.sms.tssca;

import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.exception.SMSRuntimeException;
import com.whty.euicc.sms.util.SMSUtil;
import com.whty.euicc.sms.util.TLVBean;




public class TsSca
  implements SMSConstants
{
  private String T = "";
  private String L = "";
  private String V = "";
  private boolean isDefaultValue = true;
  private static TsSca instance = null;

  public void clear() {
    instance = null;
  }
  public String toString() {
    if (this.isDefaultValue) {
      this.T = "06";
      this.V = "91947122720000";
      this.L = SMSUtil.getLengthHexStr(this.V);
    }
    else {
      if ("".equals(this.V)) {
        throw new SMSRuntimeException("Error:TS-SCA的Tag或者Value没有值");
      }
      this.L = SMSUtil.getLengthHexStr(this.V);
    }
    return this.T + this.L + this.V;
  }

  public void toObject(String tsv)
  {
    TLVBean tlv = new TLVBean(tsv);
    this.T = tlv.getT();
    this.L = tlv.getL();
    this.V = tlv.getV();
  }

  public static TsSca getInstance() {
    if (instance == null) {
      instance = new TsSca();
    }
    return instance;
  }

  public String getT() {
    return this.T;
  }

  public void setT(String t)
  {
    this.T = t;
  }

  public void setV(String v) {
    this.V = v;
  }

  public String getL() {
    return this.L;
  }

  public String getV()
  {
    return this.V;
  }

  public boolean isDefaultValue() {
    return this.isDefaultValue;
  }

  public void setDefaultValue(boolean isDefaultValue) {
    this.isDefaultValue = isDefaultValue;
  }

  public void setL(String l) {
    this.L = l;
  }
}