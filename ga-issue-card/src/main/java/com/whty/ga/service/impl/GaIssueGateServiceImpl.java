package com.whty.ga.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.whty.ga.pojo.GaGateInfo;
import com.whty.ga.repository.GaIssueGateRepo;
import com.whty.ga.service.IGaIssueGateService;

/**
 * @ClassName GaIssueGateServiceImpl
 * @author Administrator
 * @date 2017-3-3 上午9:54:17
 * @Description TODO(门禁信息业务层代码)
 */
@Transactional
@Service("gaIssueGateServiceImpl")
public class GaIssueGateServiceImpl implements IGaIssueGateService {

	@Resource
	private GaIssueGateRepo gateRepo;

	@Override
	public GaGateInfo findGateInfoByAreaNameAndBuildingNoAndUnitNo(
			String areaName, String buildingNo, String unitNo) {
		return gateRepo.findBy3Elements(areaName, buildingNo, unitNo);
	}

	@Override
	public void assignGaIssueGateInfo(GaGateInfo gate) {
		Date time = new Date();
		gate.setDelFlag("0");
		gate.setCreateDate(time);
		gate.setUpdateDate(time);
	}

	@Override
	public void assignGaIssueGateInfo(GaGateInfo gate, GaGateInfo data) {
		boolean updateFlag = false;
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(gate.getGateCode()) && 
				!StringUtils.equals(gate.getGateCode(), data.getGateCode())) {
			updateFlag = true;
			data.setGateCode(gate.getGateCode());
		} 
		if (StringUtils.isNotBlank(gate.getGateType()) && 
				!StringUtils.equals(gate.getGateType(), data.getGateType())) {
			updateFlag = true;
			data.setGateType(gate.getGateType());
		} 
		if (StringUtils.isNotBlank(gate.getBuildingNo()) && 
				!StringUtils.equals(gate.getBuildingNo(), data.getBuildingNo())) {
			updateFlag = true;
			data.setBuildingNo(gate.getBuildingNo());
		} 
		if (StringUtils.isNotBlank(gate.getUnitNo()) && 
				!StringUtils.equals(gate.getUnitNo(), data.getUnitNo())) {
			updateFlag = true;
			data.setUnitNo(gate.getUnitNo());
		} 
		if (gate.getInstallDate()!=null && StringUtils.isNotBlank(sdfDate.format(gate.getInstallDate())) && 
				!StringUtils.equals(sdfDate.format(gate.getInstallDate()), sdfDate.format(data.getInstallDate()))) {
			updateFlag = true;
			data.setInstallDate(gate.getInstallDate());
		}
		if (gate.getLastMaintainDate()!=null && StringUtils.isNotBlank(sdfDate.format(gate.getLastMaintainDate())) && 
				!StringUtils.equals(sdfDate.format(gate.getLastMaintainDate()), sdfDate.format(data.getLastMaintainDate()))) {
			updateFlag = true;
			data.setLastMaintainDate(gate.getLastMaintainDate());
		}
		if (StringUtils.isNotBlank(gate.getRemarks()) && 
				!StringUtils.equals(gate.getRemarks(), data.getRemarks())) {
			updateFlag = true;
			data.setRemarks(gate.getRemarks());
		} 
		if (updateFlag) {
			data.setUpdateDate(new Date());
		}
	}

	@Override
	public void saveGateInfo(GaGateInfo gate) {
		gateRepo.save(gate);
	}
}
