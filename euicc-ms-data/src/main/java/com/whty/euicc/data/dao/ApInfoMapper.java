package com.whty.euicc.data.dao;

import com.whty.euicc.data.pojo.ApInfo;

public interface ApInfoMapper {
    int deleteByPrimaryKey(String userId);

    int insert(ApInfo record);

    int insertSelective(ApInfo record);

    ApInfo selectByPrimaryKey(String userId);

    int updateByPrimaryKeySelective(ApInfo record);

    int updateByPrimaryKey(ApInfo record);
}