package com.whty.euicc.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.CheckLogs;
import com.whty.euicc.data.pojo.CheckLogsExample;
import com.whty.euicc.data.pojo.LogsUser;

public interface CheckLogsMapper {
    int countByExample(CheckLogsExample example);

    int deleteByExample(CheckLogsExample example);

    int deleteByPrimaryKey(String id);

    int insert(CheckLogs record);

    int insertSelective(CheckLogs record);

    List<CheckLogs> selectByExampleWithBLOBs(CheckLogsExample example);

    PageList<CheckLogs> selectByExample(CheckLogsExample example,PageBounds pageBounds);
    
    List<CheckLogs> selectByExample(CheckLogsExample example);

    CheckLogs selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") CheckLogs record, @Param("example") CheckLogsExample example);

    int updateByExampleWithBLOBs(@Param("record") CheckLogs record, @Param("example") CheckLogsExample example);

    int updateByExample(@Param("record") CheckLogs record, @Param("example") CheckLogsExample example);

    int updateByPrimaryKeySelective(CheckLogs record);

    int updateByPrimaryKeyWithBLOBs(CheckLogs record);

    int updateByPrimaryKey(CheckLogs record);
    
    PageList<LogsUser> selectByObjectId(CheckLogsExample example,PageBounds pageBounds);
    
    PageList<LogsUser> selectApByObjectId(CheckLogsExample example,PageBounds pageBounds);
}