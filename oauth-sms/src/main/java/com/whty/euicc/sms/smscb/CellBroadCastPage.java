package com.whty.euicc.sms.smscb;


import java.util.ArrayList;
import java.util.List;

import com.whty.euicc.sms.commandpacket.Command;
import com.whty.euicc.sms.commandpacket.CommandPacket;
import com.whty.euicc.sms.constants.SMSConstants;
import com.whty.euicc.sms.exception.SMSRuntimeException;
import com.whty.euicc.sms.util.SMSUtil;
import com.whty.euicc.sms.util.TLVBean;

public class CellBroadCastPage
  implements SMSConstants
{
  private String T = "";
  private String L = "";
  private String V = "";
  private String SN = "";

  private String MID = "";
  private String DCS = "";
  private String PP = "";
  private String userData = "";
  private boolean isDefaultValue = true;
  private CommandPacket cp = null;
  private Command cmd = null;
  private static CellBroadCastPage instance = null;

  public void clear() { this.cp.clear();
    instance = null; }

  public List<String> toStringList() {
    List cbpList = new ArrayList();
    List userDataList = new ArrayList();

    this.cp = CommandPacket.getInstance();
    this.cmd = Command.getInstance();
    this.cmd.setPlainString(this.userData);
    this.T = "8C";
    if (this.isDefaultValue) {
      this.SN = "A750";
      this.MID = "1080";
      this.DCS = "56";
    }

    if (SMSUtil.isBetween(this.MID, "1080", 
      "109F")) {
      String cpStr = this.cp.toString();

      if (82 > cpStr.length() / 2)
      {
        int padLength = 82 - cpStr.length() / 2;
        for (int i = 0; i < padLength; i++) {
          cpStr = cpStr + "00";
        }
        this.V = (this.SN + this.MID + this.DCS + this.PP + cpStr);
        this.L = SMSUtil.getLengthHexStr(this.V);
        cbpList.add((this.T + this.L + this.V).toUpperCase());
      }

      if (82 < cpStr.length() / 2) {
        int j = 0;
        int splitNum = cpStr.length() / 164;
        int leftNum = cpStr.length() % 164;
        for (int i = 0; i < splitNum; i++) {
          userDataList.add(cpStr.substring(j, j + 164));
          j += 164;
        }
        if (leftNum != 0) {
          userDataList.add(cpStr.substring(j, cpStr.length()));
        }
        for (int i = 0; i < userDataList.size(); i++) {
          String tsmpStr = (String)userDataList.toArray()[i];
          if (82 > tsmpStr.length() / 2)
          {
            int padLength = 82 - tsmpStr.length() / 2;
            for (int k = 0; k < padLength; k++) {
              tsmpStr = tsmpStr + "00";
            }
          }
          this.PP = 
            (Integer.toHexString(i + 1) + 
            Integer.toHexString(userDataList.size()));
          this.V = (this.SN + this.MID + this.DCS + this.PP + tsmpStr);
          this.L = SMSUtil.getLengthHexStr(this.V);
          cbpList.add((this.T + this.L + this.V).toUpperCase());
        }
      }
    }
    else if ((SMSUtil.isBetween(this.MID, "0000", 
      "03E7")) || 
      (SMSUtil.isBetween(this.MID, "1000", 
      "107F")) || 
      (SMSUtil.isBetween(this.MID, "10A0", 
      "10FF")))
    {
      if (82 > this.userData.length() / 2)
      {
        int padLength = 82 - this.userData.length() / 2;
        for (int i = 0; i < padLength; i++) {
          this.userData += "00";
        }
        this.V = (this.SN + this.MID + this.DCS + this.PP + this.userData);
        this.L = SMSUtil.getLengthHexStr(this.V);
        cbpList.add((this.T + this.L + this.V).toUpperCase());
      }

      if (82 < this.userData.length() / 2) {
        int j = 0;
        int splitNum = this.userData.length() / 164;
        int leftNum = this.userData.length() % 164;
        for (int i = 0; i < splitNum; i++) {
          userDataList.add(this.userData.substring(j, j + 164));
          j += 164;
        }
        if (leftNum != 0) {
          userDataList.add(this.userData.substring(j, this.userData.length()));
        }
        for (int i = 0; i < userDataList.size(); i++) {
          String tsmpStr = (String)userDataList.toArray()[i];
          if (82 > tsmpStr.length() / 2)
          {
            int padLength = 82 - tsmpStr.length() / 2;
            for (int k = 0; k < padLength; k++) {
              tsmpStr = tsmpStr + "00";
            }
          }
          this.PP = 
            (Integer.toHexString(i + 1) + 
            Integer.toHexString(userDataList.size()));
          this.V = (this.SN + this.MID + this.DCS + this.PP + tsmpStr);
          this.L = SMSUtil.getLengthHexStr(this.V);
          cbpList.add((this.T + this.L + this.V).toUpperCase());
        }
      }
    }
    else {
      throw new SMSRuntimeException("MID格式错误! MID=" + this.MID);
    }

    return cbpList;
  }

  public void toObject(String cbpv) {
    TLVBean tlv = new TLVBean(cbpv);
    this.T = tlv.getT();
    this.L = tlv.getL();
    this.V = tlv.getV();
    this.SN = this.V.substring(3, 7);
    this.MID = this.V.substring(7, 11);
    this.DCS = this.V.substring(12, 14);
    this.PP = this.V.substring(14, 16);
    this.cp = CommandPacket.getInstance();

    if (SMSUtil.isBetween(this.MID, "1080", 
      "109F")) {
      this.cp.toObject(this.V.substring(16, this.V.length()));
    }
    else if ((SMSUtil.isBetween(this.MID, "0000", 
      "03E7")) || 
      (SMSUtil.isBetween(this.MID, "1000", 
      "107F")) || 
      (SMSUtil.isBetween(this.MID, "10A0", 
      "10FF"))) {
      this.V = (this.SN + this.MID + this.DCS + this.PP + this.userData);
      this.userData = this.V.substring(16, this.V.length());
    }
  }

  public static CellBroadCastPage getInstance() {
    if (instance == null) {
      instance = new CellBroadCastPage();
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

  public String getSN() {
    return this.SN;
  }

  public void setSN(String sN) {
    this.SN = sN;
  }

  public String getMID() {
    return this.MID;
  }

  public void setMID(String mID) {
    this.MID = mID;
  }

  public String getUserData() {
    return this.userData;
  }

  public void setUserData(String userData) {
    this.userData = userData;
  }

  public CommandPacket getCp() {
    return this.cp;
  }

  public void setCp(CommandPacket cp) {
    this.cp = cp;
  }

  public String getDCS() {
    return this.DCS;
  }

  public void setDCS(String dCS) {
    this.DCS = dCS;
  }

  public String getPP() {
    return this.PP;
  }

  public void setPP(String pP) {
    this.PP = pP;
  }

  public boolean isDefaultValue() {
    return this.isDefaultValue;
  }

  public void setDefaultValue(boolean isDefaultValue) {
    this.isDefaultValue = isDefaultValue;
  }
}