// Copyright (C) 2012 WHTY
package com.whty.euicc.data.dao.base;

import java.util.List ;

import com.whty.euicc.data.dto.Criteria;
import com.whty.euicc.data.pojo.BusinessLog;

public interface BusinessLogMapper {


    /**
     * 保存属性不为空的记录
     */
    int insertSelective(BusinessLog record);
    
    /**
     * 根据条件查询记录集
     */
    List<BusinessLog> selectByExample(Criteria example);

}
