package com.whty.euicc.data.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.dao.TerminalDeviceInfoMapper;
import com.whty.euicc.data.pojo.RootKeyInfo;
import com.whty.euicc.data.pojo.TerminalDeviceInfo;
import com.whty.euicc.data.pojo.TerminalDeviceInfoExample;
import com.whty.euicc.data.service.TerminalDeviceInfoService;
/**
 * @author dzmsoft
 * @date 2016-05-30 15:37
 *
 * @version 1.0
 */
@Service
@Transactional
public class TerminalDeviceInfoServiceImpl implements TerminalDeviceInfoService{

	@Autowired
	private TerminalDeviceInfoMapper terminalDeviceInfoMapper;
	
	public TerminalDeviceInfo selectByPrimaryKey(String sn) {
		return terminalDeviceInfoMapper.selectByPrimaryKey(sn);
	}

	public PageList<TerminalDeviceInfo> selectByExample(TerminalDeviceInfoExample example,PageBounds pageBounds) {
		return terminalDeviceInfoMapper.selectByExample(example, pageBounds);
	}
	
	public int insertSelective(TerminalDeviceInfo record) {
		return terminalDeviceInfoMapper.insertSelective(record);
	}

	public int deleteByPrimaryKey(String sn) {
		return terminalDeviceInfoMapper.deleteByPrimaryKey(sn);
	}

	public int updateByPrimaryKeySelective(TerminalDeviceInfo record) {
		return terminalDeviceInfoMapper.updateByPrimaryKeySelective(record);
	}
	
	public RootKeyInfo selectRootKey(String sn) {
		return terminalDeviceInfoMapper.selectRootKey(sn);
	}
}
