package com.whty.euicc.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.BaseFields;
import com.whty.euicc.base.pojo.BaseFieldsExample;

public interface BaseFieldsMapper {
    int countByExample(BaseFieldsExample example);

    int deleteByExample(BaseFieldsExample example);

    int deleteByPrimaryKey(String fieldId);

    int insert(BaseFields record);

    int insertSelective(BaseFields record);

    PageList<BaseFields> selectByExample(BaseFieldsExample example, PageBounds pageBounds);
    
    List<BaseFields> selectByExample(BaseFieldsExample example);

    BaseFields selectByPrimaryKey(String fieldId);

    int updateByExampleSelective(@Param("record") BaseFields record, @Param("example") BaseFieldsExample example);

    int updateByExample(@Param("record") BaseFields record, @Param("example") BaseFieldsExample example);

    int updateByPrimaryKeySelective(BaseFields record);

    int updateByPrimaryKey(BaseFields record);
}