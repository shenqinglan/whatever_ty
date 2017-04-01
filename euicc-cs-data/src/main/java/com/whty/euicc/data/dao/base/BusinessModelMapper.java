// Copyright (C) 2012 WHTY
package com.whty.euicc.data.dao.base;

import java.util.List ;

import com.whty.euicc.data.dto.Criteria;
import com.whty.euicc.data.pojo.BusinessModel;

public interface BusinessModelMapper {


    /**
     * 根据条件查询记录集
     */
    List<BusinessModel> selectByExample(Criteria example);


}
