package com.whty.euicc.sms.tpud;


import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.exception.SMSRuntimeException;

public class IdentifyElement
  implements SMSConstants
{
  private String IEIa = "";
  private String IEIDLa = "";

  private String IEDa = "";

  private String IEIb = "";
  private String IEIDLb = "";
  private String IEDb = "";
  private static IdentifyElement instance = null;

  public void clear()
  {
    this.IEIa = "";
    this.IEDa = "";
    this.IEIDLa = "";
    this.IEIb = "";
    this.IEIDLb = "";
    this.IEDb = "";
  }

  public String toString() {
    if (this.IEIa.equals("08")) {
      String value = "";

      if ((this.IEIa.equals("70")) && (this.IEIb.equals(""))) {
        this.IEIDLa = "00";
        this.IEDa = "";
        value = this.IEIa + this.IEIDLa + this.IEDa;
        return value;
      }

      if ((this.IEIa.equals("08")) && (!this.IEIb.equals("")))
      {
        if ("01".equals(this.IEDa.substring(this.IEDa.length() - 2, this.IEDa.length()))) {
          this.IEIa = "08";
          this.IEIDLa = "04";
          this.IEIb = "70";
          this.IEIDLb = "00";
          this.IEDb = "";
          value = this.IEIa + this.IEIDLa + this.IEDa + this.IEIb + this.IEIDLb + this.IEDb;
          return value;
        }
        this.IEIa = "08";
        this.IEIDLa = "04";
        value = this.IEIa + this.IEIDLa + this.IEDa;
        return value;
      }

      if ((this.IEIa.equals("08")) && (this.IEIb.equals(""))) {
        this.IEIa = "08";
        this.IEIDLa = "04";
        value = this.IEIa + this.IEIDLa + this.IEDa;
        return value;
      }

      if ((this.IEIa.equals("")) && (this.IEIb.equals(""))) {
        return "";
      }

      throw new SMSRuntimeException(" IE 格式不对！IE=" + toString());
    }

    String value = "";

    if ((this.IEIa.equals("70")) && (this.IEIb.equals(""))) {
      this.IEIDLa = "00";
      this.IEDa = "";
      value = this.IEIa + this.IEIDLa + this.IEDa;
      return value;
    }

    if ((this.IEIa.equals("00")) && (!this.IEIb.equals("")))
    {
      if ("01".equals(this.IEDa.substring(this.IEDa.length() - 2, this.IEDa.length()))) {
        this.IEIa = "00";
        this.IEIDLa = "03";
        this.IEIb = "70";
        this.IEIDLb = "00";
        this.IEDb = "";
        value = this.IEIa + this.IEIDLa + this.IEDa + this.IEIb + this.IEIDLb + this.IEDb;
        return value;
      }
      this.IEIa = "00";
      this.IEIDLa = "03";
      value = this.IEIa + this.IEIDLa + this.IEDa;
      return value;
    }

    if ((this.IEIa.equals("00")) && (this.IEIb.equals(""))) {
      this.IEIa = "00";
      this.IEIDLa = "03";
      value = this.IEIa + this.IEIDLa + this.IEDa;
      return value;
    }

    if ((this.IEIa.equals("")) && (this.IEIb.equals(""))) {
      return "";
    }

    System.out.println(value);
    clear();
    throw new SMSRuntimeException(" IE 格式不对！IE=" + toString());
  }

  public void toObject(String iev)
  {
    this.IEIa = iev.substring(0, 2);

    if ("70".equals(this.IEIa)) {
      this.IEIDLa = iev.substring(2, iev.length());
      this.IEDa = "";
    }
    else if (("00".equals(this.IEIa)) && (iev.length() == 14)) {
      this.IEIDLa = iev.substring(2, 4);
      this.IEDa = iev.substring(4, 10);
      this.IEIb = iev.substring(10, 12);
      this.IEIDLb = iev.substring(12, 14);
      this.IEDb = "";
    }
    else if (("00".equals(this.IEIa)) && (iev.length() == 10)) {
      this.IEIDLa = iev.substring(2, 4);
      this.IEDa = iev.substring(4, 10);
    }
    else
    {
      throw new SMSRuntimeException(
        "IEIa-IEIDLa-IEDa-IEIb-IEIDLb-IEDb的某些项格式不对！");
    }
  }

  public String getIEIa() {
    return this.IEIa;
  }

  public void setIEIa(String iEIa) {
    this.IEIa = iEIa;
  }

  public String getIEIDLa() {
    return this.IEIDLa;
  }

  public void setIEIDLa(String iEIDLa) {
    this.IEIDLa = iEIDLa;
  }

  public String getIEDa() {
    return this.IEDa;
  }

  public void setIEDa(String iEDa) {
    this.IEDa = iEDa;
  }

  public String getIEIb() {
    return this.IEIb;
  }

  public void setIEIb(String iEIb) {
    this.IEIb = iEIb;
  }

  public String getIEIDLb() {
    return this.IEIDLb;
  }

  public void setIEIDLb(String iEIDLb) {
    this.IEIDLb = iEIDLb;
  }

  public String getIEDb() {
    return this.IEDb;
  }

  public void setIEDb(String iEDb) {
    this.IEDb = iEDb;
  }

  public static IdentifyElement getInstance() {
    if (instance == null) {
      instance = new IdentifyElement();
    }
    return instance;
  }
}