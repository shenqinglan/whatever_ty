package com.whty.euicc.sms.di;

import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.exception.SMSRuntimeException;
import com.whty.euicc.sms.util.SMSUtil;
import com.whty.euicc.sms.util.TLVBean;

/**
 * 设备ID
 */

public class DeviceIdentities
  implements SMSConstants
{
  private String T = "";
  private String L = "";
  private String V = "";
  private String src = "";
  private String dst = "";
  private boolean isDefaultValue = true;
  private static DeviceIdentities instance = null;

  public String toString()
  {
    if (this.isDefaultValue) {
      this.T = "82";
      this.src = "83";
      this.dst = "81";
      this.V = (this.src + this.dst);
      this.L = SMSUtil.getLengthHexStr(this.V);
    } else {
      if (("".equals(this.T)) || ("".equals(this.V))) {
        throw new SMSRuntimeException("Error:DeviceIdentities的Tag或者Value没有值");
      }
      this.L = SMSUtil.getLengthHexStr(this.V);
    }
    return this.T + this.L + this.V;
  }
  public void clear() {
    instance = null;
  }

  public void toObject(String div)
  {
    TLVBean tlv = new TLVBean(div);
    this.T = tlv.getT();
    this.L = tlv.getL();
    this.V = tlv.getV();
    this.src = this.V.substring(0, 2);
    this.dst = this.V.substring(2, 4);
  }

  public static DeviceIdentities getInstance() {
    if (instance == null) {
      instance = new DeviceIdentities();
    }
    return instance;
  }

  public String getSrc() {
    return this.src;
  }

  public String getDst() {
    return this.dst;
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

  public void setSrc(String src) {
    this.src = src;
  }

  public void setDst(String dst) {
    this.dst = dst;
  }
}