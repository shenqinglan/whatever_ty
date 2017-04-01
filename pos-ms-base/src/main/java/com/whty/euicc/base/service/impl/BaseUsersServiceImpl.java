package com.whty.euicc.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.dao.ApprovalInfoMapper;
import com.whty.euicc.base.dao.BaseUsersMapper;
import com.whty.euicc.base.pojo.ApprovalInfo;
import com.whty.euicc.base.pojo.BaseUsers;
import com.whty.euicc.base.pojo.BaseUsersExample;
import com.whty.euicc.base.service.BaseUsersService;

@Service
@Transactional
public class BaseUsersServiceImpl implements BaseUsersService {

	@Autowired
	private BaseUsersMapper baseUsersMapper;
	
	@Autowired
	private ApprovalInfoMapper approvalInfoMapper;
	
	/**
	 * 选择性插入
	 */
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public int insertSelective(BaseUsers baseUsers, ApprovalInfo approvalInfo) {
		int flg1 = baseUsersMapper.insertSelective(baseUsers);
		int flg2 = approvalInfoMapper.insertSelective(approvalInfo);
		return flg1 + flg2;
	}
	
	/**
	 * 根据主键查询
	 */
	@Override
	public BaseUsers selectByPrimaryKey(String userId) {
		return baseUsersMapper.selectByPrimaryKey(userId);
	}
		
	/**
	 * 根据条件进行查询(分页)
	 * @param example
	 * @return
	 */
	@Override
	public PageList<BaseUsers> selectByExample(BaseUsersExample example,PageBounds pageBounds){
		return baseUsersMapper.selectByExample(example,pageBounds);
	}

	/**
	 * 根据主键进行选择性更新
	 */
	@Override
	public int updateSelectiveByPrimaryKey(BaseUsers record) {
		return baseUsersMapper.updateByPrimaryKeySelective(record);
	}

	/**
	 * 根据条件进行选择性更新
	 */
	@Override
	public int updateSelectiveByRecord(BaseUsers record,
			BaseUsersExample example) {
		return baseUsersMapper.updateByExampleSelective(record, example);
	}

	/**
	 * 根据主键删除对象
	 */
	@Override
	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	public int deleteByPrimaryKey(String userId) {
		return baseUsersMapper.deleteByPrimaryKey(userId) + 
				approvalInfoMapper.deleteByPrimaryKey(userId);
	}

	/**
	 * 根据条件删除对象
	 */
	@Override
    public int deleteByExample(BaseUsersExample example){
    	return baseUsersMapper.deleteByExample(example);
    }

	@Override
	public int upateUserStatus(BaseUsers record) {
		return baseUsersMapper.upateUserStatus(record);
	}
	
	/**
	 * 根据用户名、密码获取用户信息
	 * @param username
	 * @param password
	 * @return
	 */
	@Override
	public BaseUsers selectByUserPwd(String username, String password) {
		BaseUsersExample example = new BaseUsersExample();
		BaseUsersExample.Criteria criteria = example.createCriteria();
		criteria.andUserAccountEqualTo(username);
		criteria.andUserPasswordEqualTo(password);
		PageList<BaseUsers> bList = baseUsersMapper.selectByExample(example, new PageBounds());
		return CollectionUtils.isEmpty(bList)?null:bList.get(0);
	}

	public BaseUsers selectByUserAccount(String userAccount) {
		return baseUsersMapper.selectByUserAccount(userAccount);
	}

}
