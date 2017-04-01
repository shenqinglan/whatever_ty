package com.whty.euicc.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dao.ApprovalInfoMapper;
import com.whty.euicc.base.pojo.ApprovalInfo;
import com.whty.euicc.base.pojo.ApprovalInfoExample;
import com.whty.euicc.base.pojo.ApprovalUserInfo;
import com.whty.euicc.base.service.ApprovalInfoService;
/**
 * @author liuwei
 * @date 2016-09-20 09:50
 *
 * @version 1.0
 */
@Service
@Transactional
public class ApprovalInfoServiceImpl implements ApprovalInfoService{

	@Autowired
	private ApprovalInfoMapper approvalInfoMapper;
	
	public ApprovalInfo selectByPrimaryKey(String approvalUserId) {
		return approvalInfoMapper.selectByPrimaryKey(approvalUserId);
	}

	public int insertSelective(ApprovalInfo record) {
		return approvalInfoMapper.insertSelective(record);
	}

	public int updateByPrimaryKeySelective(ApprovalInfo record) {
		return approvalInfoMapper.updateByPrimaryKeySelective(record);
	}

	public PageList<ApprovalUserInfo> selectByExample(
			ApprovalInfoExample example, PageBounds pageBounds) {
		return approvalInfoMapper.selectByExample(example, pageBounds);
	}

	public ApprovalUserInfo selectUserInfoByPrimaryKey(String approvalUserId) {
		return approvalInfoMapper.selectUserInfoByPrimaryKey(approvalUserId);
	}	
}
