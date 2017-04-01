package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccEumSignature;
import com.whty.efs.data.pojo.EuiccEumSignatureExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccEumSignatureMapper {
    int countByExample(EuiccEumSignatureExample example);

    int deleteByExample(EuiccEumSignatureExample example);

    int deleteByPrimaryKey(String signatureId);

    int insert(EuiccEumSignature record);

    int insertSelective(EuiccEumSignature record);

    List<EuiccEumSignature> selectByExample(EuiccEumSignatureExample example);

    EuiccEumSignature selectByPrimaryKey(String signatureId);

    int updateByExampleSelective(@Param("record") EuiccEumSignature record, @Param("example") EuiccEumSignatureExample example);

    int updateByExample(@Param("record") EuiccEumSignature record, @Param("example") EuiccEumSignatureExample example);

    int updateByPrimaryKeySelective(EuiccEumSignature record);

    int updateByPrimaryKey(EuiccEumSignature record);
}