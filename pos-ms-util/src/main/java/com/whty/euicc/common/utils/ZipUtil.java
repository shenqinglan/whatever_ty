package com.whty.euicc.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipUtil {

	/**
	 * 查找zip包中，指定实体数据<br>
	 * 
	 * 
	 * @param stream
	 *            传入的zip包流对象。方法内未关闭流对象，需要显性关闭。
	 * @param name
	 *            实体名称
	 * @return
	 * @throws java.io.IOException
	 */
	public static byte[] readEntry(InputStream stream, String name) throws IOException {
		if (null == name) {
			return new byte[0];
		}

		ZipInputStream zipStream = new ZipInputStream(stream);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ZipEntry zipEntry = null;

		while ((zipEntry = zipStream.getNextEntry()) != null) {
			if (zipEntry.getName().equals(name)) {
				byte[] b = new byte[1024];
				int len = -1;
				while ((len = zipStream.read(b)) != -1) {
					baos.write(b, 0, len);
				}
				// 找到指定实体，退出循环
				break;
			}
		}

		return baos.toByteArray();
	}
}
