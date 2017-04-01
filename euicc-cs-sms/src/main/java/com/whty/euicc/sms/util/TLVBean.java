package com.whty.euicc.sms.util;

import com.whty.euicc.sms.exception.SMSRuntimeException;
/**
 * TLV结构的javaBean
 * @author Administrator
 *
 */
public class TLVBean
{
  private String T = "";
  private String L = "";
  private String V = "";

  public TLVBean(String tlv) {
    this.T = tlv.substring(0, 2);
    this.L = tlv.substring(2, 4);
    this.V = tlv.substring(4, tlv.length());
    if (this.V.length() / 2 != Integer.parseInt(this.L, 16))
      throw new SMSRuntimeException("Error:TLV结构有错误 ");
  }

  public String getT()
  {
    return this.T;
  }

  public String getL() {
    return this.L;
  }

  public String getV() {
    return this.V;
  }
}