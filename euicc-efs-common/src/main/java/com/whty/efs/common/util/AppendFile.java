package com.whty.efs.common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuy
 * @version 2014-9-29 下午7:49:41
 */
public class AppendFile {
	private static Logger logger = LoggerFactory.getLogger(AppendFile.class);
	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param FrontSerialNo
	 * @param content
	 */
	@SuppressWarnings("resource")
	public static void writeFileByLine(String fileName, String FrontSerialNo, String content) {
		FileWriter writer = null;
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			writer = new FileWriter(fileName, true);
			BufferedReader reader = null;
			String tempString = null;
			StringBuffer sb = new StringBuffer();
			reader = new BufferedReader(new FileReader(fileName));
//			sb.append("\r\n");
			// 将写文件指针移到文件尾。
			while ((tempString = reader.readLine()) == null || !tempString.contains("**")) {
				sb.append("******流水号【" + FrontSerialNo + "】" + content);
				sb.append(System.getProperty( "line.separator" ));
				break;
			}
			writer.write(sb.toString());

		} catch (IOException e) {
			logger.error("", e);
		} finally {
			try {
				if (writer != null) {
					writer.close();
				}
			} catch (IOException e) {
				logger.error("", e);
			}
		}
	}

}
