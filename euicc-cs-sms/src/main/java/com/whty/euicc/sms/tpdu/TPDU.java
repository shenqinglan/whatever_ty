package com.whty.euicc.sms.tpdu;


import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.parameter.DeliverParameter;
import com.whty.euicc.sms.parameter.SubmitParameter;
import com.whty.euicc.sms.tpud.TPUD;
import com.whty.euicc.sms.util.TLVBean;
import com.whty.euicc.util.StringUtil;

/**
 * 拼装tpdu
 * @author Administrator
 *
 */
public class TPDU
  implements SMSConstants
{
  private String T = "";
  private String L = "";
  private String V = "";
  private DeliverParameter dp = null;
  private SubmitParameter sp = null;
  private TPUD tpud = null;
  private String UDL = "";
  private boolean isDeliver = true;
  private boolean isDefaultValue = true;
  private static TPDU instance = null;
  private boolean isUPD = false;

  public void clear() { this.dp.clear();
    this.sp.clear();
    this.tpud.clear();
    instance = null;
  }

  public String toString()
  {
    this.tpud = TPUD.getInstance();
    if (this.isDefaultValue) {
      this.T = "8B";

      if (this.isDeliver) {
        this.V = this.tpud.toString();
      }
      else {
        this.V = this.tpud.toString();
      }

    }
    else if (this.isDeliver) {
      this.V = this.tpud.toString();
    }
    else
    {
      this.V = this.tpud.toString();
    }

    return this.V;
  }

  public void toObject(String tpduv) {
    TLVBean tlv = new TLVBean(tpduv);
    this.T = tlv.getT();
    this.L = tlv.getL();
    this.V = tlv.getV();

    if (this.isDeliver) {
      int dpLength = StringUtil.hexToByte(tpduv.substring(2, 4));
      String dpv = this.V.substring(0, 2) + this.V.substring(2, dpLength + 24);
      this.UDL = tpduv.substring(dpLength + 24, dpLength + 26);

      this.dp = DeliverParameter.getInstance();
      this.tpud = TPUD.getInstance();
      this.dp.toObject(dpv);
      this.tpud.toObject(tpduv.substring(dpLength + 26, tpduv.length()));
    } else {
      int spLength = StringUtil.hexToByte(tpduv.substring(4, 6));
      String spv = this.V.substring(0, 4) + this.V.substring(4, spLength + 12);
      this.UDL = tpduv.substring(spLength + 12, spLength + 14);

      this.sp = SubmitParameter.getInstance();
      this.tpud = TPUD.getInstance();
      this.sp.toObject(spv);
      this.tpud.toObject(tpduv.substring(spLength + 14, tpduv.length()));
    }
  }

  public static TPDU getInstance() {
    if (instance == null) {
      instance = new TPDU();
    }
    return instance;
  }

  public SubmitParameter getSp() {
    return this.sp;
  }

  public void setSp(SubmitParameter sp) {
    this.sp = sp;
  }

  public boolean isDeliver() {
    return this.isDeliver;
  }

  public void setDeliver(boolean isDeliver) {
    this.isDeliver = isDeliver;
  }

  public String getT() {
    return this.T;
  }

  public String getL() {
    return this.L;
  }

  public String getV() {
    return this.V;
  }

  public String getUDL() {
    return this.UDL;
  }

  public void setUDL(String uDL) {
    this.UDL = uDL;
  }

  public TPUD getTpud() {
    return this.tpud;
  }

  public void setTpud(TPUD tpud) {
    this.tpud = tpud;
  }

  public DeliverParameter getDp() {
    return this.dp;
  }

  public void setDp(DeliverParameter dp) {
    this.dp = dp;
  }

  public boolean isDefaultValue() {
    return this.isDefaultValue;
  }

  public void setDefaultValue(boolean isDefaultValue) {
    this.isDefaultValue = isDefaultValue;
  }

  public void setT(String t) {
    this.T = t;
  }

  public void setL(String l) {
    this.L = l;
  }

  public void setV(String v) {
    this.V = v;
  }
  public boolean isUPD() {
    return this.isUPD;
  }
  public void setUPD(boolean isUPD) {
    this.isUPD = isUPD;
  }
}
