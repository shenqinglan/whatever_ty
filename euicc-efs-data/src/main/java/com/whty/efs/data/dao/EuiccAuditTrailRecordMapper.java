package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccAuditTrailRecord;
import com.whty.efs.data.pojo.EuiccAuditTrailRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccAuditTrailRecordMapper {
    int countByExample(EuiccAuditTrailRecordExample example);

    int deleteByExample(EuiccAuditTrailRecordExample example);

    int deleteByPrimaryKey(String auditId);
    
    int deleteByEid(String auditId);

    int insert(EuiccAuditTrailRecord record);

    int insertSelective(EuiccAuditTrailRecord record);

    List<EuiccAuditTrailRecord> selectByExample(EuiccAuditTrailRecordExample example);

    EuiccAuditTrailRecord selectByPrimaryKey(String auditId);

    int updateByExampleSelective(@Param("record") EuiccAuditTrailRecord record, @Param("example") EuiccAuditTrailRecordExample example);

    int updateByExample(@Param("record") EuiccAuditTrailRecord record, @Param("example") EuiccAuditTrailRecordExample example);

    int updateByPrimaryKeySelective(EuiccAuditTrailRecord record);

    int updateByPrimaryKey(EuiccAuditTrailRecord record);
}