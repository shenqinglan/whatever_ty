package com.whty.euicc.sms.smspp;

import com.whty.euicc.sms.commandpacket.Command;
import com.whty.euicc.util.StringUtil;



public class SMSPpResponse
{
  private static SMSPpResponse instance = null;
  private String UDHL = "";
  private String CPI = "";
  private String RPL = "";
  private String RHL = "";
  private String TAR = "";
  private String CNTR = "";
  private String PCNTR = "";

  private String SC = "";
  private String RCCCDS = "";
  private String responseInfo = "";

  public void clear() {
    instance = null;
  }

  public void toObject(String updatav) {
    Command cmd = Command.getInstance();

    int startIndex = updatav.indexOf("027100");
    String tempv = updatav.substring(startIndex, updatav.length());

    this.UDHL = tempv.substring(0, 2);

    this.CPI = tempv.substring(2, 6);

    this.RPL = tempv.substring(6, 10);

    this.RHL = tempv.substring(10, 12);

    this.TAR = tempv.substring(12, 18);
    String cipherPartStr = tempv.substring(18, tempv.length());
    Command.getInstance().anyMacCipherTag();

    if ((!"06".equals(this.SC)) && (!"09".equals(this.SC))) {
      cipherPartStr = cmd.deCrypt(Command.getInstance().getCipherAlg(), 
        StringUtil.hexToByte(Command.getInstance().getKIc()), 
        cipherPartStr);
    }

    this.CNTR = cipherPartStr.substring(0, 10);

    this.PCNTR = cipherPartStr.substring(10, 12);

    this.SC = cipherPartStr.substring(12, 14);

    int RCCCDSLength = Command.getInstance().getRCCCDSLength();
    if (RCCCDSLength != 0) {
      this.RCCCDS = cipherPartStr.substring(14, 14 + RCCCDSLength * 2);
    }

    this.responseInfo = cipherPartStr.substring(14 + RCCCDSLength * 2, 
      cipherPartStr.length());

    this.responseInfo = this.responseInfo.substring(0, this.responseInfo.length() - 
      StringUtil.hexToByte(this.PCNTR) * 2);
  }

  public static SMSPpResponse getInstance() {
    if (instance == null) {
      instance = new SMSPpResponse();
    }
    return instance;
  }

  public String getUDHL() {
    return this.UDHL;
  }

  public String getCPI() {
    return this.CPI;
  }

  public String getRPL() {
    return this.RPL;
  }

  public String getRHL() {
    return this.RHL;
  }

  public String getTAR() {
    return this.TAR;
  }

  public String getCNTR() {
    return this.CNTR;
  }

  public String getPCNTR() {
    return this.PCNTR;
  }

  public String getSC() {
    return this.SC;
  }

  public String getRCCCDS() {
    return this.RCCCDS;
  }

  public String getResponseInfo() {
    return this.responseInfo;
  }

  public void setSC(String sC) {
    this.SC = sC;
  }
}