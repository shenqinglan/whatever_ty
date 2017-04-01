package com.whty.euicc.common.utils;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExt(String fileName) {
		if (null == fileName) {
			return null;
		}

		int index = fileName.lastIndexOf(".");
		if (-1 == index) {
			return null;
		} else {
			return fileName.substring(index + 1);
		}
	}

	/**
	 * 文件大小控制
	 * @throws java.io.IOException
	 */
	public static boolean chkFileSize(MultipartFile file) throws IOException {
		long maxFileSize = 2 * 1024 * 1024;
		if(file.getInputStream().available() < maxFileSize){
			return false;
		}
		return true;
	}

}
