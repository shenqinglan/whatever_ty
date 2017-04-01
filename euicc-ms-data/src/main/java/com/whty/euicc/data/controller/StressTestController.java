package com.whty.euicc.data.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;
import com.whty.euicc.common.base.BaseController;
import com.whty.euicc.common.utils.UUIDUtil;
import com.whty.euicc.data.pojo.CardInfo;
import com.whty.euicc.data.pojo.EISInfo;
import com.whty.euicc.data.pojo.EcasdInfo;
import com.whty.euicc.data.pojo.EuiccProfile;
import com.whty.euicc.data.pojo.IsdPInfo;
import com.whty.euicc.data.pojo.IsdRInfo;
import com.whty.euicc.data.pojo.ProfileInfo;
import com.whty.euicc.data.pojo.ProfileInfo.Profile;
import com.whty.euicc.data.pojo.Scp03Info;
import com.whty.euicc.data.pojo.Scp80Info;
import com.whty.euicc.data.pojo.Scp81Info;
import com.whty.euicc.data.service.EuiccCardService;
import com.whty.euicc.data.service.EuiccProfileService;


/**
 * @author liuwei
 * @date 2016-09-12 13:36
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/stressTest")
public class StressTestController extends BaseController{

	@Autowired
	EuiccCardService euiccCardService;
	
	@Autowired
	EuiccProfileService profileService;
	
	@RequestMapping(value = "/insertEuiccCard", method = RequestMethod.POST)
	public void insertEuiccCard(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        String reqBody = sb.toString();
        Gson gson = new Gson();
        EISInfo info = gson.fromJson(reqBody, EISInfo.class);
		insertCardInfo(info);
			
	}

	@Transactional(propagation= Propagation.REQUIRED,rollbackFor = Exception.class)
	private void insertCardInfo(EISInfo info) {
		//更新卡基本数据
		EISInfo.CardInfo cardInfo = info.EIS.get(0).CARD.get(0);
		String eid = cardInfo.EID;
		euiccCardService.deleteScp03InfoByEid(eid);
		euiccCardService.deleteScp81InfoByEid(eid);
		euiccCardService.deleteScp80InfoByEid(eid);
		euiccCardService.deleteIsdPInfoByEid(eid);
		euiccCardService.deleteIsdRInfoByEid(eid);
		euiccCardService.deleteCardInfoByPrimaryKey(eid);
		euiccCardService.deleteEcasdInfoByPrimaryKey(cardInfo.ECASD_ID);
		
		euiccCardService.insertEuiccCardSelective(getCardInfo(cardInfo));
		euiccCardService.insertIsdRSelective(getIsdRInfo(info.EIS.get(2).ISD_R.get(0)));
		euiccCardService.insertEcasdSelective(getEcasdInfo(info.EIS.get(3).ECASD.get(0)));

		
		//更新Scp80
		for(EISInfo.Scp80Info scp80 : info.EIS.get(4).SCP80) {
			
			euiccCardService.insertScp80Selective(getScp80Info(scp80));
		}
		
		//更新Scp81
		for(EISInfo.Scp81Info scp81 : info.EIS.get(5).SCP81) {
			euiccCardService.insertScp81Selective(getScp81Info(scp81));
		}
		
		//更新Scp03
		for(EISInfo.Scp03Info scp03 : info.EIS.get(6).SCP03) {
			euiccCardService.insertScp03Selective(getScp03Info(scp03));
		}
		
		//更新isd-p
		for(EISInfo.IsdPListInfo isdPListInfo : info.EIS.get(1).ISD_P_LIST){
			euiccCardService.insertIsdPSelective(getIsdPInfo(isdPListInfo.ISD_P.get(0)));
		}
	}
	
	private CardInfo getCardInfo(EISInfo.CardInfo info) {
		CardInfo cardInfo = new CardInfo();
		cardInfo.setEid(info.EID);
		cardInfo.setEumId(info.EUM_ID);
		cardInfo.setProductiondate(info.PRODUCTION_DATE);
		cardInfo.setPlatformtype(info.PLATFORM_VERSION);
		cardInfo.setPlatformversion(info.PLATFORM_VERSION);
		cardInfo.setRemainingmemory(Integer.parseInt(info.REMAINING_MEMORY));
		cardInfo.setAvailablememoryforprofiles(Integer.parseInt(info.AVAILABLEMEMORYFORPROFILES));
		cardInfo.setSmsrId(info.SMSR_ID);
		cardInfo.setEcasdId(StringUtils.defaultIfEmpty(info.ECASD_ID,UUIDUtil.getUuidString()));
		return cardInfo;
	}
	
	private IsdRInfo getIsdRInfo(EISInfo.IsdRInfo info) {
		IsdRInfo isdRInfo = new IsdRInfo();
		isdRInfo.setrId(info.R_ID);
		isdRInfo.setIsdRAid(info.ISD_R_AID);
		isdRInfo.setEid(info.EID);
		isdRInfo.setPol1Id(info.POL1_ID);
		return isdRInfo;
	}
	
	private EcasdInfo getEcasdInfo(EISInfo.EcasdInfo info) {
		EcasdInfo ecasdInfo = new EcasdInfo();
		ecasdInfo.setEcasdId(info.ECASD_ID);
		ecasdInfo.setCertEcasdEcka(info.CERT_ECASD_ECKA);
		return ecasdInfo;
	}
	
	private Scp80Info getScp80Info(EISInfo.Scp80Info info) {
		Scp80Info scp80Info = new Scp80Info();
		scp80Info.setScp80Id(info.SCP80_ID);
		scp80Info.setEid(info.EID);
		scp80Info.setId(info.ID);
		scp80Info.setVersion(info.VERSION);
		scp80Info.setData(info.DATA);
		return scp80Info;
	}
	
	private Scp81Info getScp81Info(EISInfo.Scp81Info info) {
		Scp81Info scp81Info = new Scp81Info();
		scp81Info.setScp81Id(info.SCP81_ID);
		scp81Info.setEid(info.EID);
		scp81Info.setId(info.ID);
		scp81Info.setVersion(info.VERSION);
		scp81Info.setData(info.DATA);
		return scp81Info;
	}
	
	private Scp03Info getScp03Info(EISInfo.Scp03Info info) {
		Scp03Info scp03Info = new Scp03Info();
		scp03Info.setScp03Id(info.SCP03_ID);
		scp03Info.setEid(info.EID);
		scp03Info.setIsdPAid(info.ISD_P_AID);
		scp03Info.setId(info.ID);
		scp03Info.setVersion(info.VERSION);
		scp03Info.setData(info.DATA);
		return scp03Info;
	}
	
	private IsdPInfo getIsdPInfo(EISInfo.IsdPInfo info) {
		try {
			IsdPInfo isdPInfo = new IsdPInfo();
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
			isdPInfo.setpId(info.P_ID);
			isdPInfo.setEid(info.EID);
			isdPInfo.setIsdPState(info.ISD_P_STATE);
			isdPInfo.setCreateDt(df.parse(info.CDREATE_DT));
			isdPInfo.setUpdateDt(df.parse(info.UPDATE_DT));
			isdPInfo.setIsdPLoadfileAid(info.ISD_P_LOADFILE_AID);
			isdPInfo.setIsdPModuleAid(info.ISD_P_MODULE_AID);
			isdPInfo.setConnectivityParam(info.CONNECTIVITY_PARAM);
			isdPInfo.setIsdPAid(info.ISD_P_AID);
			return isdPInfo;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/insertProfile", method = RequestMethod.POST)
	public void insertProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String line = null;
        StringBuilder sb = new StringBuilder();
        while((line = br.readLine())!=null){
            sb.append(line);
        }
        String reqBody = sb.toString();
        Gson gson = new Gson();
        ProfileInfo info = gson.fromJson(reqBody, ProfileInfo.class);
        for (ProfileInfo.ProfileList profileList : info.PROFILE_LIST) {
        	Profile profile = profileList.PROFILE.get(0);
        	profileService.deleteByPrimaryKey(profile.ICCID);
        	profileService.insertProfileSelective(getProfileInfo(profile));
        }	
	}
	
	private EuiccProfile getProfileInfo(ProfileInfo.Profile profile) {
		EuiccProfile euiccProfile = new EuiccProfile();
		euiccProfile.setIccid(profile.ICCID);
		euiccProfile.setEid(profile.EID);
		euiccProfile.setIsdPAid(profile.EID);
		euiccProfile.setMnoId(profile.MNO_ID);
		euiccProfile.setFallbackAttribute(profile.FALLBACK_ATTRIBUTE);
		euiccProfile.setImsi(profile.IMSI);
		euiccProfile.setMsisdn(profile.MSISDN);
		euiccProfile.setState(profile.STATE);
		euiccProfile.setSmdpId(profile.SMDP_ID);
		euiccProfile.setProfileType(profile.PROFILE_TYPE);
		euiccProfile.setPol2Id(profile.POL2_ID);
		return euiccProfile;
	}
}
