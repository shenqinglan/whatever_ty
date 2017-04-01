package com.whty.euicc.rsp.profile;

import static com.whty.euicc.common.utils.StringUtils.appends;
import static com.whty.euicc.common.apdu.ToTLV.toTLV;
import static com.whty.euicc.common.apdu.ToTLV.toTLVExtend;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.whty.euicc.common.exception.EuiccBusiException;
import com.whty.euicc.common.spring.SpringPropertyPlaceholderConfigurer;
import com.whty.euicc.rsp.constant.EccProperties;
import com.whty.euicc.rsp.util.RandomUtil;
import com.whty.euicc.rsp.util.ToTLV;
import com.whty.rsp.data.pojo.RspProfileWithBLOBs;
import com.whty.rsp.data.service.RspProfileService;
import com.whty.security.ecc.ECCUtils;
import com.whty.security.scp03t.dataencryption.CreateC_MAC;
import com.whty.security.scp03t.dataencryption.EncData;
import com.whty.security.scp03t.scp03t.bean.CmdApduBean;
import com.whty.security.scp03t.scp03t.counter.Scp03Counter;

/**
 * 生成Bound Profile Package
 * 
 * @author Administrator
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Service
public class ProfileUtil {
	private static Logger logger = LoggerFactory.getLogger(ProfileUtil.class);
	//安全级别（Security Level）
	//Mutual authentication
	private final static String MUTUAL_AUTHENTICATION = "33";
	//Integrity and data origin authentication
	private final static String DATA_INTEGRITY = "01";
	//Confidentiality
	private final static String CONFIDENTIALITY = "02";
	//签名数据
	private static String P = EccProperties.P;
	private static String A = EccProperties.A;
	private static String B = EccProperties.B;
	private static String Gx = EccProperties.Gx;   // 待确定
	private static String Gy = EccProperties.Gy;   // 待确定
	private static String N = EccProperties.N;
	private static String H = EccProperties.H;
	private static String SK_DPpb_ECDSA = EccProperties.SK_DPpb_ECDSA;
	
	@Autowired
    private RspProfileService rspProfileService;
	
	/**
	 * 获取 UPP
	 * 分包操作
	 * @param UPP 
	 * @param length
	 * @return 
	 */
	public String[] getUPP(String upp ,int length) {
		//暂时先注释
		/*if((length < 2)&&(length > 1982)){
			throw new EuiccBusiException("8010","The length of PPP segment is out of specified size(1~991 bytes)");
		}*/
		int len = upp.length();//profile长度
		int n = len / length;//分包个数
		boolean mod = (len % length != 0);//取余
		String data[] = mod ? new String[n+1] : new String[n];
		//取前面完整的包
		if (n != 0) {
			for (int i = 0; i < n; i++) {
				data[i] = upp.substring(i * length, (i + 1) * length);
			}
		}
		//如果只有一个包(n=0) || 取最后一个包
		if (mod) {
			data[n] = upp.substring(length * n, len);
		}
		logger.info("数据分包个数为:{}", data.length);
		return data;
	}
	
	/**
	 * 生成PPP
	 * @param upp
	 * @param initialMacChaining
	 * @param enc
	 * @param mac
	 * @return
	 */
	private String generatePPP(String upp ,String initialMacChaining ,String enc ,String mac){
		//Profile分包长度
		String proflieSegLen = SpringPropertyPlaceholderConfigurer.getStringProperty("proflie_seg_len");
		//加密的UPP不应为空
		if((upp == null) | (upp.equals(""))){
			throw new EuiccBusiException("8010","UPP is empty");
		}
		//对UPP进行分包
		String data[] = getUPP(upp ,Integer.parseInt(proflieSegLen)*2);
		String encCounter = "000000";//SCP03通道加密计数器初始值
		String perMac = initialMacChaining;
		StringBuilder ppp = new StringBuilder();
		//分别加密并拼装
		CmdApduBean encryptionDate = null;
		for(int i=0,num = data.length;i<num;i++){
			encryptionDate = encryptionProfile("86",data[i], enc, mac, perMac, encCounter, MUTUAL_AUTHENTICATION);
			ppp.append(encryptionDate.getApdu());
			perMac = encryptionDate.getPerMac();
			encCounter = encryptionDate.getCounter();
			System.out.println("------------------------------第" + (i + 1) + "包PPP-----------------------------");
			logger.info("PPP segment:{}", encryptionDate.getApdu());
			logger.info("PPP perMac:{}", perMac);
			logger.info("PPP encCounter:{}", encCounter);
		}
		
		return ppp.toString();
	}
	
	/**
	 * 生成BPP
	 * @param iccid
	 * @param UPP
	 * @param initialMacChaining
	 * @param S_ENC
	 * @param S_MAC
	 * @param transactionId
	 * @param euiccOtpk
	 * @param smdpOtpk
	 * @param isRandomKeyMode
	 * @return
	 */
	 
	public String generateBPP(String iccid,String UPP ,String initialMacChaining ,String S_ENC ,String S_MAC, String transactionId, String euiccOtpk, String smdpOtpk, boolean isRandomKeyMode){
		//MetaData分包长度
		String metaDataSegLen = SpringPropertyPlaceholderConfigurer.getStringProperty("meta_data_seg_len");
		CmdApduBean encryptionDate = null;
		String encCounter = "000000";
		logger.info("encCounter:{}", encCounter);
		logger.info("initialMacChaining:{}", initialMacChaining);
		
		//1.拼接initialiseSecureChannel
		String initialiseSecureChannel = initialiseSecureChannel(iccid, transactionId,euiccOtpk, smdpOtpk);
		logger.info("******************************1*********************************");
		logger.info("initialiseSecureChannel:{} ", initialiseSecureChannel);
		
		//2.拼接configIsdP并加密
		logger.info("******************************2*********************************");
		String configIsdP = configureIsdp(iccid);
		encryptionDate =encryptionProfile("87",configIsdP, S_ENC, S_MAC, initialMacChaining, encCounter, MUTUAL_AUTHENTICATION);
		configIsdP =  encryptionDate.getApdu();
		initialMacChaining = encryptionDate.getPerMac();
		encCounter = encryptionDate.getCounter();
		String configureISDP = toTLV("A0",configIsdP);
		logger.info("configureISDP:{}", configureISDP);
		logger.info("initialMacChaining:{}", initialMacChaining);
		logger.info("encCounter:{}", encCounter);
		
		//3.拼接storeMetadata并计算mac
		logger.info("******************************3*********************************");
		//3.1 对storeMetadata 进行分包处理
		String metaDatas[] = getUPP(storeMetadata(iccid) ,Integer.parseInt(metaDataSegLen)*2);
		int num = metaDatas.length;
		//3.2 对分包后的storeMetadata进行mac计算
		StringBuilder storeMetadataTemp = new StringBuilder();
		for(int i=0;i<num;i++){
			System.out.println("第" + (i+1) + "包" +"storeMetadata原始报文： " + metaDatas[i]);
			encryptionDate = encryptionProfile("88",metaDatas[i], S_ENC, S_MAC, initialMacChaining, encCounter, DATA_INTEGRITY);
			System.out.println("第" + (i+1) + "包" +"storeMetadata进行mac计算后报文： " + encryptionDate.getApdu());
			initialMacChaining = encryptionDate.getPerMac();
			encCounter = encryptionDate.getCounter();
			System.out.println("initialMacChaining: " + initialMacChaining);
			System.out.println("encCounter: " + encCounter);
			storeMetadataTemp.append(encryptionDate.getApdu());
		}
		String storeMetadata = toTLV("A1",storeMetadataTemp.toString());
		logger.info("storeMetadata:{}", storeMetadata);
		logger.info("initialMacChaining:{}", initialMacChaining);
		logger.info("encCounter:{}", encCounter);
		
		//4.拼接ppkKeys并加密
		logger.info("******************************4*********************************");
		String PPP = null;
		String data =null;
		if(isRandomKeyMode){
			String ppkKeys = replaceSessionKeys();
			String ppkKeys2 = ppkKeys;
			encryptionDate =encryptionProfile("87",ppkKeys, S_ENC, S_MAC, initialMacChaining, encCounter , MUTUAL_AUTHENTICATION);
			ppkKeys = encryptionDate.getApdu();
			String replaceSessionKeys = toTLV("A2",ppkKeys);
			logger.info("replaceSessionKeys加密数据:{}", replaceSessionKeys);
			logger.info("replaceSessionKeys原始数据:{}", ppkKeys2);
			//解出PPK密钥
			ppkKeys2 = ppkKeys2 + "FFFF";
			ppkKeys2 = ToTLV.selectTlv(ppkKeys2, "BF26", "FFFF").getValue() + "FFFF";
			//解出initialMacChainingPPK
			String initialMacChainingPPK = ToTLV.selectTlv(ppkKeys2, "80", "81").getValue();
			int ppkLen = toTLV("80", initialMacChainingPPK).length();
			ppkKeys2 = ppkKeys2.substring(ppkLen);
			logger.info("initialMacChainingPPK:{}", initialMacChainingPPK.toUpperCase());
			//解出ppkEnc
			String ppkEnc = ToTLV.selectTlv(ppkKeys2, "81", "82").getValue();
			ppkLen = toTLV("81", ppkEnc).length();
			ppkKeys2 = ppkKeys2.substring(ppkLen);
			logger.info("ppkEnc:{}", ppkEnc.toUpperCase());
			//解出ppkCmac
			String ppkCmac = ToTLV.selectTlv(ppkKeys2, "82", "FFFF").getValue();
			logger.info("ppkCmac:{}", ppkCmac.toUpperCase());
			logger.info("******************************5*********************************");
			
			//5.拼接PPP
			//生成PPP
			PPP = generatePPP(UPP, initialMacChainingPPK, ppkEnc, ppkCmac);
			String getPPP = toTLV("A3",PPP);
			logger.info("PPP:{}", getPPP);
			
			//6.拼接BPP
			logger.info("******************************6*********************************");
			data = appends(initialiseSecureChannel,configureISDP,storeMetadata,replaceSessionKeys,getPPP);
		}else{
			//5.拼接PPP
			PPP = generatePPP(UPP, initialMacChaining, S_ENC, S_MAC);
			String getPPP = toTLV("A3",PPP);
			logger.info("PPP:{}", getPPP);
			
			//6.拼接BPP
			logger.info("******************************5*********************************");
			data = appends(initialiseSecureChannel,configureISDP,storeMetadata,getPPP);
		}
		return toTLV("BF36",data);
	}
	
	/**
	 * 生成initialiseSecureChannel
	 * @param iccid
	 * @param transactionId
	 * @param euiccOtpk
	 * @param smdpOtpk
	 * @return
	 */
	public String initialiseSecureChannel(String iccid, String transactionId, String euiccOtpk, String smdpOtpk){
		String hostId = SpringPropertyPlaceholderConfigurer.getStringProperty("host_id");
		//拼接各部分报文
		String remoteOpId2 = appends("82","01","01");
		String transactionId2 = toTLV("80", transactionId);
		//拼接controlRefTemplate序列
		String keyType2 = appends("80","01","88");
		String keyLen2 = appends("81","01","10");
		String hostId2 = toTLV("84", hostId);
		String controlRefTemplate = appends(keyType2,keyLen2,hostId2);
		controlRefTemplate = toTLV("A6", controlRefTemplate);
		//smdpOtpk = ToTLV.toTLV("5F49", "04" + smdpOtpk);
		String data_sign = appends(remoteOpId2,transactionId2,controlRefTemplate,smdpOtpk,euiccOtpk);
		String smdpSign = ToTLV.toTLV("5F37", ECCUtils.eccECKASign(P, A, B, Gx, Gy, N, H, data_sign, SK_DPpb_ECDSA));
		//拼接initialiseSecureChannel报文
		return toTLV("BF23", appends(remoteOpId2,transactionId2,controlRefTemplate,smdpOtpk,smdpSign));
	}
	
	/**
	 * 生成configureISDP
	  * @param iccid
	 * @return
	 */
	public String configureIsdp(String iccid){
		String dpOid = SpringPropertyPlaceholderConfigurer.getStringProperty("dp_oid");
		return toTLV("BF24", toTLV("B8", toTLV("80", dpOid)));
	}
	
	/**
	 * 生成storeMetadata
	 * @param iccid
	 * @return
	 */
	public String storeMetadata(String iccid){
		RspProfileWithBLOBs rspProfile = rspProfileService.selectByPrimaryKey(iccid);
		//根据iccid去数据库中获取数据
		//拼装各个参数部分
		String iccid2 = toTLV("5A", iccid);
		String serviceProviderName2 = toTLV("91", rspProfile.getServiceProviderName());
		String profileName2 = toTLV("92", rspProfile.getProfileName());
		String iconType2 = toTLV("93", rspProfile.getIconType());
		String icon2 = toTLV("94", rspProfile.getIcon());
		String profileClass2 = toTLV("95", rspProfile.getProfileClass());
		//拼装notificationConfigInfo序列
		String notifiEvent2 = toTLV("80", rspProfile.getNotifEvent());
		String notifiAddress2 = toTLV("81", rspProfile.getNotifAddress());
		String notifiConfInfo = toTLV("B6", toTLV("30", appends(notifiEvent2,notifiAddress2)));
		
		String profileOwner2 = toTLV("B7", rspProfile.getProfileOwner());
		String ppr2 = toTLV("99", rspProfile.getPpr());
		//profilePolicyRules是可选的
		if(StringUtils.isBlank(rspProfile.getPpr()))ppr2 = "";
		//拼接storeMetadata报文
		String data = appends(iccid2,serviceProviderName2,profileName2,iconType2,
				icon2,profileClass2,notifiConfInfo,profileOwner2,ppr2);
		return toTLV("BF25", data);
	}
	
	/**
	 * 生成replaceSessionKeys(可选)
	 * @return
	 */
	public String replaceSessionKeys(){
		//本地生成
		String initialMacChainingPPK = toTLV("80", RandomUtil.createId().substring(0, 32));
		String ppkEnc = toTLV("81", RandomUtil.createId().substring(0, 32));
		String ppkCmac = toTLV("82", RandomUtil.createId().substring(0, 32));
		//拼接replaceSessionKeys报文
		String data = appends(initialMacChainingPPK,ppkEnc,ppkCmac);
		return toTLV("BF26", data);
	}
	
	/**
	 * 生成loadProfileElement
	 * @param UPP
	 * @return
	 */
	public String loadProfileElements(String UPP){
		String data = UPP;
		return data;
	}
	
	/**
	 * 对数据进行SCP03t加密并计算mac
	 * 并包含加密级别‘33’（加密并签名）‘02’（只加密）‘01’（只签名）
	 * @param tag
	 * @param data
	 * @param enc
	 * @param cmac
	 * @param perMac
	 * @param encCounter
	 * @param securedLevel
	 * @return
	 */
	private CmdApduBean encryptionProfile(String tag ,String data ,String enc ,String cmac , String perMac ,String encCounter ,String securedLevel) {
		String counter = Scp03Counter.addEncCounter(encCounter);
		EncData encOne=new EncData();
		String encData=encOne.encryption(enc, data, counter);//计算密文
		CreateC_MAC one=new CreateC_MAC();
		if(StringUtils.equals(securedLevel, MUTUAL_AUTHENTICATION)){
			String str = toTLVExtend(tag,encData,"1122334455667788");
			String mac=one.create(cmac,str,perMac);//计算MAC
			str = appends(str,mac.substring(0, 16));	//拼装报文
			return new CmdApduBean(str,counter,mac);
		}else if(StringUtils.equals(securedLevel, CONFIDENTIALITY)){
			return new CmdApduBean(toTLV(tag, encData),counter,null);
		}else if(StringUtils.equals(securedLevel, DATA_INTEGRITY)){
			String str = toTLVExtend(tag,data,"1122334455667788");
			String mac=one.create(cmac,str,perMac);//计算MAC
			str = appends(str,mac.substring(0, 16));	//拼装报文
			return new CmdApduBean(str,counter,mac);
		}else{
			return null;
		}
	}
}
	
	

