// Copyright (C) 2012 WHTY
package com.whty.euicc.base.pojo;

import java.io.Serializable;

/**
 * 用户角色表
 */
public class BaseUserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户角色ID
     */
    //private String userRoleId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 角色ID
     */
    private String roleId;

//    /**
//     * @return 用户角色ID
//     */
//    public String getUserRoleId() {
//        return userRoleId;
//    }
//
//    /**
//     * @param userRoleId
//     *            用户角色ID
//     */
//    public void setUserRoleId(String userRoleId) {
//        this.userRoleId = userRoleId;
//    }

    /**
     * @return 用户ID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            用户ID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return 角色ID
     */
    public String getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     *            角色ID
     */
    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
