package com.whty.efs.packets.constant;

import java.nio.charset.Charset;

import com.google.gson.Gson;

/**
 * 报文常量
 * @author dengzm
 *
 */
public interface Constant {
    /** 字符集 */
    public static Charset UTF8 = Charset.forName("UTF-8");
    /** GSON */
    public static final Gson gson = new Gson();
    
    /**
     * 银数
     */
    public static final String YINSHU = "uniondata";
    
    /**
     * 查询接口
     */

    public static final String TATH02100201 = "tath.021.002.01";

    
    /**
     * 个人化接口
     */
    public static final String TATH00800101 = "tath.008.001.01";
    public static final String TATH00800201 = "tath.008.002.01";
    /**
     * 应用申请接口
     */
    public static final String TATH00900101 = "tath.009.001.01";
    
    /**
     * 发送短信
     */
    public static final String TATH10000101="tath.100.001.01";
    /**
     * 验证短信
     */
    public static final String TATH10100101="tath.101.001.01";
    
    /**
     * 应用下载
     */
    public static final String TATH00500201="tath.005.002.01";
    
    /**
     * 应用删除
     */
    public static final String TATH01000201="tath.010.002.01";
    
    /**
     * 获取Token
     */
    public static final String TATH00600201="tath.006.002.01";
    
    /**
     * 应用下载结果通知
     */
    public static final String TATH00700201="tath.007.002.01";
    /**
     * 应用删除结果通知
     */
    public static final String TATH01100201="tath.011.002.01";
    
    /**
     * 应用申请
     */
    public static final String TATH11200201="tath.112.002.01";
 
}
