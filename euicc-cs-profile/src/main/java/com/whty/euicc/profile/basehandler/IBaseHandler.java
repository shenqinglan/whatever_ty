package com.whty.euicc.profile.basehandler;

import java.io.BufferedReader;
/**
 * 借口
 * @author Administrator
 *
 */
public interface IBaseHandler {
	String handler(BufferedReader br) throws Exception;
}
