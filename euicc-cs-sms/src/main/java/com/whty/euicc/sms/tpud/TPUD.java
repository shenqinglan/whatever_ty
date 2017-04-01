package com.whty.euicc.sms.tpud;

import com.whty.euicc.sms.commandpacket.CommandPacket;


/**
 * 拼装tpud
 * @author Administrator
 *
 */
public class TPUD
{
  private UDH udh = null;
  private CommandPacket cp = null;
  private static TPUD instance = null;

  public String toString() {
    this.udh = UDH.getInstance();
    this.cp = CommandPacket.getInstance();

    return this.udh.toString() + this.cp.toString();
  }
  public void clear() {
    this.udh.clear();
    this.cp.clear();
    instance = null;
  }

  public void toObject(String tpudv) {
  }

  public static TPUD getInstance() {
    if (instance == null) {
      instance = new TPUD();
    }
    return instance;
  }

  public UDH getUdh() {
    return this.udh;
  }

  public void setUdh(UDH udh) {
    this.udh = udh;
  }

  public CommandPacket getCp() {
    return this.cp;
  }

  public void setCp(CommandPacket cp) {
    this.cp = cp;
  }
}