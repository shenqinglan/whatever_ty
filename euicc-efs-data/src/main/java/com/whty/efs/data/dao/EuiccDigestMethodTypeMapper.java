package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccDigestMethodType;
import com.whty.efs.data.pojo.EuiccDigestMethodTypeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccDigestMethodTypeMapper {
    int countByExample(EuiccDigestMethodTypeExample example);

    int deleteByExample(EuiccDigestMethodTypeExample example);

    int deleteByPrimaryKey(String typeId);
    
    int deleteByReferenceId(String referenceId);

    int insert(EuiccDigestMethodType record);

    int insertSelective(EuiccDigestMethodType record);

    List<EuiccDigestMethodType> selectByExample(EuiccDigestMethodTypeExample example);

    EuiccDigestMethodType selectByPrimaryKey(String typeId);

    int updateByExampleSelective(@Param("record") EuiccDigestMethodType record, @Param("example") EuiccDigestMethodTypeExample example);

    int updateByExample(@Param("record") EuiccDigestMethodType record, @Param("example") EuiccDigestMethodTypeExample example);

    int updateByPrimaryKeySelective(EuiccDigestMethodType record);

    int updateByPrimaryKey(EuiccDigestMethodType record);
}