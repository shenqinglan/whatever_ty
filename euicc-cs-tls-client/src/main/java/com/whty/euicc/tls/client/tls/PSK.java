package com.whty.euicc.tls.client.tls;

public class PSK {
	  private byte[] id;
	  private byte[] keyValue;
	  
	  public byte[] getId() { return id; }
	  
	  public void setId(byte[] id) {
	    this.id = id;
	  }
	  
	  public byte[] getKeyValue() { return keyValue; }
	  
	  public void setKeyValue(byte[] keyValue) {
	    this.keyValue = keyValue;
	  }
}