package com.whty.euicc.sms.smspp;

import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.di.DeviceIdentities;
import com.whty.euicc.sms.tpdu.TPDU;
import com.whty.euicc.sms.tssca.TsSca;
import com.whty.euicc.sms.util.TLVBean;


public class SMSPpDeliver
  implements SMSConstants
{
  private String T = "";
  private String L = "";
  private String V = "";
  private DeviceIdentities di = null;
  private TsSca ts = null;
  private TPDU tpdu = null;
  private static SMSPpDeliver instance = null;

  public void clear() {
    this.di.clear();
    this.tpdu.clear();
    instance = null;
  }

  public String toString()
  {
    this.tpdu = TPDU.getInstance();

    this.V = this.tpdu.toString();
    return this.V;
  }

  public void toObject(String ppv)
  {
    TLVBean tlv = new TLVBean(ppv);
    this.T = tlv.getT();
    this.L = tlv.getL();
    this.V = tlv.getV();

    this.di = DeviceIdentities.getInstance();
    this.di.toObject("82028381");
    this.ts = TsSca.getInstance();

    this.ts.toObject("060100");
    this.tpdu = TPDU.getInstance();

    this.tpdu.setDeliver(true);
    this.tpdu.toObject(this.V.substring(14, this.V.length()));
  }

  public static SMSPpDeliver getInstance()
  {
    if (instance == null) {
      instance = new SMSPpDeliver();
    }
    return instance;
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

  public DeviceIdentities getDi()
  {
    return this.di;
  }

  public void setDi(DeviceIdentities di) {
    this.di = di;
  }

  public TsSca getTs() {
    return this.ts;
  }

  public void setTs(TsSca ts) {
    this.ts = ts;
  }

  public TPDU getTpdu() {
    return this.tpdu;
  }

  public void setTpdu(TPDU tpdu) {
    this.tpdu = tpdu;
  }
}