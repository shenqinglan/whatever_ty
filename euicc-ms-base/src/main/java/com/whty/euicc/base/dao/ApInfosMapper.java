package com.whty.euicc.base.dao;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.ApInfo;
import com.whty.euicc.base.pojo.ApInfoExample;

public interface ApInfosMapper {
    int countByExample(ApInfoExample example);

    int deleteByExample(ApInfoExample example);

    int deleteByPrimaryKey(String userId);

    int insert(ApInfo record);

    int insertSelective(ApInfo record);

    PageList<ApInfo> selectByExample(ApInfoExample example, PageBounds pageBounds);
    
    PageList<ApInfo> selectByExample(ApInfoExample example);
    
    ApInfo selectByPrimaryKey(String userId);

    int updateByExampleSelective(@Param("record") ApInfo record, @Param("example") ApInfoExample example);

    int updateByExample(@Param("record") ApInfo record, @Param("example") ApInfoExample example);

    int updateByPrimaryKeySelective(ApInfo record);

    int updateByPrimaryKey(ApInfo record);
    
}