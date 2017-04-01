package com.whty.euicc.sms.smscb;

import java.util.ArrayList;
import java.util.List;

import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.di.DeviceIdentities;
import com.whty.euicc.sms.util.SMSUtil;
import com.whty.euicc.sms.util.TLVBean;

public class SMSCB
  implements SMSConstants
{
  private String T = "";
  private String L = "";
  private String V = "";
  private DeviceIdentities di = null;
  private CellBroadCastPage cbp = null;
  private static SMSCB instance = null;

  public void clear() { this.cbp.clear();
    this.di.clear();
    instance = null; }

  public List<String> toStringList() {
    this.di = DeviceIdentities.getInstance();
    this.cbp = CellBroadCastPage.getInstance();
    List smsCBList = new ArrayList();
    this.T = "D2";
    List temp = this.cbp.toStringList();
    for (int i = 0; i < temp.size(); i++) {
      this.V = (this.di.toString() + temp.toArray()[i]);
      this.L = SMSUtil.getLengthHexStr(this.V);
      smsCBList.add(this.T + this.L + this.V);
    }
    return smsCBList;
  }

  public void toObject(String smscbv) {
    TLVBean tlv = new TLVBean(smscbv);
    this.T = tlv.getT();
    this.L = tlv.getL();
    this.V = tlv.getV();
    this.di.toObject(this.V.substring(3, 11));
    this.cbp.toObject(this.V.substring(11, this.V.length()));
  }

  public static SMSCB getInstance() {
    if (instance == null) {
      instance = new SMSCB();
    }
    return instance;
  }

  public String getT() {
    return this.T;
  }

  public void setT(String t) {
    this.T = t;
  }

  public String getL() {
    return this.L;
  }

  public void setL(String l) {
    this.L = l;
  }

  public String getV() {
    return this.V;
  }

  public void setV(String v) {
    this.V = v;
  }

  public DeviceIdentities getDi() {
    return this.di;
  }

  public void setDi(DeviceIdentities di) {
    this.di = di;
  }

  public CellBroadCastPage getCbp() {
    return this.cbp;
  }

  public void setPp(CellBroadCastPage cbp) {
    this.cbp = cbp;
  }
}