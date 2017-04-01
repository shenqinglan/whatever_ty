package com.whty.euicc.sr.tls;


import org.junit.Test;

import com.whty.euicc.tls.client.HttpsShakeHandsClient;
/**
 * 卡片接入模拟测试
 * @author Administrator
 *
 */
public class SrTest {
	@Test
	public void createISDP()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void createISDP_nginx_116()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr_116(eid,1);
	}
	
	@Test
	public void createISDP_gz()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr_gz(eid,1);
	}
	
	@Test
	public void createISDP_116()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr_116(eid,1);
	}
	
	
	
	@Test
	public void installProfile()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void downloadProfile()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,34);
	}
	
	
	@Test
	public void firstPersonalTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,2);
	}
	
	@Test
	public void secondPersonalTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void allPersonalTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,3);
	}
	
	@Test
	public void allPersonal_116()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr_116(eid,3);
	}
	
	@Test
	public void enableProfileByHttpsTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void disableProfileByHttpsTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void deleteProfileByHttpsTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	@Test
	public void masterDeleteByHttpsTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	@Test
	public void getStatusByHttpsTest()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void scp03Count()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void updateConnectionParameter()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,3);
	}
	@Test
	public void setFallBackAttr()throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,3);
	}

	@Test
	public void updateSrAddressParaByHttpsTest() throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void srChangeAuthenticateSMSRTest() throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void srChangeCreateAdditionalKeySetTest() throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}
	
	@Test
	public void srChangeFinaliseISDRhandoverTest() throws Exception{
		String eid = "89001012012341234012345678901224";
        new HttpsShakeHandsClient().callSr(eid,1);
	}

	
}
