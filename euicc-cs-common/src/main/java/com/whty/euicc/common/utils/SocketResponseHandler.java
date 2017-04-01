package com.whty.euicc.common.utils;

import java.io.IOException;
import java.io.InputStream;
/**
 * socket返回接口
 * @author Administrator
 *
 */
public interface SocketResponseHandler {
	void read(InputStream in) throws IOException;
}