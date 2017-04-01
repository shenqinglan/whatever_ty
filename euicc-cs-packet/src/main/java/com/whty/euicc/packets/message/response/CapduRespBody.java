package com.whty.euicc.packets.message.response;

import com.whty.euicc.packets.message.response.attr.AttrCApdu;

import java.util.ArrayList;
import java.util.List;

public class CapduRespBody extends BaseRespBody implements AttrCApdu {
	private List<Capdu> cApdu = new ArrayList<Capdu>(0);

	@Override
	public List<Capdu> getcApdu() {
		return this.cApdu;
	}

	@Override
	public void setcApdu(List<Capdu> cApdu) {
		this.setcApdu(cApdu);
	}

}
