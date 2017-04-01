package com.whty.efs.data.dao;

import com.whty.efs.data.pojo.EuiccCard;
import com.whty.efs.data.pojo.EuiccCardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EuiccCardMapper {
    int countByExample(EuiccCardExample example);

    int deleteByExample(EuiccCardExample example);

    int deleteByPrimaryKey(String eid);

    int insert(EuiccCard record);

    int insertSelective(EuiccCard record);

    List<EuiccCard> selectByExample(EuiccCardExample example);

    EuiccCard selectByPrimaryKey(String eid);

    int updateByExampleSelective(@Param("record") EuiccCard record, @Param("example") EuiccCardExample example);

    int updateByExample(@Param("record") EuiccCard record, @Param("example") EuiccCardExample example);

    int updateByPrimaryKeySelective(EuiccCard record);

    int updateByPrimaryKey(EuiccCard record);
    
    /**
     * @author Administrator
     * @date 2016-11-2
     * @param eid
     * @return
     * @Description TODO(手机端查询卡信息dao接口)
     */
    List<EuiccCard> findEuiccCardInfo(@Param(value="eid") String eid);
}