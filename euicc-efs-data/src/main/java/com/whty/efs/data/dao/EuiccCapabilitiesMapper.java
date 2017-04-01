package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccCapabilities;
import com.whty.efs.data.pojo.EuiccCapabilitiesExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccCapabilitiesMapper {
    int countByExample(EuiccCapabilitiesExample example);

    int deleteByExample(EuiccCapabilitiesExample example);

    int deleteByPrimaryKey(String capabilitieId);
    
    int deleteByEid(String eid);

    int insert(EuiccCapabilities record);

    int insertSelective(EuiccCapabilities record);

    List<EuiccCapabilities> selectByExample(EuiccCapabilitiesExample example);

    EuiccCapabilities selectByPrimaryKey(String capabilitieId);

    int updateByExampleSelective(@Param("record") EuiccCapabilities record, @Param("example") EuiccCapabilitiesExample example);

    int updateByExample(@Param("record") EuiccCapabilities record, @Param("example") EuiccCapabilitiesExample example);

    int updateByPrimaryKeySelective(EuiccCapabilities record);

    int updateByPrimaryKey(EuiccCapabilities record);
}