package com.whty.efs.common.util;

import java.io.StringReader;

import com.google.gson.stream.JsonReader;

public class AbstractConvertor {
	/**
	 * 构建Gson reader
	 * @param decodeStr
	 * @return
	 */
	public static JsonReader buildJsonReader(String decodeStr){
		JsonReader reader = new JsonReader(new StringReader(decodeStr));
		reader.setLenient(true);
		return reader;
	}
}
