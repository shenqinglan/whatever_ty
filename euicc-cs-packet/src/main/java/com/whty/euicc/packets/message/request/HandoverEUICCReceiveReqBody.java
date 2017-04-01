package com.whty.euicc.packets.message.request;

import java.util.List;

import com.whty.euicc.data.pojo.EuiccCard;
import com.whty.euicc.data.pojo.EuiccIsdP;
import com.whty.euicc.data.pojo.EuiccIsdR;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;
import com.whty.euicc.data.pojo.EuiccScp03;
import com.whty.euicc.data.pojo.EuiccScp80;
import com.whty.euicc.data.pojo.EuiccScp81;
import com.whty.euicc.packets.message.MsgType;

@MsgType("handoverEUICC")
public class HandoverEUICCReceiveReqBody extends EuiccReqBody {

	private String isdrId;
	
	private EuiccCard card;
	
    private String certSrEcdsa;
	
	private String certOfEuicc;
	
	private String currentSmsrId;
	
	//新增，send要删除的内容
	private List<EuiccProfileWithBLOBs> profiles;
	
	private List<EuiccIsdP> isdp;
	
	private EuiccIsdR isdR;
	
	private List<EuiccScp03> scp03s;
	
	private List<EuiccScp80> scp80s;
	
	private EuiccScp81 scp81;
	
    public List<EuiccScp80> getScp80s() {
		return scp80s;
	}

	public void setScp80s(List<EuiccScp80> scp80s) {
		this.scp80s = scp80s;
	}

	public EuiccScp81 getScp81() {
		return scp81;
	}

	public void setScp81(EuiccScp81 scp81) {
		this.scp81 = scp81;
	}

	public List<EuiccScp03> getScp03s() {
		return scp03s;
	}

	public void setScp03s(List<EuiccScp03> scp03s) {
		this.scp03s = scp03s;
	}

	public List<EuiccIsdP> getIsdp() {
		return isdp;
	}

	public void setIsdp(List<EuiccIsdP> isdp) {
		this.isdp = isdp;
	}

	public EuiccIsdR getIsdR() {
		return isdR;
	}

	public void setIsdR(EuiccIsdR isdR) {
		this.isdR = isdR;
	}

	public String getIsdrId() {
		return isdrId;
	}

	public void setIsdrId(String isdrId) {
		this.isdrId = isdrId;
	}

	public String getCurrentSmsrId() {
		return currentSmsrId;
	}

	public void setCurrentSmsrId(String currentSmsrId) {
		this.currentSmsrId = currentSmsrId;
	}

	public String getCertOfEuicc() {
		return certOfEuicc;
	}

	public void setCertOfEuicc(String certOfEuicc) {
		this.certOfEuicc = certOfEuicc;
	}

	public String getCertSrEcdsa() {
		return certSrEcdsa;
	}

	public void setCertSrEcdsa(String certSrEcdsa) {
		this.certSrEcdsa = certSrEcdsa;
	}

	public EuiccCard getCard() {
		return card;
	}

	public void setCard(EuiccCard card) {
		this.card = card;
	}
	
	public List<EuiccProfileWithBLOBs> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<EuiccProfileWithBLOBs> profiles) {
		this.profiles = profiles;
	}


}
