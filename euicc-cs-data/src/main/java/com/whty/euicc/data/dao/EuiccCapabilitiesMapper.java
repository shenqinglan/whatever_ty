package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccCapabilities;

public interface EuiccCapabilitiesMapper {
    int deleteByPrimaryKey(String capabilitieId);

    int insert(EuiccCapabilities record);

    int insertSelective(EuiccCapabilities record);

    EuiccCapabilities selectByPrimaryKey(String capabilitieId);

    int updateByPrimaryKeySelective(EuiccCapabilities record);

    int updateByPrimaryKey(EuiccCapabilities record);
}