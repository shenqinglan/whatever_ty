package com.whty.efs.client;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.efs.webservice.es.message.ES3AuditEISRequest;
import com.whty.efs.webservice.es.message.ES3AuditEISResponse;
import com.whty.efs.webservice.es.message.ES3CreateISDPRequest;
import com.whty.efs.webservice.es.message.ES3CreateISDPResponse;
import com.whty.efs.webservice.es.message.ES3DeleteISDPRequest;
import com.whty.efs.webservice.es.message.ES3DeleteISDPResponse;
import com.whty.efs.webservice.es.message.ES3DisableProfileRequest;
import com.whty.efs.webservice.es.message.ES3DisableProfileResponse;
import com.whty.efs.webservice.es.message.ES3EnableProfileRequest;
import com.whty.efs.webservice.es.message.ES3EnableProfileResponse;
import com.whty.efs.webservice.es.message.ES3GetEISRequest;
import com.whty.efs.webservice.es.message.ES3GetEISResponse;
import com.whty.efs.webservice.es.message.ES3ProfileDownloadCompletedRequest;
import com.whty.efs.webservice.es.message.ES3ProfileDownloadCompletedResponse;
import com.whty.efs.webservice.es.message.ES3SendDataRequest;
import com.whty.efs.webservice.es.message.ES3SendDataResponse;
import com.whty.efs.webservice.es.message.ES3SmSr;
import com.whty.efs.webservice.es.message.ES3SmSrService;
import com.whty.efs.webservice.es.message.ES3UpdateConnectivityParametersRequest;
import com.whty.efs.webservice.es.message.ES3UpdateConnectivityParametersResponse;
import com.whty.efs.webservice.es.message.ES3UpdatePolicyRulesRequest;
import com.whty.efs.webservice.es.message.ES3UpdateSubscriptionAddressRequest;
import com.whty.efs.webservice.es.message.POL2RuleActionType;
import com.whty.efs.webservice.es.message.POL2RuleQualificationType;
import com.whty.efs.webservice.es.message.POL2RuleSubjectType;
import com.whty.efs.webservice.es.message.POL2RuleType;
import com.whty.efs.webservice.es.message.POL2Type;

public class SmSrES3Test {
 	private static final QName SERVICE_NAME = new QName("http://namespaces.gsma.org/esim-messaging/3", "ES3SmSrService");
 	private static final String _url = "http://localhost:8080/euicc-efs-container/webservice/ES3SmSrService?wsdl";

 	@Test
 	public void getEisTest() throws MalformedURLException {
 		com.whty.efs.webservice.es.message.ES3GetEISRequest eisRequest = new ES3GetEISRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		ES3GetEISResponse es3GetEISResponse = es3SmSr.es3GetEIS(eisRequest);
 		
 		System.out.println("ES3getEisTest>>>>>>>  " + new Gson().toJson(es3GetEISResponse));
	}
 	
 	@Test
 	public void deleteISDPTest() throws MalformedURLException {
 		com.whty.efs.webservice.es.message.ES3DeleteISDPRequest deleteISDPRequest = new ES3DeleteISDPRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		ES3DeleteISDPResponse es3DeleteISDPResponse = es3SmSr.es3DeleteISDP(deleteISDPRequest);
 		System.out.println("es3DeleteISDPResponse>>>>>>>  " + new Gson().toJson(es3DeleteISDPResponse));
	}
 
 	
 	@Test
 	public void updateSubscriptionAddressTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3UpdateSubscriptionAddressRequest parameters = new ES3UpdateSubscriptionAddressRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		es3SmSr.es3UpdateSubscriptionAddress(parameters);
 	}
 	
 	@Test
 	public void createISDPTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3CreateISDPRequest parameters = new ES3CreateISDPRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		ES3CreateISDPResponse es3CreateISDPResponse = es3SmSr.es3CreateISDP(parameters);
 		System.out.println("es3CreateISDPResponse>>>>>>>  " + new Gson().toJson(es3CreateISDPResponse));
 	}
 	
 	@Test
 	public void updatePolicyRulesTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3UpdatePolicyRulesRequest parameters = new ES3UpdatePolicyRulesRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		es3SmSr.es3UpdatePolicyRules(parameters);
 	}
 	
 	@Test
 	public void profileDownloadCompletedTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3ProfileDownloadCompletedRequest parameters = new ES3ProfileDownloadCompletedRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		
 		parameters.setEid("89001012012341234012345678901224".getBytes());
 		parameters.setFunctionCallIdentifier("01");
 		parameters.setIccid("12356465");
 		POL2Type pol2Type = new POL2Type();
 		List<POL2RuleType> rule = new ArrayList<POL2RuleType>();
 		POL2RuleType e = new POL2RuleType();
 		e.setAction(POL2RuleActionType.DELETE);
 		e.setQualification(POL2RuleQualificationType.AUTO_DELETE);
 		e.setSubject(POL2RuleSubjectType.PROFILE);
		rule.add(e);
		pol2Type.setRule(rule);
		parameters.setPol2(pol2Type);
 		ES3ProfileDownloadCompletedResponse es3ProfileDownloadCompletedResponse = es3SmSr.es3ProfileDownloadCompleted(parameters);
 		System.out.println("es3ProfileDownloadCompletedResponse>>>>>>>  " + new Gson().toJson(es3ProfileDownloadCompletedResponse));
 	}
 	
 	@Test
 	public void updateConnectivityParametersTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3UpdateConnectivityParametersRequest parameters = new ES3UpdateConnectivityParametersRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		parameters.setConnectivityParameters("4142".getBytes());
 		ES3UpdateConnectivityParametersResponse es3UpdateConnectivityParametersResponse = es3SmSr.es3UpdateConnectivityParameters(parameters);
 		System.out.println("es3UpdateConnectivityParametersResponse>>>>>>>  " + new Gson().toJson(es3UpdateConnectivityParametersResponse));
 	}
 	
 	@Test
 	public void auditEISTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3AuditEISRequest parameters = new ES3AuditEISRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		parameters.setEid("544554".getBytes());
 		parameters.setFunctionCallIdentifier("01");
 		parameters.setValidityPeriod(new BigInteger("123"));
 		List<String> iccid = new ArrayList<String>();
 		iccid.add("524151");
 		iccid.add("213sadsa");
 		parameters.setIccid(iccid);
 		ES3AuditEISResponse es3AuditEISResponse = es3SmSr.es3AuditEIS(parameters);
 		System.out.println("es3AuditEISResponse>>>>>>>  " + new Gson().toJson(es3AuditEISResponse));
 	}
 	
 	@Test
 	public void sendDataTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3SendDataRequest parameters = new ES3SendDataRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		parameters.setData("465464574".getBytes());
 		parameters.setEid("1245".getBytes());
 		parameters.setFunctionCallIdentifier("01");
 		parameters.setMoreToDo(true);
 		parameters.setSdAid("4564".getBytes());
 		parameters.setValidityPeriod(new BigInteger("121"));
 		ES3SendDataResponse es3SendDataResponse = es3SmSr.es3SendData(parameters);
 		System.out.println("es3SendDataResponse>>>>>>>  " + new Gson().toJson(es3SendDataResponse));
 	}
 	
 	@Test
 	public void disableProfileTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3DisableProfileRequest parameters = new ES3DisableProfileRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		ES3DisableProfileResponse es3DisableProfileResponse = es3SmSr.es3DisableProfile(parameters);
 		System.out.println("es3DisableProfileResponse>>>>>>>  " + new Gson().toJson(es3DisableProfileResponse));
 	}
 	
 	@Test
 	public void enableProfileTest() throws MalformedURLException{
 		com.whty.efs.webservice.es.message.ES3EnableProfileRequest parameters = new ES3EnableProfileRequest();
 		URL wsdlURL = new URL(_url);
 		ES3SmSrService es3SmSrService = new ES3SmSrService(wsdlURL, SERVICE_NAME);
 		ES3SmSr es3SmSr = es3SmSrService.getSmSrES3Port();
 		ES3EnableProfileResponse es3EnableProfileResponse =	es3SmSr.es3EnableProfile(parameters);
 		System.out.println("es3EnableProfileResponse>>>>>>>  " + new Gson().toJson(es3EnableProfileResponse));
 	}
}
