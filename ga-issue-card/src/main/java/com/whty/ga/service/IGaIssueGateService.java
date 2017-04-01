package com.whty.ga.service;

import com.whty.ga.pojo.GaGateInfo;

public interface IGaIssueGateService {

	GaGateInfo findGateInfoByAreaNameAndBuildingNoAndUnitNo(String areaName,
			String buildingNo, String unitNo);

	void assignGaIssueGateInfo(GaGateInfo gate);

	void assignGaIssueGateInfo(GaGateInfo gate, GaGateInfo data);

	void saveGateInfo(GaGateInfo gateInfo);

}
