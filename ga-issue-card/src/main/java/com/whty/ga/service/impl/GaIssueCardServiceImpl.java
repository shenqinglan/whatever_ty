package com.whty.ga.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.whty.ga.pojo.GaCardInfo;
import com.whty.ga.repository.GaIssueCardRepo;
import com.whty.ga.service.IGaIssueCardService;

/**
 * @ClassName GaIssueCardServiceImpl
 * @author Administrator
 * @date 2017-3-3 上午9:54:03
 * @Description TODO(卡片信息业务层代码)
 */
@Transactional
@Service("gaIssueCardServiceImpl")
public class GaIssueCardServiceImpl implements IGaIssueCardService {
	
	@Resource
	private GaIssueCardRepo cardRepo;

	@Override
	public GaCardInfo findCardInfoByCardNo(String cardNo) {
		return cardRepo.findByCardNo(cardNo);
	}
	
	@Override
	public void saveCardInfo(GaCardInfo card) {
		cardRepo.save(card);
	}

	@Override
	public void assignGaIssueCardInfo(GaCardInfo card) {
		Date time = new Date();
		card.setDelFlag("0");
		card.setCreateDate(time);
		card.setUpdateDate(time);
	}

	@Override
	public void assignGaIssueCardInfo(GaCardInfo card, GaCardInfo data) {
		boolean updateFlag = false;
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(card.getCardNo()) && 
				!StringUtils.equals(card.getCardNo(), data.getCardNo())) {
			updateFlag = true;
			data.setCardNo(card.getCardNo());
		} 
		if (card.getExpiryTime()!=null && StringUtils.isNotBlank(sdfDate.format(card.getExpiryTime())) && 
				!StringUtils.equals(sdfDate.format(card.getExpiryTime()), sdfDate.format(data.getExpiryTime()))) {
			updateFlag = true;
			data.setExpiryTime(card.getExpiryTime());
		}
		if (StringUtils.isNotBlank(card.getRelation()) && 
				!StringUtils.equals(card.getRelation(), data.getRelation())) {
			updateFlag = true;
			data.setRelation(card.getRelation());
		} 
		if (StringUtils.isNotBlank(card.getCardTypeCode()) && 
				!StringUtils.equals(card.getCardTypeCode(), data.getCardTypeCode())) {
			updateFlag = true;
			data.setCardTypeCode(card.getCardTypeCode());
		} 
		if (StringUtils.isNotBlank(card.getBlackListFlag()) && 
				!StringUtils.equals(card.getBlackListFlag(), data.getBlackListFlag())) {
			updateFlag = true;
			data.setBlackListFlag(card.getBlackListFlag());
		} 
		if (StringUtils.isNotBlank(card.getIssuer()) && 
				!StringUtils.equals(card.getIssuer(), data.getIssuer())) {
			updateFlag = true;
			data.setIssuer(card.getIssuer());
		} 
		if (card.getIssueDate()!=null && StringUtils.isNotBlank(sdfDate.format(card.getIssueDate())) && 
				!StringUtils.equals(sdfDate.format(card.getIssueDate()), sdfDate.format(data.getIssueDate()))) {
			updateFlag = true;
			data.setIssueDate(card.getIssueDate());
		}
		if (StringUtils.isNotBlank(card.getInLimitCount()) && 
				!StringUtils.equals(card.getInLimitCount(), data.getInLimitCount())) {
			updateFlag = true;
			data.setInLimitCount(card.getInLimitCount());
		} 
		if (StringUtils.isNotBlank(card.getOutLimitCount()) && 
				!StringUtils.equals(card.getOutLimitCount(), data.getOutLimitCount())) {
			updateFlag = true;
			data.setOutLimitCount(card.getOutLimitCount());
		} 
		if (StringUtils.isNotBlank(card.getRemarks()) && 
				!StringUtils.equals(card.getRemarks(), data.getRemarks())) {
			updateFlag = true;
			data.setRemarks(card.getRemarks());
		} 
		if (updateFlag) {
			data.setUpdateDate(new Date());
		}
	}
}
