package com.whty.euicc.sms.commandpacket;


import com.whty.euicc.sms.parameter.DeliverParameter;
import com.whty.euicc.sms.tpud.IdentifyElement;
import com.whty.euicc.util.StringUtil;
/**
 * 拼装commandPacket
 * @author Administrator
 *
 */
public class CommandPacket
{
  private String CPI = "";
  private String CPL = "";
  private String CMD = "";
  private String conatednatedStr = "";
  private Command cmd = null;

  private static CommandPacket instance = null;

  public static CommandPacket getInstance()
  {
    if (instance == null) {
      instance = new CommandPacket();
    }
    return instance;
  }

  public String toString()
  {
    this.cmd = Command.getInstance();

    if ((IdentifyElement.getInstance().getIEIa().equalsIgnoreCase("00")) || 
      (IdentifyElement.getInstance().getIEIa()
      .equalsIgnoreCase("08"))) {
      return this.conatednatedStr;
    }

    //this.CPI = "";

    //this.CMD = this.cmd.toString();

    //this.CPL = StringUtil.shortToHex((short)(this.CMD.length() / 2));
    
    boolean isFormat = DeliverParameter.getInstance().isFormate();
    
    return this.cmd.toString(isFormat);
    /*if (DeliverParameter.getInstance().isFormate())
    {
      return this.CPI + this.CPL + this.CMD;
    }
    return this.CMD;*/
  }

 
  
  public void  toObject(String udhv)
  {
  }

  public void clear()
  {
    this.cmd.clear();
    instance = null;
  }

  public String getCPI() {
    return this.CPI;
  }

  public void setCPI(String cPI) {
    this.CPI = cPI;
  }

  public String getCPL() {
    return this.CPL;
  }

  public void setCPL(String cPL) {
    this.CPL = cPL;
  }

  public String getCMD() {
    return this.CMD;
  }

  public void setCMD(String cMD) {
    this.CMD = cMD;
  }

  public String getConatednatedStr()
  {
    return this.conatednatedStr;
  }

  public void setConatednatedStr(String conatednatedStr)
  {
    this.conatednatedStr = conatednatedStr;
  }
}