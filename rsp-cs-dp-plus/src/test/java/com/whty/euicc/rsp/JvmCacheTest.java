package com.whty.euicc.rsp;

import org.junit.Test;

import com.whty.euicc.rsp.cache.JvmCacheUtil;

public class JvmCacheTest {
	JvmCacheUtil cacheUtil = new JvmCacheUtil();

	@Test
	public void testCache() {
		try {
			cacheUtil.put("a", "aaa", 3 * 1000);
			boolean flag = true;
			int count = 0, timeout = 5;
			while (flag) {
				count++;
				System.out.print(count + ":" + cacheUtil.getString("a") + "\t");
				System.out.println();

				Thread.sleep(1000);

				if (count >= timeout) {
					flag = false;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
