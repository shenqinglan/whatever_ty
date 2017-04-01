package com.whty.euicc.sms.submit;


import com.whty.euicc.util.StringUtil;

public class SubmitParameter
{
  private String mrvrus = "";
  private String mr = "";
  private String da = "";
  private String pid = "";
  private String dcs = "";
  private String vp = "";
  private static SubmitParameter instance = null;

  public void clear() {
    instance = null;
  }
  public String toString() {
    return this.mrvrus + this.mr + this.da + this.pid + this.dcs + this.vp;
  }

  public void toObject(String spv)
  {
    this.mrvrus = spv.substring(0, 2);

    this.mr = spv.substring(2, 4);

    int oaLength = StringUtil.hexToByte(spv.substring(4, 6));

    this.da = spv.substring(4, oaLength + 8);
    this.pid = spv.substring(oaLength + 8, oaLength + 10);
    this.dcs = spv.substring(oaLength + 10, oaLength + 12);
    this.vp = spv.substring(oaLength + 12, spv.length());
  }

  public static SubmitParameter getInstance() {
    if (instance == null) {
      instance = new SubmitParameter();
    }
    return instance;
  }

  public String getMrvrus() {
    return this.mrvrus;
  }

  public void setMrvrus(String mrvrus) {
    this.mrvrus = mrvrus;
  }

  public String getMr() {
    return this.mr;
  }

  public void setMr(String mr) {
    this.mr = mr;
  }

  public String getDa() {
    return this.da;
  }

  public void setDa(String da) {
    this.da = da;
  }

  public String getPid() {
    return this.pid;
  }

  public void setPid(String pid) {
    this.pid = pid;
  }

  public String getDcs() {
    return this.dcs;
  }

  public void setDcs(String dcs) {
    this.dcs = dcs;
  }

  public String getVp() {
    return this.vp;
  }

  public void setVp(String vp) {
    this.vp = vp;
  }
}