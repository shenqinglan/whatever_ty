package com.whty.euicc.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 对象学序列化、反序列化工具类
 * @author dengjun
 * @since 2014-9-25
 */
public class ObjectTranscoder {

    private static final Logger logger = LoggerFactory
            .getLogger(ObjectTranscoder.class);

	public static byte[] serialize(Object value) {
		if (value == null) {
			throw new NullPointerException("序列化对象为空");
		}
		byte[] rv = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream os = null;
		try {
			bos = new ByteArrayOutputStream();
			os = new ObjectOutputStream(bos);
			os.writeObject(value);
			os.close();
			bos.close();
			rv = bos.toByteArray();
		} catch (IOException e) {
			throw new IllegalArgumentException("序列化失败", e);
		} finally {
			close(os);
			close(bos);
		}
		return rv;
	}

	public static Object deserialize(byte[] in) {
		Object rv = null;
		ByteArrayInputStream bis = null;
		ObjectInputStream is = null;
		try {
			if (in != null) {
				bis = new ByteArrayInputStream(in);
				is = new ObjectInputStream(bis);
				rv = is.readObject();
				is.close();
				bis.close();
			}
		} catch (Exception e) {
			logger.error("反序列化失败", e);
		} finally {
			close(is);
			close(bis);
		}
		return rv;
	}

	public static void close(Closeable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			} catch (Exception e) {
				logger.error("无法关闭流", e);
			}
		}
	}
}
