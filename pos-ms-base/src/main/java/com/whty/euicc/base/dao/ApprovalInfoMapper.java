package com.whty.euicc.base.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.base.pojo.ApprovalInfo;
import com.whty.euicc.base.pojo.ApprovalInfoExample;
import com.whty.euicc.base.pojo.ApprovalUserInfo;

public interface ApprovalInfoMapper {
    int countByExample(ApprovalInfoExample example);

    int deleteByExample(ApprovalInfoExample example);

    int deleteByPrimaryKey(String approvalUserId);

    int insert(ApprovalInfo record);

    int insertSelective(ApprovalInfo record);

    List<ApprovalInfo> selectByExample(ApprovalInfoExample example);
    
    PageList<ApprovalUserInfo> selectByExample(ApprovalInfoExample example,PageBounds pageBounds);

    ApprovalInfo selectByPrimaryKey(String approvalUserId);
    
    ApprovalUserInfo selectUserInfoByPrimaryKey(String approvalUserId);

    int updateByExampleSelective(@Param("record") ApprovalInfo record, @Param("example") ApprovalInfoExample example);

    int updateByExample(@Param("record") ApprovalInfo record, @Param("example") ApprovalInfoExample example);

    int updateByPrimaryKeySelective(ApprovalInfo record);

    int updateByPrimaryKey(ApprovalInfo record);
}