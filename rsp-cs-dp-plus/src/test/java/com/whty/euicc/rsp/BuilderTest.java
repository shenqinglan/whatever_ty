package com.whty.euicc.rsp;

import org.junit.Test;

import com.whty.euicc.rsp.cache.CacheBean;

public class BuilderTest {
	@Test
	public void buildCacheBeanTest() {
		CacheBean comp = new CacheBean.CacheBeanBuilder("100").setMatchingId("234").setConfirmationCode("123").build();
		System.out.println(comp);
	}
}
