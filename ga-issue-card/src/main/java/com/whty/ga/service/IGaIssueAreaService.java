package com.whty.ga.service;

import com.whty.ga.pojo.GaAreaInfo;

public interface IGaIssueAreaService {

	GaAreaInfo findAreaInfoByAreaName(String areaName);

	void assignGaIssueAreaInfo(GaAreaInfo area);

	void assignGaIssueAreaInfo(GaAreaInfo area, GaAreaInfo data);

	void saveAreaInfo(GaAreaInfo areaInfo);

}
