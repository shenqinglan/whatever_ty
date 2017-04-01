package com.whty.efs.client;

import java.math.BigInteger;
import java.net.URL;
import java.util.Date;
import javax.xml.namespace.QName;
import org.junit.Test;
import com.google.gson.Gson;
import com.whty.efs.common.util.DateUtil;
import com.whty.efs.webservice.es.message.CanonicalizationMethodType;
import com.whty.efs.webservice.es.message.DSAKeyValueType;
import com.whty.efs.webservice.es.message.EISType;
import com.whty.efs.webservice.es.message.EISType.AdditionalProperties;
import com.whty.efs.webservice.es.message.EISType.AuditTrail;
import com.whty.efs.webservice.es.message.EISType.EumSignedInfo;
import com.whty.efs.webservice.es.message.EISType.EumSignedInfo.EuiccCapabilities;
import com.whty.efs.webservice.es.message.ES7AuthenticateSMSRRequest;
import com.whty.efs.webservice.es.message.ES7AuthenticateSMSRResponse;
import com.whty.efs.webservice.es.message.ES7CreateAdditionalKeySetRequest;
import com.whty.efs.webservice.es.message.ES7CreateAdditionalKeySetResponse;
import com.whty.efs.webservice.es.message.ES7HandoverEUICCRequest;
import com.whty.efs.webservice.es.message.ES7SmSr;
import com.whty.efs.webservice.es.message.ES7SmSrService;
import com.whty.efs.webservice.es.message.KeyInfoType;
import com.whty.efs.webservice.es.message.KeyValueType;
import com.whty.efs.webservice.es.message.PGPDataType;
import com.whty.efs.webservice.es.message.RSAKeyValueType;
import com.whty.efs.webservice.es.message.RetrievalMethodType;
import com.whty.efs.webservice.es.message.SDRoleType;
import com.whty.efs.webservice.es.message.SPKIDataType;
import com.whty.efs.webservice.es.message.SecurityDomainType;
import com.whty.efs.webservice.es.message.SignatureMethodType;
import com.whty.efs.webservice.es.message.SignatureType;
import com.whty.efs.webservice.es.message.SignedInfoType;
import com.whty.efs.webservice.es.message.TransformsType;
import com.whty.efs.webservice.es.message.X509DataType;
import com.whty.efs.webservice.es.message.X509IssuerSerialType;

public class SmSrES7Test {

 	private static final QName SERVICE_NAME = new QName("http://namespaces.gsma.org/esim-messaging/3", "ES7SmSrService");
 	private static final String _url = "http://localhost:8080/euicc-efs-container/webservice/ES7SmSrService?wsdl";
 	
 	@Test
 	public void ES7HandoverEUICC() throws Exception{
 		com.whty.efs.webservice.es.message.ES7HandoverEUICCRequest request = new ES7HandoverEUICCRequest();
 		//webservice方式
		URL wsdlURL = new URL(_url);
        ES7SmSrService ss = new ES7SmSrService(wsdlURL, SERVICE_NAME);
        ES7SmSr port = ss.getES7SmSrPort(); 
        EISType eis = new EISType();
        EumSignedInfo eumSignedInfo = new EumSignedInfo();
        
        byte[] eid = "89001012012341234012345678901224".getBytes();
		eumSignedInfo.setEid(eid);
		eumSignedInfo.setEumId("1");
		eumSignedInfo.setProductionDate(DateUtil.dateToXmlDate(new Date()));
		eumSignedInfo.setPlatformType("2");
		eumSignedInfo.setPlatformVersion("1");
		eumSignedInfo.setIsdPLoadfileAid("1241545".getBytes());
		eumSignedInfo.setIsdPModuleAid("1212222".getBytes());
		
		SecurityDomainType securityDomain = new SecurityDomainType();
		securityDomain.setAid("4545455".getBytes());
		securityDomain.setSin("12345".getBytes());
		securityDomain.setSdin("585858".getBytes());
		securityDomain.setRole(SDRoleType.ECASD);
		
		eumSignedInfo.setEcasd(securityDomain);
		EuiccCapabilities capabilites = new EuiccCapabilities();
		capabilites.setCattpSupport(true);
		capabilites.setCattpVersion("01");
		capabilites.setHttpSupport(true);
		capabilites.setHttpVersion("1");
		capabilites.setRemoteProvisioningVersion("111");
		capabilites.setSecurePacketVersion("1");
		eumSignedInfo.setEuiccCapabilities(capabilites);
		eis.setEumSignedInfo(eumSignedInfo);
		
		SignatureType eumSignedNature = new SignatureType();
		
		SignedInfoType signedInfo = new SignedInfoType();
		
		CanonicalizationMethodType methodType = new CanonicalizationMethodType();
		methodType.setAlgorithm("1");
		signedInfo.setCanonicalizationMethod(methodType);
		
		SignatureMethodType signedNatureMethod = new SignatureMethodType();
		signedNatureMethod.setAlgorithm("1");
		signedNatureMethod.sethMACOutputLength("12");
		signedInfo.setSignatureMethod(signedNatureMethod);
		
		signedInfo.setId("123");
		
		eumSignedNature.setSignedInfo(signedInfo);
		eumSignedNature.setId("1245");
		KeyInfoType keyInfo = new KeyInfoType();
		keyInfo.setKeyName("1");
		KeyValueType keyValue = new KeyValueType();
		DSAKeyValueType dSAKeyValue = new DSAKeyValueType();
		dSAKeyValue.setP("795554625441".getBytes());
		dSAKeyValue.setQ("4674987121312".getBytes());
		dSAKeyValue.setG("562679773423".getBytes());
		dSAKeyValue.setY("774454842212".getBytes());
		dSAKeyValue.setJ("825268565415".getBytes());
		dSAKeyValue.setSeed("20123357858512".getBytes());
		dSAKeyValue.setPgenCounter("8945451212556".getBytes());
		keyValue.setdSAKeyValue(dSAKeyValue);
		
		RSAKeyValueType rSAKeyValue = new RSAKeyValueType();
		rSAKeyValue.setModulus("259784154845".getBytes());
		rSAKeyValue.setExponent("1654521244498".getBytes());
		keyValue.setrSAKeyValue(rSAKeyValue);
		
		keyInfo.setKeyValue(keyValue);
		RetrievalMethodType retrevalMethod = new RetrievalMethodType();
		retrevalMethod.setType("1");
		retrevalMethod.setURI("1");
		TransformsType transformType = new TransformsType();
		retrevalMethod.setTransforms(transformType);
		
		keyInfo.setRetrevalMethod(retrevalMethod);
		
		X509DataType x509DataType = new X509DataType();
		X509IssuerSerialType x509IssuerSerial = new X509IssuerSerialType();
		x509IssuerSerial.setX509IssuerName("1");
		BigInteger bigNumber = new BigInteger("123214");
		x509IssuerSerial.setX509SerialNumber(bigNumber);
		
		x509DataType.setX509IssuerSerial(x509IssuerSerial);
		x509DataType.setX509SKI("774114557441");
		x509DataType.setX509SubjectName("78784545421578");
		x509DataType.setX509Certificate("145568975248987");
		x509DataType.setX509CRL("987654654879557");
		keyInfo.setX509DataType(x509DataType);
		PGPDataType pGPData = new PGPDataType();
		pGPData.setpGPKeyID("5454122665");
		pGPData.setpGPKeyPacket("6568778942");
		
		keyInfo.setpGPData(pGPData);
		
		SPKIDataType sPKIData = new SPKIDataType();
		sPKIData.setsPKISexp("47876467");
		
		keyInfo.setsPKIData(sPKIData);
		keyInfo.setMgmtData("7545412135686");
		eumSignedNature.setKeyInfo(keyInfo);
		eis.setEumSignature(eumSignedNature);
		
		BigInteger memorySize = new BigInteger("213");
		eis.setRemainingMemory(memorySize);
		BigInteger profileMemorySize = new BigInteger("123");
		eis.setAvailableMemoryForProfiles(profileMemorySize);
		eis.setLastAuditDate(DateUtil.dateToXmlDate(new Date()));
		eis.setSmsrId("0819");
		AdditionalProperties properites = new AdditionalProperties();
		eis.setAdditionalProperties(properites);
		AuditTrail auditTrail = new AuditTrail();
		eis.setAuditTrail(auditTrail);
//		eis.setIsdR(value);
//		eis.setLastAuditDate(value);
//		eis.setEumSignature(value);
//		eis.setRemainingMemory(value);
//		eis.setAvailableMemoryForProfiles(value);
		
		request.setFunctionCallIdentifier("01");
		request.setEis(eis);
		port.es7HandoverEUICC(request);
 		
		//http方式，可选
 		/*String xml = XmlUtil.inputStream2String(SmSrES7Test.class.getClassLoader().getResourceAsStream("es7HandleoverEUICC.xml"));
    	String response = HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);*/
 	}
	
	@Test
 	public void authenticateSMSRTest() throws Exception{
		//webservice方式
 		com.whty.efs.webservice.es.message.ES7AuthenticateSMSRRequest parameters = new ES7AuthenticateSMSRRequest();
		URL wsdlURL = new URL(_url);
        ES7SmSrService es7SmSrService = new ES7SmSrService(wsdlURL, SERVICE_NAME);
        ES7SmSr es7SmSr = es7SmSrService.getES7SmSrPort(); 
        EISType eis = new EISType();
        EumSignedInfo eumSignedInfo = new EumSignedInfo();
        byte[] eid = "89001012012341234012345678901224".getBytes();
		eumSignedInfo.setEid(eid);
		eis.setEumSignedInfo(eumSignedInfo);
		parameters.setEis(eis);
		parameters.setSmsrCertificate("2281B180E20900AC3A01A97F2181A59301014201015F2001019501885F2404214501017303C801027f4946B041046466E042804FAAC48F839EA53E85D0B8B93F2F015028A472F07F3227AF408170ACFB39D198BA7D0DCFF3DE5032A6CC8F6ACC84EF556BFE530DEC0FF75F2AF59AF001005F3740F1B7D2080AABCEE4EEE484130279F075CEF5A34C27AC87B0F8413DF8D3548A3C4666F06A812A079E740B1956566585F49F65D6A559F5C5B41C3795D1524A63DF".getBytes());
		ES7AuthenticateSMSRResponse es7AuthenticateSMSRResponse = es7SmSr.es7authenticateSMSR(parameters);
		System.out.println("es7AuthenticateSMSRResponse >>>>>>>  " + new Gson().toJson(es7AuthenticateSMSRResponse));
		
		//http方式，可选
		/*String xml = XmlUtil.inputStream2String(SmSrES7Test.class.getClassLoader().getResourceAsStream("es7AuthenticateSMSR.xml"));
    	String response = HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);*/
 	}
 	
 	@Test
 	public void createAdditionalKeySet() throws Exception{
 		//webservice方式
 		com.whty.efs.webservice.es.message.ES7CreateAdditionalKeySetRequest parameters = new ES7CreateAdditionalKeySetRequest();
 		URL wsdlURL = new URL(_url);
        ES7SmSrService es7SmSrService = new ES7SmSrService(wsdlURL, SERVICE_NAME);
        ES7SmSr es7SmSr = es7SmSrService.getES7SmSrPort(); 
        parameters.setEid("89001012012341234012345678901224".getBytes());
        parameters.setEphemeralPublicKey("2281A680E28901A13A0217A6159002030995011080018881011082010183010291007F494104C8A12565C76B64BA84305D4370704BEBCA4799DDACF035402F1FD85E180A1801AE64BCCC968632819373E3CAA48559930F7A9C34C0FDE9922FF2268A07CE5F575F3740AA559462E0238A57DD6156CBF626F336F12E7DE0C8AE174EDCD4A0F4CCA588CACA2A513E2752168C2CFA8ABEBDD844EA96359E33081E1976B301DD1AA86BBABC".getBytes());
        ES7CreateAdditionalKeySetResponse es7CreateAdditionalKeySetResponse = es7SmSr.es7createAdditionalKeySet(parameters);
        System.out.println("es7CreateAdditionalKeySetResponse >>>>>>>  "+new Gson().toJson(es7CreateAdditionalKeySetResponse));
 		
        //http方式，可选
 		/*String xml = XmlUtil.inputStream2String(SmSrES7Test.class.getClassLoader().getResourceAsStream("es7CreateAdditionalKeySet.xml"));
    	String response = HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);*/
 	}
}
