package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccProfile;
import com.whty.efs.data.pojo.EuiccProfileExample;
import com.whty.efs.data.pojo.EuiccProfileWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccProfileMapper {
    int countByExample(EuiccProfileExample example);

    int deleteByExample(EuiccProfileExample example);

    int deleteByPrimaryKey(String iccid);
    
    int deleteByEid(String eid);

    int insert(EuiccProfileWithBLOBs record);

    int insertSelective(EuiccProfileWithBLOBs record);

    List<EuiccProfileWithBLOBs> selectByExampleWithBLOBs(EuiccProfileExample example);

    List<EuiccProfile> selectByExample(EuiccProfileExample example);

    EuiccProfileWithBLOBs selectByPrimaryKey(String iccid);
    
    EuiccProfile selectByIccid(String iccid);

    int updateByExampleSelective(@Param("record") EuiccProfileWithBLOBs record, @Param("example") EuiccProfileExample example);

    int updateByExampleWithBLOBs(@Param("record") EuiccProfileWithBLOBs record, @Param("example") EuiccProfileExample example);

    int updateByExample(@Param("record") EuiccProfile record, @Param("example") EuiccProfileExample example);

    int updateByPrimaryKeySelective(EuiccProfileWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(EuiccProfileWithBLOBs record);

    int updateByPrimaryKey(EuiccProfile record);
    
    List<EuiccProfileWithBLOBs> selectByEid(String eid);
    
    /**
     * 
     * @author Administrator
     * @date 2016-11-1
     * @param eid
     * @return
     * @Description TODO(查询卡管理的profile)
     */
   List<EuiccProfile> findMgrProfile(String eid);

    /**
     * @author Administrator
     * @date 2016-11-1
     * @param eid
     * @return
     * @Description TODO(查询空白profile)
     */
   	List<EuiccProfile> findInstallProfile(String eid);
}