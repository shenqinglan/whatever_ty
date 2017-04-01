package com.whty.ga.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.whty.ga.pojo.GaPersonInfo;
import com.whty.ga.repository.GaIssuePersonRepo;
import com.whty.ga.service.IGaIssuePersonService;

/**
 * @ClassName GaIssuePersonServiceImpl
 * @author Administrator
 * @date 2017-3-3 上午9:55:11
 * @Description TODO(个人基本信息业务层代码)
 */
@Transactional
@Service("gaIssuePersonServiceImpl")
public class GaIssuePersonServiceImpl implements IGaIssuePersonService {
	
	@Resource
	private GaIssuePersonRepo personRepo;

	@Override
	public GaPersonInfo findPersonInfoByIdCardNo(String idCardNo) {
		return personRepo.findByIdCardNo(idCardNo);
	}
	
	@Override
	public GaPersonInfo findPersonInfoByName(String name) {
		// TODO Auto-generated method stub
		return personRepo.findByName(name);
	}
	
	@Override
	public GaPersonInfo findPersonInfoByNameAndMobil(String name, String mobil) {
		// TODO Auto-generated method stub
		return personRepo.findByNameAndMobil(name, mobil);
	}
	
	@Override
	public void savePersonInfo(GaPersonInfo person) {
		// TODO Auto-generated method stub
		personRepo.save(person);
	}

	@Override
	public void assignGaIssuePersonInfo(GaPersonInfo person) {
		Date time = new Date();
		person.setDelFlag("0");
		person.setCreateDate(time);
		person.setUpdateDate(time);
	}

	@Override
	public void assignGaIssuePersonInfo(GaPersonInfo person, GaPersonInfo data) {
		boolean updateFlag = false;
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(person.getName()) && 
				!StringUtils.equals(person.getName(), data.getName())) {
			updateFlag = true;
			data.setName(person.getName());
		} 
		if (StringUtils.isNotBlank(person.getSex()) && 
				!StringUtils.equals(person.getSex(), data.getSex())) {
			updateFlag = true;
			data.setSex(person.getSex());
		}
		if (StringUtils.isNotBlank(person.getEthnic()) && 
				!StringUtils.equals(person.getEthnic(), data.getEthnic())) {
			updateFlag = true;
			data.setEthnic(person.getEthnic());
		}
		if (person.getBirthday() != null && StringUtils.isNotBlank(sdfDate.format(person.getBirthday())) && 
				!StringUtils.equals(sdfDate.format(person.getBirthday()), sdfDate.format(data.getBirthday()))) {
			updateFlag = true;
			data.setBirthday(person.getBirthday());
		}
		if (StringUtils.isNotBlank(person.getPoliticalStatus()) && 
				!StringUtils.equals(person.getPoliticalStatus(), data.getPoliticalStatus())) {
			updateFlag = true;
			data.setPoliticalStatus(person.getPoliticalStatus());
		}
		if (StringUtils.isNotBlank(person.getEducationDegree()) && 
				!StringUtils.equals(person.getEducationDegree(), data.getEducationDegree())) {
			updateFlag = true;
			data.setEducationDegree(person.getEducationDegree());
		}
		if (StringUtils.isNotBlank(person.getHeight()) && 
				!StringUtils.equals(person.getHeight(), data.getHeight())) {
			updateFlag = true;
			data.setHeight(person.getHeight());
		}
		if (StringUtils.isNotBlank(person.getMaritalStatus()) && 
				!StringUtils.equals(person.getMaritalStatus(), data.getMaritalStatus())) {
			updateFlag = true;
			data.setMaritalStatus(person.getMaritalStatus());
		}
		if (StringUtils.isNotBlank(person.getHukouAreaNo()) && 
				!StringUtils.equals(person.getHukouAreaNo(), data.getHukouAreaNo())) {
			updateFlag = true;
			data.setHukouAreaNo(person.getHukouAreaNo());
		}
		if (StringUtils.isNotBlank(person.getHukouAddress()) && 
				!StringUtils.equals(person.getHukouAddress(), data.getHukouAddress())) {
			updateFlag = true;
			data.setHukouAddress(person.getHukouAddress());
		}
		if (StringUtils.isNotBlank(person.getResidenceAddress()) && 
				!StringUtils.equals(person.getResidenceAddress(), data.getResidenceAddress())) {
			updateFlag = true;
			data.setResidenceAddress(person.getResidenceAddress());
		}
		if (StringUtils.isNotBlank(person.getFormerAddress()) && 
				!StringUtils.equals(person.getFormerAddress(), data.getFormerAddress())) {
			updateFlag = true;
			data.setFormerAddress(person.getFormerAddress());
		}
		if (StringUtils.isNotBlank(person.getBloodType()) && 
				!StringUtils.equals(person.getBloodType(), data.getBloodType())) {
			updateFlag = true;
			data.setBloodType(person.getBloodType());
		}
		if (StringUtils.isNotBlank(person.getReligion()) && 
				!StringUtils.equals(person.getReligion(), data.getReligion())) {
			updateFlag = true;
			data.setReligion(person.getReligion());
		}
		if (StringUtils.isNotBlank(person.getCompany()) && 
				!StringUtils.equals(person.getCompany(), data.getCompany())) {
			updateFlag = true;
			data.setCompany(person.getCompany());
		}
		if (StringUtils.isNotBlank(person.getTel()) && 
				!StringUtils.equals(person.getTel(), data.getTel())) {
			updateFlag = true;
			data.setTel(person.getTel());
		}
		if (StringUtils.isNotBlank(person.getMobil()) && 
				!StringUtils.equals(person.getMobil(), data.getMobil())) {
			updateFlag = true;
			data.setMobil(person.getMobil());
		}
		if (StringUtils.isNotBlank(person.getFace()) && 
				!StringUtils.equals(person.getFace(), data.getFace())) {
			updateFlag = true;
			data.setFace(person.getFace());
		}
		if (StringUtils.isNotBlank(person.getPersonTypeCode()) && 
				!StringUtils.equals(person.getPersonTypeCode(), data.getPersonTypeCode())) {
			updateFlag = true;
			data.setPersonTypeCode(person.getPersonTypeCode());
		}
		if (StringUtils.isNotBlank(person.getIssueOrgan()) && 
				!StringUtils.equals(person.getIssueOrgan(), data.getIssueOrgan())) {
			updateFlag = true;
			data.setIssueOrgan(person.getIssueOrgan());
		}
		if (person.getExpiryTime()!= null && StringUtils.isNotBlank(sdfDate.format(person.getExpiryTime())) && 
				!StringUtils.equals(sdfDate.format(person.getExpiryTime()), sdfDate.format(data.getExpiryTime()))) {
			updateFlag = true;
			data.setExpiryTime(person.getExpiryTime());
		}
		if (StringUtils.isNotBlank(person.getRemarks()) && 
				!StringUtils.equals(person.getRemarks(), data.getRemarks())) {
			updateFlag = true;
			data.setRemarks(person.getRemarks());
		} 
		
		if (updateFlag) {
			data.setUpdateDate(new Date());
		}
	}
}
