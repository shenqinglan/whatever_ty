package com.whty.euicc.sr.util;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.whty.euicc.profile.util.Tool;


public class HexTest {
	 String beginCounter="0010";
	 String endCounter="FFFF";
	

	
	@Test
	public void substr(){
		String aid = "A0000005591010FFFFFFFF8900001203";
		System.out.println(StringUtils.substring(aid, aid.length()-6));
	}
	
	@Test
	public void isdP(){
		String apdu = "80E60C003F10A0000005591010FFFFFFFF8900000D0010A0000005591010FFFFFFFF8900000E0010A0000005591010FFFFFFFF89000013000380c00006C9048102037000";
		String apdu1 = "10A0000005591010FFFFFFFF8900000D0010A0000005591010FFFFFFFF8900000E0010A0000005591010FFFFFFFF89000013000380c00006C9048102037000";
		System.out.println(apdu1.length());
	}
	
	@Test
	public void add(){
		System.out.println(hexAdd("0010"));
	}
	
	private  String hexAdd(String instanceAid){
		String nowCounter;
		int now = Integer.parseInt(instanceAid, 16)+1;
		int end = Integer.parseInt(endCounter, 16);
		if(now > end){
			nowCounter = beginCounter;
		}else{
			String hex = Integer.toHexString(now); 
			String encCounter="000000"+hex;//加密计数器增加
			nowCounter = encCounter.substring((encCounter.length()-4), encCounter.length()).toUpperCase();
		}
		return nowCounter;
	}
}
