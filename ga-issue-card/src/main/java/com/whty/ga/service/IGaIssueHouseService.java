package com.whty.ga.service;

import com.whty.ga.pojo.GaHouseInfo;

public interface IGaIssueHouseService {

	GaHouseInfo findHouseInfoByBuildingNoAndUnitNoAndRoomNo(String buildingNo,
			String unitNo, String roomNo);

	void assignGaIssueHouseInfo(GaHouseInfo house);

	void assignGaIssueHouseInfo(GaHouseInfo house, GaHouseInfo data);

	void saveHouseInfo(GaHouseInfo house);

}
