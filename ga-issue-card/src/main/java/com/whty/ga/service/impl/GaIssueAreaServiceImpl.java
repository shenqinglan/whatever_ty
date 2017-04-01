package com.whty.ga.service.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.whty.ga.pojo.GaAreaInfo;
import com.whty.ga.repository.GaIssueAreaRepo;
import com.whty.ga.service.IGaIssueAreaService;

/**
 * @ClassName GaIssueAreaServiceImpl
 * @author Administrator
 * @date 2017-3-3 上午9:53:33
 * @Description TODO(小区信息业务层代码)
 */
@Transactional
@Service("gaIssueAreaServiceImpl")
public class GaIssueAreaServiceImpl implements IGaIssueAreaService {

	@Resource
	private GaIssueAreaRepo areaRepo;

	@Override
	public GaAreaInfo findAreaInfoByAreaName(String areaName) {
		return areaRepo.findByAreaName(areaName);
	}
	
	@Override
	public void saveAreaInfo(GaAreaInfo area) {
		areaRepo.save(area);
	}

	@Override
	public void assignGaIssueAreaInfo(GaAreaInfo area) {
		Date time = new Date();
		area.setDelFlag("0");
		area.setCreateDate(time);
		area.setUpdateDate(time);
	}

	@Override
	public void assignGaIssueAreaInfo(GaAreaInfo area, GaAreaInfo data) {
		boolean updateFlag = false;
		if (StringUtils.isNotBlank(area.getAreaName()) && 
				!StringUtils.equals(area.getAreaName(), data.getAreaName())) {
			updateFlag = true;
			data.setAreaName(area.getAreaName());
		} 
		if (StringUtils.isNotBlank(area.getCityNo()) && 
				!StringUtils.equals(area.getCityNo(), data.getCityNo())) {
			updateFlag = true;
			data.setCityNo(area.getCityNo());
		} 
		if (StringUtils.isNotBlank(area.getAreaNo()) && 
				!StringUtils.equals(area.getAreaNo(), data.getAreaNo())) {
			updateFlag = true;
			data.setAreaNo(area.getAreaNo());
		} 
		if (StringUtils.isNotBlank(area.getAreaAddress()) && 
				!StringUtils.equals(area.getAreaAddress(), data.getAreaAddress())) {
			updateFlag = true;
			data.setAreaAddress(area.getAreaAddress());
		} 
		if (StringUtils.isNotBlank(area.getAreaTypeCode()) && 
				!StringUtils.equals(area.getAreaTypeCode(), data.getAreaTypeCode())) {
			updateFlag = true;
			data.setAreaTypeCode(area.getAreaTypeCode());
		} 
		if (StringUtils.isNotBlank(area.getRemarks()) && 
				!StringUtils.equals(area.getRemarks(), data.getRemarks())) {
			updateFlag = true;
			data.setRemarks(area.getRemarks());
		} 
		if (updateFlag) {
			data.setUpdateDate(new Date());
		}
	}
}
