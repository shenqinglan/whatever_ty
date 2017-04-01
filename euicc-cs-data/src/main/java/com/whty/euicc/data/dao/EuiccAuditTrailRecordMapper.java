package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.EuiccAuditTrailRecord;

public interface EuiccAuditTrailRecordMapper {
    int deleteByPrimaryKey(String auditId);

    int insert(EuiccAuditTrailRecord record);

    int insertSelective(EuiccAuditTrailRecord record);

    EuiccAuditTrailRecord selectByPrimaryKey(String auditId);

    int updateByPrimaryKeySelective(EuiccAuditTrailRecord record);

    int updateByPrimaryKey(EuiccAuditTrailRecord record);
}