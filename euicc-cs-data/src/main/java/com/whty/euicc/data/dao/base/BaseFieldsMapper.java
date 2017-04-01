// Copyright (C) 2012 WHTY
package com.whty.euicc.data.dao.base;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.whty.euicc.data.dto.Criteria;
import com.whty.euicc.data.pojo.BaseFields;


public interface BaseFieldsMapper {
    /**
     * 根据条件查询记录总数
     */
    int countByExample(Criteria example);

    /**
     * 根据条件删除记录
     */
    int deleteByExample(Criteria example);

    /**
     * 根据主键删除记录
     */
    int deleteByPrimaryKey(String fieldId);

    /**
     * 保存记录,不管记录里面的属性是否为空
     */
    int insert(BaseFields record);

    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BaseFields record);

    /**
     * 根据条件查询记录集
     */
    List<BaseFields> selectByExample(Criteria example);

    /**
     * 根据主键查询记录
     */
    BaseFields selectByPrimaryKey(String fieldId);

    /**
     * 根据条件更新属性不为空的记录
     */
    int updateByExampleSelective(@Param("record") BaseFields record,
                                 @Param("condition") Map<String, Object> condition);

    /**
     * 根据条件更新记录
     */
    int updateByExample(@Param("record") BaseFields record,
                        @Param("condition") Map<String, Object> condition);

    /**
     * 根据主键更新属性不为空的记录
     */
    int updateByPrimaryKeySelective(BaseFields record);

    /**
     * 根据主键更新记录
     */
    int updateByPrimaryKey(BaseFields record);
}
