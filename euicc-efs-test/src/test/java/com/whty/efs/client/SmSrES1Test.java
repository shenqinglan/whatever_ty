package com.whty.efs.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.whty.efs.common.util.DateUtil;
import com.whty.efs.packets.message.EuiccEntity;
import com.whty.efs.packets.message.Header;
import com.whty.efs.webservice.es.message.AuditTrailRecordType;
import com.whty.efs.webservice.es.message.CanonicalizationMethodType;
import com.whty.efs.webservice.es.message.EISType;
import com.whty.efs.webservice.es.message.EISType.AuditTrail;
import com.whty.efs.webservice.es.message.EISType.EumSignedInfo;
import com.whty.efs.webservice.es.message.EISType.EumSignedInfo.EuiccCapabilities;
import com.whty.efs.webservice.es.message.ES1RegisterEISRequest;
import com.whty.efs.webservice.es.message.ES1RegisterEISResponse;
import com.whty.efs.webservice.es.message.ES1SmSr;
import com.whty.efs.webservice.es.message.ES1SmSrService;
import com.whty.efs.webservice.es.message.KeyInfoType;
import com.whty.efs.webservice.es.message.SDRoleType;
import com.whty.efs.webservice.es.message.SecurityDomainType;
import com.whty.efs.webservice.es.message.SignatureMethodType;
import com.whty.efs.webservice.es.message.SignatureType;
import com.whty.efs.webservice.es.message.SignedInfoType;
import com.whty.efs.webservice.service.impl.EsWsClientImpl;

public class SmSrES1Test {
	private static final QName SERVICE_NAME = new QName("http://namespaces.gsma.org/esim-messaging/3", "ES1SmSrService");
	
	private final String _url = "http://localhost:8080/euicc-efs-container/webservice/ES1SmSrService?wsdl";

	private EsWsClientImpl client = null;
	
	private ES1RegisterEISRequest _es1RegisterEIS_parameters = null;
	
	private EuiccEntity<ES1RegisterEISRequest> es1Request = null;
	
	@Before
	public void init()throws Exception{
		client = new EsWsClientImpl();
		client.setWsdl_url_in_string(_url);
		client.init("ES1SmSrService","SmSrES1Port");
		
		Header header = new Header();
		header.setReceiver("whty");
		header.setSender("whty");// 前置定义的合法发送方
		header.setTradeNO("");
		header.setTradeRefNO("");
		header.setMsgType("tath.112.002.01");
		header.setSendTime(DateUtil.dateToDateString(new Date()));
		header.setVersion("01");
		header.setDeviceType("0911");
		
		System.out.println("Invoking es1RegisterEIS...");
        _es1RegisterEIS_parameters = new ES1RegisterEISRequest();
        EISType _eis = new EISType();
        
        
        SignatureType eumSignature = new SignatureType();
        SignedInfoType signedInfo = new SignedInfoType();
        CanonicalizationMethodType canonicalizationMethod = new CanonicalizationMethodType();
        canonicalizationMethod.setAlgorithm("1");
        SignatureMethodType signatureMethod = new SignatureMethodType();
        signatureMethod.setAlgorithm("1");
        signatureMethod.sethMACOutputLength("1");
        signedInfo.setId("1");
        signedInfo.setCanonicalizationMethod(canonicalizationMethod);
        signedInfo.setSignatureMethod(signatureMethod);
        
        KeyInfoType keyInfo = new KeyInfoType();
        keyInfo.setId("1");
        
        
        eumSignature.setId("1");
        eumSignature.setSignedInfo(signedInfo);
        _eis.setEumSignature(eumSignature);
        
        SecurityDomainType isdR = new SecurityDomainType();
        isdR.setAid("1234567890".getBytes());
        isdR.setRole(SDRoleType.ISD_R);
        isdR.setSdin("01".getBytes());
        isdR.setSin("02".getBytes());
        SecurityDomainType.Keyset keyset = new SecurityDomainType.Keyset();
        keyset.setCntr(BigInteger.valueOf(101010));
        keyset.setVersion(1);
        List<SecurityDomainType.Keyset> keysetList = new ArrayList<SecurityDomainType.Keyset>();
        keysetList.add(keyset);
//        isdR.setKeyset(keysetList);
        
        EumSignedInfo eumSignedInfo = new EumSignedInfo();
        eumSignedInfo.setEid("123456".getBytes());
        eumSignedInfo.setEumId("1");
        eumSignedInfo.setProductionDate(DateUtil.dateToXmlDate(new Date()));
        SecurityDomainType ecasd = new SecurityDomainType();
        ecasd.setAid("1234567890".getBytes());
        ecasd.setRole(SDRoleType.ECASD);
        ecasd.setSdin("01".getBytes());
        ecasd.setSin("02".getBytes());
//        ecasd.setKeyset(keysetList);
        eumSignedInfo.setEcasd(ecasd);
        
        EuiccCapabilities capabilities = new EuiccCapabilities();
        capabilities.setCattpSupport(true);
        capabilities.setCattpVersion("11");
        capabilities.setHttpSupport(false);
        capabilities.setHttpVersion("13");
        capabilities.setRemoteProvisioningVersion("21");
        capabilities.setSecurePacketVersion("33");
        eumSignedInfo.setEuiccCapabilities(capabilities);
        
        AuditTrailRecordType auditTrailRecordType = new AuditTrailRecordType();
        auditTrailRecordType.setEid("123456".getBytes());
        auditTrailRecordType.setIccid("01");
        auditTrailRecordType.setIsdPAid("2345678".getBytes());
        auditTrailRecordType.setOperationDate(DateUtil.dateToXmlDate(new Date()));
        auditTrailRecordType.setOperationType("a1".getBytes());
        auditTrailRecordType.setRequesterId("6666");
        auditTrailRecordType.setImei("13547789090990".getBytes());
        auditTrailRecordType.setMeid("9966566".getBytes());
        auditTrailRecordType.setSmsrId("123456");
        List<AuditTrailRecordType> auditTrailRecordList = new ArrayList<AuditTrailRecordType>();
        auditTrailRecordList.add(auditTrailRecordType);
        AuditTrail auditTrail = new AuditTrail();
//        auditTrail.setRecord(auditTrailRecordList);
      
        _eis.setLastAuditDate(DateUtil.dateToXmlDate(new Date()));
        _eis.setSmsrId("123456");
        _eis.setIsdR(isdR);
        _eis.setEumSignedInfo(eumSignedInfo);
        _eis.setAuditTrail(auditTrail);
        _es1RegisterEIS_parameters.setEis(_eis);

		es1Request = new EuiccEntity<ES1RegisterEISRequest>(header,_es1RegisterEIS_parameters);
	}

	
	/**
	 * webservice协议
	 * @throws Exception
	 */
	@Test
    public void es1RegisterEISByWs() throws Exception {
        URL wsdlURL = new URL(_url);
      
        ES1SmSrService ss = new ES1SmSrService(wsdlURL, SERVICE_NAME);
        ES1SmSr port = ss.getSmSrES1Port();  
       
        ES1RegisterEISResponse _es1RegisterEIS__return = port.es1RegisterEIS(_es1RegisterEIS_parameters);
        System.out.println("es1RegisterEIS.result=" + new Gson().toJson(_es1RegisterEIS__return));
    }
	
	@Test
	public void es1RegisterEISByCommon()throws Exception {
		String returnStr = client.es1RegisterEIS(es1Request);
		System.out.println(returnStr);
	}
    
	/**
	 * http协议
	 * @throws Exception
	 */
    @Test
    public void es1RegisterEISByHttp()throws Exception{
    	String xml = inputStream2String(SmSrES1Test.class.getClassLoader().getResourceAsStream("es1.xml"));
    	//String xml = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:ns=\"http://namespaces.gsma.org/esim-messaging/3\" xmlns:add=\"http://www.w3.org/2005/08/addressing\"><soap:Header><add:Action>01</add:Action><add:MessageID>whty</add:MessageID><add:To>whty</add:To><add:From><add:Address>tath.112.002.01</add:Address><add:ReferenceParameters></add:ReferenceParameters><add:Metadata></add:Metadata></add:From></soap:Header><soap:Body><ns:ES1-RegisterEISRequest><ns:Eis><ns:Smsr-id>123456</ns:Smsr-id><ns:Isd-r><ns:Aid>31323334353637383930</ns:Aid><ns:Sin>3032</ns:Sin><ns:Sdin>3031</ns:Sdin><ns:Role>ISD-R</ns:Role></ns:Isd-r></ns:Eis></ns:ES1-RegisterEISRequest></soap:Body></soap:Envelope>";
    	HttpClientSoap.httpClientPostForSoap(StringUtils.remove(_url, "?wsdl"), xml);
    }
    
    String inputStream2String(InputStream is) throws IOException{
	   BufferedReader in = new BufferedReader(new InputStreamReader(is));
	   StringBuffer buffer = new StringBuffer();
	   String line = "";
	   while ((line = in.readLine()) != null){
	     buffer.append(line);
	   }
	   return buffer.toString();
	}
}
