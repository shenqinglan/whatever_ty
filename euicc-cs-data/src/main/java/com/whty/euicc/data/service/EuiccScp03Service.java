package com.whty.euicc.data.service;

import java.util.List;

import com.whty.euicc.data.pojo.EuiccScp03;

public interface EuiccScp03Service {
	/**
	 * 通过主键删除记录
	 * @param scp03Id
	 * @return
	 */
	int deleteByPrimaryKey(String scp03Id);

	/**
	 * 通过eid删除记录
	 * @param eid
	 * @return
	 */
	int deleteByEid(String eid);
	/**
	 * 通过eid and isdPAid删除记录
	 * @param eid
	 * @return
	 */
	int deleteByEidAndIsdPAid(EuiccScp03 record);
	/**
	 * 插入一条记录
	 * @param record
	 * @return
	 */
    int insert(EuiccScp03 record);

    int insertSelective(EuiccScp03 record);

    /**
     * 通过主键选择记录
     * @param scp03Id
     * @return
     */
    EuiccScp03 selectByPrimaryKey(String scp03Id);

    int updateByPrimaryKeySelective(EuiccScp03 record);

    /**
     * 通过主键更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(EuiccScp03 record);
    
    
    EuiccScp03 selectByScp03(EuiccScp03 record);
    
    List<EuiccScp03> selectListByEid(String eid);
    
    int insertListScp03(List<EuiccScp03> scp03s);


}
