package com.whty.euicc.sms.tpud;


import com.whty.euicc.sms.parameter.DeliverParameter;
import com.whty.euicc.sms.util.SMSUtil;

public class UDH
{
  private String UDHL = "";
  private IdentifyElement ie = null;

  private static UDH instance = null;

  public void clear() { this.ie.clear();
    instance = null; }

  public String toString() {
    this.ie = IdentifyElement.getInstance();
    String ieStr = this.ie.toString();
    if (DeliverParameter.getInstance().isCC())
      this.UDHL = SMSUtil.getLengthHexStr(ieStr);
    else {
      this.UDHL = "";
    }
    return this.UDHL + ieStr;
  }

  public void toObject(String udhv) {
    this.ie = IdentifyElement.getInstance();
    this.UDHL = udhv.substring(0, 2);
    this.ie.toObject(udhv.substring(2, udhv.length()));
  }

  public static UDH getInstance() {
    if (instance == null) {
      instance = new UDH();
    }
    return instance;
  }

  public String getUDHL() {
    return this.UDHL;
  }

  public void setUDHL(String uDHL) {
    this.UDHL = uDHL;
  }

  public IdentifyElement getIe() {
    return this.ie;
  }

  public void setIe(IdentifyElement ie) {
    this.ie = ie;
  }
}
