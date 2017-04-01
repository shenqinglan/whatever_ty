// Copyright (C) 2012 WHTY
package com.whty.euicc.common.utils;

/**
 * 检查是否为空类
 * @author Administrator
 *
 */
public class CheckEmpty {
    public static boolean isEmpty(String check) {
        if (check == null || check.length() == 0) {
            return true;
        }

            return false;

    }

    public static boolean isNotEmpty(String check) {
        if (check != null && check.length() > 0) {
            return true;
        }

            return false;

    }

}
