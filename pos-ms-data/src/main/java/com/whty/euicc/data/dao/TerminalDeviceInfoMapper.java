package com.whty.euicc.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.RootKeyInfo;
import com.whty.euicc.data.pojo.TerminalDeviceInfo;
import com.whty.euicc.data.pojo.TerminalDeviceInfoExample;

public interface TerminalDeviceInfoMapper {
    int countByExample(TerminalDeviceInfoExample example);

    int deleteByExample(TerminalDeviceInfoExample example);

    int deleteByPrimaryKey(String sn);

    int insert(TerminalDeviceInfo record);

    int insertSelective(TerminalDeviceInfo record);

    List<TerminalDeviceInfo> selectByExample(TerminalDeviceInfoExample example);
    
    PageList<TerminalDeviceInfo> selectByExample(TerminalDeviceInfoExample example,PageBounds pageBounds);

    TerminalDeviceInfo selectByPrimaryKey(String sn);
    
    RootKeyInfo selectRootKey(String sn);

    int updateByExampleSelective(@Param("record") TerminalDeviceInfo record, @Param("example") TerminalDeviceInfoExample example);

    int updateByExample(@Param("record") TerminalDeviceInfo record, @Param("example") TerminalDeviceInfoExample example);

    int updateByPrimaryKeySelective(TerminalDeviceInfo record);

    int updateByPrimaryKey(TerminalDeviceInfo record);
}