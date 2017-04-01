package com.whty.tool.util;

public interface Constant {
	String MF_PREFIX = "00E0";
	String EF_PREFIX = MF_PREFIX;
	String FILE_CONTENT_PREFIX = "00DC";
	String PIN_PREFIX = "A0D80000";
	String PUK_PREFIX = "A0D80000";
	String FILE_PADDING_PREFIX = "A0EE";
	String EILE_WITH00D6_PREFIX = "00D6";
	//不需要解析的
	String COMPARE_PREFIX = "COMPTXTA";
	String CMD0044_PREFIX = "00440000";
	String SEMI_PREFIX =";";
	String ANNOTA_PREFIX = "//";
	String[]UNnecessary_PREFIX = {COMPARE_PREFIX,CMD0044_PREFIX,SEMI_PREFIX,ANNOTA_PREFIX,"/*","*/"};
}
