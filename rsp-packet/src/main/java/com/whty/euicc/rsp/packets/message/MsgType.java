package com.whty.euicc.rsp.packets.message;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 消息类型注解<br>
 * 
 * 
 * @author baojw@whty.com.cn
 * @date 2014年9月26日 下午1:26:14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MsgType {
	String value();
}
