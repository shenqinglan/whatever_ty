package com.whty.ga.service;

import com.whty.ga.pojo.GaPersonInfo;

public interface IGaIssuePersonService {
	
	public GaPersonInfo findPersonInfoByIdCardNo(String idCardNo);

	public void assignGaIssuePersonInfo(GaPersonInfo person);
	
	public void assignGaIssuePersonInfo(GaPersonInfo person, GaPersonInfo data);

	public GaPersonInfo findPersonInfoByName(String name);
	
	public GaPersonInfo findPersonInfoByNameAndMobil(String name, String mobil);

	public void savePersonInfo(GaPersonInfo person);
}
