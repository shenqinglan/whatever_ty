package com.whty.efs.data.controller;

import java.io.StringReader;

import com.google.gson.stream.JsonReader;

public abstract class BaseController {
	/**
	 * 构建Gson reader
	 * @param decodeStr
	 * @return
	 */
	protected JsonReader buildJsonReader(String decodeStr){
		JsonReader reader = new JsonReader(new StringReader(decodeStr));
		reader.setLenient(true);
		return reader;
	}
}
