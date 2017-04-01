package com.whty.rsp.data.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.whty.rsp.data.pojo.RspProfile;
import com.whty.rsp.data.pojo.RspProfileExample;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;

public interface RspProfileMapper {
    int countByExample(RspProfileExample example);

    int deleteByExample(RspProfileExample example);

    int deleteByPrimaryKey(String iccid);

    int insert(RspProfileWithBLOBs record);

    int insertSelective(RspProfileWithBLOBs record);

    List<RspProfileWithBLOBs> selectByExampleWithBLOBs(RspProfileExample example);

    List<RspProfile> selectByExample(RspProfileExample example);

    RspProfileWithBLOBs selectByPrimaryKey(String iccid);
    
    RspProfileWithBLOBs selectByProfileType(String profileType);
    
    RspProfileWithBLOBs selectByMatchingId(String matchingId);
    
    RspProfileWithBLOBs selectByEid(String eid);
    
    RspProfileWithBLOBs selectByMsisdn(String msisdn);

    int updateByExampleSelective(@Param("record") RspProfileWithBLOBs record, @Param("example") RspProfileExample example);

    int updateByExampleWithBLOBs(@Param("record") RspProfileWithBLOBs record, @Param("example") RspProfileExample example);

    int updateByExample(@Param("record") RspProfile record, @Param("example") RspProfileExample example);

    int updateByPrimaryKeySelective(RspProfileWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(RspProfileWithBLOBs record);

    int updateByPrimaryKey(RspProfile record);
}