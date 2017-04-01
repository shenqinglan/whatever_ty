package com.whty.efs.client;

import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Test;

import com.google.gson.Gson;
import com.whty.efs.webservice.es.message.POL2RuleActionType;
import com.whty.efs.webservice.es.message.POL2RuleQualificationType;
import com.whty.efs.webservice.es.message.POL2RuleSubjectType;
import com.whty.efs.webservice.es.message.POL2RuleType;
import com.whty.efs.webservice.es.message.POL2Type;
import com.whty.efs.webservice.es.message.SubscriptionAddressType;
import com.whty.efs.webservice.es.message.ES2DisableProfileRequest;
import com.whty.efs.webservice.es.message.ES2DownloadProfileRequest;
import com.whty.efs.webservice.es.message.ES2EnableProfileRequest;
import com.whty.efs.webservice.es.message.ES2GetEISRequest;
import com.whty.efs.webservice.es.message.ES2Mno;
import com.whty.efs.webservice.es.message.ES2MnoService;
import com.whty.efs.webservice.es.message.ES2UpdatePolicyRulesRequest;
import com.whty.efs.webservice.es.message.ES2UpdateSubscriptionAddressRequest;

public class MnoES2Test {
	 	private static final QName SERVICE_NAME = new QName("http://namespaces.gsma.org/esim-messaging/3", "ES2MnoService");
	 	private static final String _url = "http://localhost:8080/euicc-efs-container/webservice/ES2MnoService?wsdl";
	 	
	 	/**
	 	 * webservice协议
	 	 * @param args
	 	 * @throws java.lang.Exception
	 	 */
	 	@Test
	    public void deleteProfile() throws java.lang.Exception {
	        URL wsdlURL = new URL(_url);
	        ES2MnoService ss = new ES2MnoService(wsdlURL, SERVICE_NAME);
	        ES2Mno port = ss.getMnoES2Port();  
	        
	        System.out.println("Invoking es2DeleteProfile...");
	        com.whty.efs.webservice.es.message.ES2DisableProfileRequest _es2DisableProfile_parameters = new ES2DisableProfileRequest();
	        _es2DisableProfile_parameters.setEid("89001012012341234012345678901224".getBytes());
	        _es2DisableProfile_parameters.setIccid("00");
	        _es2DisableProfile_parameters.setSmsrId("01");
	        _es2DisableProfile_parameters.setFunctionCallIdentifier("02");
	        _es2DisableProfile_parameters.setValidityPeriod(new BigInteger("2"));
	        com.whty.efs.webservice.es.message.ES2DisableProfileResponse _es2DisableProfile__return = port.es2DisableProfile(_es2DisableProfile_parameters);
	        System.out.println("es2DisableProfile.result=" + new Gson().toJson(_es2DisableProfile__return));

	    }
	    @Test
	    public void downloadProfile() throws java.lang.Exception {
	        URL wsdlURL = new URL(_url);
	        ES2MnoService ss = new ES2MnoService(wsdlURL, SERVICE_NAME);
	        ES2Mno port = ss.getMnoES2Port();  
	        
	        System.out.println("Invoking es2DownloadProfile...");
	        com.whty.efs.webservice.es.message.ES2DownloadProfileRequest _es2DownloadProfile_parameters = new ES2DownloadProfileRequest();
	        _es2DownloadProfile_parameters.setEid("89001012012341234012345678901224".getBytes());
	        _es2DownloadProfile_parameters.setIccid("00");
	        _es2DownloadProfile_parameters.setSmSrId("1");
	        _es2DownloadProfile_parameters.setProfileType("2");
	        _es2DownloadProfile_parameters.setEnableProfile(false);
	        _es2DownloadProfile_parameters.setFunctionCallIdentifier("02");
	        _es2DownloadProfile_parameters.setValidityPeriod(new BigInteger("2"));
	        com.whty.efs.webservice.es.message.ES2DownloadProfileResponse _es2DownloadProfile__return = port.es2DownloadProfile(_es2DownloadProfile_parameters);
	        System.out.println("es2DownloadProfile.result=" + new Gson().toJson(_es2DownloadProfile__return));

	    }
	    @Test
	    public void disableProfileTest() throws java.lang.Exception {
	        URL wsdlURL = new URL(_url);
	        ES2MnoService ss = new ES2MnoService(wsdlURL, SERVICE_NAME);
	        ES2Mno port = ss.getMnoES2Port();  
	        
	        System.out.println("Invoking es2DisableProfile...");
	        com.whty.efs.webservice.es.message.ES2DisableProfileRequest _es2DisableProfile_parameters = new ES2DisableProfileRequest();
	        _es2DisableProfile_parameters.setEid("89001012012341234012345678901224".getBytes());
	        _es2DisableProfile_parameters.setIccid("00");
	        _es2DisableProfile_parameters.setFunctionCallIdentifier("02");
	        _es2DisableProfile_parameters.setValidityPeriod(new BigInteger("2"));
	        com.whty.efs.webservice.es.message.ES2DisableProfileResponse _es2DisableProfile__return = port.es2DisableProfile(_es2DisableProfile_parameters);
	        System.out.println("es2DisableProfile.result=" + new Gson().toJson(_es2DisableProfile__return));

	    }
	    
	    @Test
	    public void enableProfile() throws java.lang.Exception {
	        URL wsdlURL = new URL(_url);
	        ES2MnoService ss = new ES2MnoService(wsdlURL, SERVICE_NAME);
	        ES2Mno port = ss.getMnoES2Port();  
	        
	        System.out.println("Invoking es2EnableProfile...");
	        com.whty.efs.webservice.es.message.ES2EnableProfileRequest _es2EnableProfile_parameters = new ES2EnableProfileRequest();
	        _es2EnableProfile_parameters.setEid("89001012012341234012345678901224".getBytes());
	        _es2EnableProfile_parameters.setIccid("00");
	        _es2EnableProfile_parameters.setFunctionCallIdentifier("02");
	        _es2EnableProfile_parameters.setValidityPeriod(new BigInteger("2"));
	        com.whty.efs.webservice.es.message.ES2EnableProfileResponse _es2EnableProfile__return = port.es2EnableProfile(_es2EnableProfile_parameters);
	        System.out.println("es2EnableProfile.result=" + new Gson().toJson(_es2EnableProfile__return));

	    }
	    
	    @Test
	    public void getEIS() throws java.lang.Exception {
	        URL wsdlURL = new URL(_url);
	        ES2MnoService ss = new ES2MnoService(wsdlURL, SERVICE_NAME);
	        ES2Mno port = ss.getMnoES2Port();  
	        
	        System.out.println("Invoking es2GetEis...");
	        com.whty.efs.webservice.es.message.ES2GetEISRequest _es2GetEIS_parameters = new ES2GetEISRequest();
	        _es2GetEIS_parameters.setEid("89001012012341234012345678901224".getBytes());
	        _es2GetEIS_parameters.setFunctionCallIdentifier("02");
	        _es2GetEIS_parameters.setValidityPeriod(new BigInteger("2"));
	        com.whty.efs.webservice.es.message.ES2GetEISResponse _es2GetEIS__return = port.es2GetEIS(_es2GetEIS_parameters);
	        System.out.println("es2GetEis.result=" + new Gson().toJson(_es2GetEIS__return));

	    }
	    @Test
	    public void updateSubscriptionAddressTest() throws java.lang.Exception {
	        URL wsdlURL = new URL(_url);
	        ES2MnoService ss = new ES2MnoService(wsdlURL, SERVICE_NAME);
	        ES2Mno port = ss.getMnoES2Port();  
	        SubscriptionAddressType subscrAddr = new SubscriptionAddressType();
	        subscrAddr.setImsi("225555585");
	        subscrAddr.setMsisdn("88855555");
	        
	        System.out.println("Invoking es2UpdateSubscriptionAddress...");
	        com.whty.efs.webservice.es.message.ES2UpdateSubscriptionAddressRequest _es2UpdateSubscriAddr_parameters = new ES2UpdateSubscriptionAddressRequest();
	        _es2UpdateSubscriAddr_parameters.setEid("89001012012341234012345678901224".getBytes());
	        _es2UpdateSubscriAddr_parameters.setIccid("00");
	        _es2UpdateSubscriAddr_parameters.setNewSubscriptionAddress(subscrAddr);
	        _es2UpdateSubscriAddr_parameters.setFunctionCallIdentifier("02");
	        _es2UpdateSubscriAddr_parameters.setValidityPeriod(new BigInteger("2"));
	        com.whty.efs.webservice.es.message.ES2UpdateSubscriptionAddressResponse _es2UpdateSubscriAddr__return = port.es2UpdateSubscriptionAddress(_es2UpdateSubscriAddr_parameters);
	        System.out.println("es2_es2UpdateSubscritionAddress.result=" + new Gson().toJson(_es2UpdateSubscriAddr__return));

	    }
	    
	    @Test
	    public void updatePolicyRule() throws java.lang.Exception {
	        URL wsdlURL = new URL(_url);
	        ES2MnoService ss = new ES2MnoService(wsdlURL, SERVICE_NAME);
	        ES2Mno port = ss.getMnoES2Port();  
	        
	        POL2RuleSubjectType subject = POL2RuleSubjectType.fromValue("PROFILE");
	        POL2RuleActionType action = POL2RuleActionType.fromValue("DISABLE");
	        POL2RuleQualificationType qualify = POL2RuleQualificationType.fromValue("Not-Allowed");
	        POL2RuleType pol2Rule = new POL2RuleType();
	        pol2Rule.setSubject(subject);
	        pol2Rule.setAction(action);
	        pol2Rule.setQualification(qualify);
	        List<POL2RuleType> pol2RuleList = new ArrayList<POL2RuleType>();
	        pol2RuleList.add(pol2Rule);
	        
	        POL2Type pol2Type = new POL2Type();
	        pol2Type.setRule(pol2RuleList);
	        
	        System.out.println("Invoking es2UpdatePolicyRule...");
	        com.whty.efs.webservice.es.message.ES2UpdatePolicyRulesRequest _es2UpdatePolicyRules_parameters = new ES2UpdatePolicyRulesRequest();
	        _es2UpdatePolicyRules_parameters.setEid("89001012012341234012345678901224".getBytes());
	        _es2UpdatePolicyRules_parameters.setIccid("00");
	        _es2UpdatePolicyRules_parameters.setPol2(pol2Type);
	        _es2UpdatePolicyRules_parameters.setFunctionCallIdentifier("02");
	        _es2UpdatePolicyRules_parameters.setValidityPeriod(new BigInteger("2"));
	        com.whty.efs.webservice.es.message.ES2UpdatePolicyRulesResponse _es2UpdatePolicyRules__return = port.es2UpdatePolicyRules(_es2UpdatePolicyRules_parameters);
	        System.out.println("esUpdatePolicyRule.result=" + new Gson().toJson(_es2UpdatePolicyRules__return));

	    }
}
