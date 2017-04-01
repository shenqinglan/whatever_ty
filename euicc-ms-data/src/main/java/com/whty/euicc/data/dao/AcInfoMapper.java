package com.whty.euicc.data.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.AcInfo;
import com.whty.euicc.data.pojo.AcInfoExample;

public interface AcInfoMapper {
    int countByExample(AcInfoExample example);

    int deleteByExample(AcInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(AcInfo record);

    int insertSelective(AcInfo record);

    PageList<AcInfo> selectByExample(AcInfoExample example,PageBounds pageBounds);
    
    List<AcInfo> selectByExample(AcInfoExample example);

    AcInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") AcInfo record, @Param("example") AcInfoExample example);

    int updateByExample(@Param("record") AcInfo record, @Param("example") AcInfoExample example);

    int updateByPrimaryKeySelective(AcInfo record);

    int updateByPrimaryKey(AcInfo record);
}