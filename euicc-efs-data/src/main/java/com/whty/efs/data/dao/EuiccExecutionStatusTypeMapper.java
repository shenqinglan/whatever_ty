package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccExecutionStatusType;
import com.whty.efs.data.pojo.EuiccExecutionStatusTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccExecutionStatusTypeMapper {
    int countByExample(EuiccExecutionStatusTypeExample example);

    int deleteByExample(EuiccExecutionStatusTypeExample example);

    int deleteByPrimaryKey(String statusId);
    
    int deleteByAuditId(String auditId);

    int insert(EuiccExecutionStatusType record);

    int insertSelective(EuiccExecutionStatusType record);

    List<EuiccExecutionStatusType> selectByExample(EuiccExecutionStatusTypeExample example);

    EuiccExecutionStatusType selectByPrimaryKey(String statusId);

    int updateByExampleSelective(@Param("record") EuiccExecutionStatusType record, @Param("example") EuiccExecutionStatusTypeExample example);

    int updateByExample(@Param("record") EuiccExecutionStatusType record, @Param("example") EuiccExecutionStatusTypeExample example);

    int updateByPrimaryKeySelective(EuiccExecutionStatusType record);

    int updateByPrimaryKey(EuiccExecutionStatusType record);
}