package com.whty.euicc.rsp;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.whty.euicc.rsp.util.RandomUtil;

public class StringUtilsTest {
	@Test
	public void stringTest() {
		String iccid = "a";
		System.out.println(StringUtils.defaultIfBlank(iccid, "1"));
	}

	@Test
	public void uuidTest() {
		int i = 0;
		while (i < 10) {
			System.out.println(UUID.randomUUID().toString());
			i += 1;
		}

	}

	@Test
	public void randomTest() {
		System.out.println(RandomUtil.genarateMatchingId());
	}


}
