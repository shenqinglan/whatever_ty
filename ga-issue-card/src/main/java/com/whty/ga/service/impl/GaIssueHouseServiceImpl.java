package com.whty.ga.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.whty.ga.pojo.GaHouseInfo;
import com.whty.ga.repository.GaIssueHouseRepo;
import com.whty.ga.service.IGaIssueHouseService;

/**
 * @ClassName GaIssueHouseServiceImpl
 * @author Administrator
 * @date 2017-3-3 上午9:54:33
 * @Description TODO(房屋信息业务层代码)
 */
@Transactional
@Service("gaIssueHouseServiceImpl")
public class GaIssueHouseServiceImpl implements IGaIssueHouseService {

	@Resource
	private GaIssueHouseRepo houseRepo;

	@Override
	public GaHouseInfo findHouseInfoByBuildingNoAndUnitNoAndRoomNo(
			String buildingNo, String unitNo, String roomNo) {
		return houseRepo.findByBuildingNoAndUnitNoAndRoomNo(buildingNo, unitNo, roomNo);
	}
	
	@Override
	public void saveHouseInfo(GaHouseInfo house) {
		houseRepo.save(house);
	}

	@Override
	public void assignGaIssueHouseInfo(GaHouseInfo house) {
		Date time = new Date();
		house.setDelDlag("0");
		house.setCreateDate(time);
		house.setUpdateDate(time);
	}

	@Override
	public void assignGaIssueHouseInfo(GaHouseInfo house, GaHouseInfo data) {
		boolean updateFlag = false;
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(house.getBuildingNo()) && 
				!StringUtils.equals(house.getBuildingNo(), data.getBuildingNo())) {
			updateFlag = true;
			data.setBuildingNo(house.getBuildingNo());
		} 
		if (StringUtils.isNotBlank(house.getUnitNo()) && 
				!StringUtils.equals(house.getUnitNo(), data.getUnitNo())) {
			updateFlag = true;
			data.setUnitNo(house.getUnitNo());
		} 
		if (StringUtils.isNotBlank(house.getRoomNo()) && 
				!StringUtils.equals(house.getRoomNo(), data.getRoomNo())) {
			updateFlag = true;
			data.setRoomNo(house.getRoomNo());
		} 
		if (StringUtils.isNotBlank(house.getStandardAddress()) && 
				!StringUtils.equals(house.getStandardAddress(), data.getStandardAddress())) {
			updateFlag = true;
			data.setStandardAddress(house.getStandardAddress());
		} 
		if (StringUtils.isNotBlank(house.getCommonAddress()) && 
				!StringUtils.equals(house.getCommonAddress(), data.getCommonAddress())) {
			updateFlag = true;
			data.setCommonAddress(house.getCommonAddress());
		} 
		if (StringUtils.isNotBlank(house.getBuildingInfo()) && 
				!StringUtils.equals(house.getBuildingInfo(), data.getBuildingInfo())) {
			updateFlag = true;
			data.setBuildingInfo(house.getBuildingInfo());
		} 
		if (StringUtils.isNotBlank(house.getHouseTypeCode()) && 
				!StringUtils.equals(house.getHouseTypeCode(), data.getHouseTypeCode())) {
			updateFlag = true;
			data.setHouseTypeCode(house.getHouseTypeCode());
		} 
		if (StringUtils.isNotBlank(house.getSize()) && 
				!StringUtils.equals(house.getSize(), data.getSize())) {
			updateFlag = true;
			data.setSize(house.getSize());
		} 
		if (house.getIssueDate()!=null && StringUtils.isNotBlank(sdfDate.format(house.getIssueDate())) && 
				!StringUtils.equals(sdfDate.format(house.getIssueDate()), sdfDate.format(data.getIssueDate()))) {
			updateFlag = true;
			data.setIssueDate(house.getIssueDate());
		}
		if (StringUtils.isNotBlank(house.getRemarks()) && 
				!StringUtils.equals(house.getRemarks(), data.getRemarks())) {
			updateFlag = true;
			data.setRemarks(house.getRemarks());
		} 
		if (updateFlag) {
			data.setUpdateDate(new Date());
		}
	}
}
