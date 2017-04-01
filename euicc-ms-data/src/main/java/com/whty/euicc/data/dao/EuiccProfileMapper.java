package com.whty.euicc.data.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.EuiccProfileExample;
import com.whty.euicc.data.pojo.EuiccProfileWithBLOBs;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccProfileMapper {
    int countByExample(EuiccProfileExample example);

    int deleteByExample(EuiccProfileExample example);

    int deleteByPrimaryKey(String iccid);

    int insert(EuiccProfileWithBLOBs record);

    int insertSelective(EuiccProfileWithBLOBs record);
    
    int insertProfileSelective(EuiccProfile record);

    List<EuiccProfileWithBLOBs> selectByExampleWithBLOBs(EuiccProfileExample example);

    List<EuiccProfile> selectByExample(EuiccProfileExample example);
    
    PageList<EuiccProfile> selectByExample(EuiccProfileExample example,PageBounds pageBounds);
    
    PageList<EuiccProfile> selectByExampleForInstall(String eid,PageBounds pageBounds);
    
    PageList<EuiccProfileWithBLOBs> selectByExampleWithBLOBs(EuiccProfileExample example,PageBounds pageBounds);

    EuiccProfileWithBLOBs selectByPrimaryKey(String iccid);

    int updateByExampleSelective(@Param("record") EuiccProfileWithBLOBs record, @Param("example") EuiccProfileExample example);

    int updateByExampleWithBLOBs(@Param("record") EuiccProfileWithBLOBs record, @Param("example") EuiccProfileExample example);

    int updateByExample(@Param("record") EuiccProfile record, @Param("example") EuiccProfileExample example);

    int updateByPrimaryKeySelective(EuiccProfileWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(EuiccProfileWithBLOBs record);

    int updateByPrimaryKey(EuiccProfile record);
    
    int updateByEid(EuiccProfile record);
    
    int updatePol2ByPrimaryKey(EuiccProfile record);
    
    List<EuiccProfileWithBLOBs> selectByEidAndFallback(EuiccProfileWithBLOBs record);
    EuiccProfileWithBLOBs selectEuiccProfileByStateAndEid(EuiccProfileWithBLOBs euiccProfile);
}