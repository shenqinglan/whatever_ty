package com.whty.euicc.packets.message.response.attr;

import com.whty.euicc.packets.message.response.Capdu;

import java.util.List;

public interface AttrCApdu {

	public List<Capdu> getcApdu();

	public void setcApdu(List<Capdu> cApdu);

}
