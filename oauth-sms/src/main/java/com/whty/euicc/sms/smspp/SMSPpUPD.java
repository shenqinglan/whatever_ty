package com.whty.euicc.sms.smspp;

import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.tpdu.TPDU;
import com.whty.euicc.sms.tssca.TsSca;

public class SMSPpUPD
  implements SMSConstants
{
  private TsSca ts = null;
  private TPDU tpdu = null;
  private static SMSPpUPD instance = null;

  public void clear() { this.ts.clear();
    this.tpdu.clear();
    instance = null; }

  public String toString() {
    this.ts = TsSca.getInstance();
    this.tpdu = TPDU.getInstance();
    this.ts.setDefaultValue(false);
    this.ts.setT("");
    this.ts.setV("91947122720000");
    this.tpdu.setDefaultValue(false);
    this.tpdu.setT("");
    this.tpdu.setUPD(true);
    this.tpdu.setDeliver(true);
    return "03" + this.ts.toString() + this.tpdu.toString();
  }

  public static SMSPpUPD getInstance() {
    if (instance == null) {
      instance = new SMSPpUPD();
    }
    return instance;
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