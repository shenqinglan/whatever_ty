package com.whty.euicc.data.dao;

import java.util.List;

import com.whty.euicc.data.pojo.EuiccScp80;

public interface EuiccScp80Mapper {
	 int deleteByPrimaryKey(String Scp80Id);

	 int insert(EuiccScp80 record);

	 int insertSelective(EuiccScp80 record);

	 EuiccScp80 selectByPrimaryKey(String scp80Id);
	    
     String selectByIdAndVersion(EuiccScp80 record);

     int updateByPrimaryKeySelective(EuiccScp80 record);

     int updateByPrimaryKey(EuiccScp80 record);
	    
     List<EuiccScp80> selectLists();
     
     List<EuiccScp80> selectListByEid(String eid);
     
     int deleteByEid(String eid);
     
     int insertListScp80(List<EuiccScp80> scp80s);

}
	