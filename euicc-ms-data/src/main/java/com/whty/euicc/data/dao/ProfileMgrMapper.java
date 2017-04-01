package com.whty.euicc.data.dao;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.whty.euicc.data.pojo.ProfileMgr;
import com.whty.euicc.data.pojo.ProfileMgrExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ProfileMgrMapper {
    int countByExample(ProfileMgrExample example);

    int deleteByExample(ProfileMgrExample example);

    int deleteByPrimaryKey(String eid);

    int insert(ProfileMgr record);

    int insertSelective(ProfileMgr record);
    
    PageList<ProfileMgr> selectByExample(ProfileMgrExample example,PageBounds pageBounds);

    List<ProfileMgr> selectByExample(ProfileMgrExample example);

    ProfileMgr selectByPrimaryKey(String eid);

    int updateByExampleSelective(@Param("record") ProfileMgr record, @Param("example") ProfileMgrExample example);

    int updateByExample(@Param("record") ProfileMgr record, @Param("example") ProfileMgrExample example);

    int updateByPrimaryKeySelective(ProfileMgr record);

    int updateByPrimaryKey(ProfileMgr record);
}