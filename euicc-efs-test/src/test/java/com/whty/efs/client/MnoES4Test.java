package com.whty.efs.client;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.junit.Test;


import com.google.gson.Gson;
import com.whty.efs.webservice.es.message.ES4AuditEISRequest;
import com.whty.efs.webservice.es.message.POL2RuleActionType;
import com.whty.efs.webservice.es.message.POL2RuleQualificationType;
import com.whty.efs.webservice.es.message.POL2RuleSubjectType;
import com.whty.efs.webservice.es.message.POL2RuleType;
import com.whty.efs.webservice.es.message.POL2Type;
import com.whty.efs.webservice.es.message.SubscriptionAddressType;
import com.whty.efs.webservice.es.message.ES4DeleteProfileRequest;
import com.whty.efs.webservice.es.message.ES4DisableProfileRequest;
import com.whty.efs.webservice.es.message.ES4EnableProfileRequest;
import com.whty.efs.webservice.es.message.ES4GetEISRequest;
import com.whty.efs.webservice.es.message.ES4Mno;
import com.whty.efs.webservice.es.message.ES4MnoService;
import com.whty.efs.webservice.es.message.ES4PrepareSMSRChangeRequest;
import com.whty.efs.webservice.es.message.ES4SMSRChangeRequest;
import com.whty.efs.webservice.es.message.ES4UpdatePolicyRulesRequest;
import com.whty.efs.webservice.es.message.ES4UpdateSubscriptionAddressRequest;

public class MnoES4Test {
	private static final QName SERVICE_NAME = new QName("http://namespaces.gsma.org/esim-messaging/3", "ES4MnoService");
 	private static final String _url = "http://localhost:8080/euicc-efs-container/webservice/ES4MnoService?wsdl";
	 /*
     * ES4 UpdatePolicyRules 更新策略规则
     */
    @Test
    public void updatePolicyRules() throws Exception{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
        POL2RuleSubjectType subject = POL2RuleSubjectType.fromValue("PROFILE");
        POL2RuleActionType action = POL2RuleActionType.fromValue("DELETE");
        POL2RuleQualificationType qualify = POL2RuleQualificationType.fromValue("Not-Allowed");
        POL2RuleType pol2Rule = new POL2RuleType();
        pol2Rule.setSubject(subject);
        pol2Rule.setAction(action);
        pol2Rule.setQualification(qualify);
        List<POL2RuleType> pol2RuleList = new ArrayList<POL2RuleType>();
        pol2RuleList.add(pol2Rule);
     
        System.out.println("Invoking es4UpdatePolicyRules...");
        com.whty.efs.webservice.es.message.ES4UpdatePolicyRulesRequest _es4UpdatePolicyRules_parameters = new ES4UpdatePolicyRulesRequest();
        _es4UpdatePolicyRules_parameters.setEid("89001012012341234012345678901224".getBytes());
        System.out.println(_es4UpdatePolicyRules_parameters.getEid());
        _es4UpdatePolicyRules_parameters.setIccid("00");
        
        _es4UpdatePolicyRules_parameters.setFunctionCallIdentifier("02");
        _es4UpdatePolicyRules_parameters.setValidityPeriod(new BigInteger("2"));
        POL2Type pol2Type = new POL2Type();
        pol2Type.setRule(pol2RuleList);
        _es4UpdatePolicyRules_parameters.setPol2(pol2Type);
        com.whty.efs.webservice.es.message.ES4UpdatePolicyRulesResponse _es4UpdatePolicyRules__return = port.es4UpdatePolicyRules(_es4UpdatePolicyRules_parameters);
        System.out.println("es4UpdatePolicyRules.result=" + new Gson().toJson(_es4UpdatePolicyRules__return));
    }
    
    @Test
    public void getEisTest() throws MalformedURLException{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
        System.out.println("Invoking es4GetEis...");
        
        com.whty.efs.webservice.es.message.ES4GetEISRequest _es4GetEIS_parameters = new ES4GetEISRequest();
        _es4GetEIS_parameters.setEid("89001012012341234012345678901224".getBytes());
        _es4GetEIS_parameters.setFunctionCallIdentifier("02");
        _es4GetEIS_parameters.setValidityPeriod(new BigInteger("2"));
        com.whty.efs.webservice.es.message.ES4GetEISResponse _es4GetEIS__return = port.es4GetEIS(_es4GetEIS_parameters);
        System.out.println("ES4GetEIS.result=" + new Gson().toJson(_es4GetEIS__return));
    }
    
    @Test
    public void updateSubscriAddrTest() throws MalformedURLException{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
        System.out.println("Invoking es4UpdateSubscriptionAddress...");
        SubscriptionAddressType subscrAddr = new SubscriptionAddressType();
        subscrAddr.setImsi("123456789");
        subscrAddr.setMsisdn("987654321");
        
        com.whty.efs.webservice.es.message.ES4UpdateSubscriptionAddressRequest _es4UpdateSubscrAddr_parameters = new ES4UpdateSubscriptionAddressRequest();
        _es4UpdateSubscrAddr_parameters.setEid("89001012012341234012345678901224".getBytes());
        _es4UpdateSubscrAddr_parameters.setIccid("00");
        _es4UpdateSubscrAddr_parameters.setNewSubscriptionAddress(subscrAddr);
        _es4UpdateSubscrAddr_parameters.setFunctionCallIdentifier("02");
        _es4UpdateSubscrAddr_parameters.setValidityPeriod(new BigInteger("2"));
        com.whty.efs.webservice.es.message.ES4UpdateSubscriptionAddressResponse _es4UpdateSubscrAddr__return = port.es4UpdateSubscriptionAddress(_es4UpdateSubscrAddr_parameters);
        System.out.println("ES4UpdateSubscriptionAddress.result=" + new Gson().toJson(_es4UpdateSubscrAddr__return));
    }
    
    @Test
    public void deleteProfileTest() throws Exception{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
        System.out.println("Invoking es4DeleteProfile...");
        
        com.whty.efs.webservice.es.message.ES4DeleteProfileRequest _es4DeleteProfile_parameters = new ES4DeleteProfileRequest();
        _es4DeleteProfile_parameters.setEid("89001012012341234012345678901224".getBytes());
        _es4DeleteProfile_parameters.setIccid("00");
        _es4DeleteProfile_parameters.setFunctionCallIdentifier("02");
        _es4DeleteProfile_parameters.setValidityPeriod(new BigInteger("2"));
        com.whty.efs.webservice.es.message.ES4DeleteProfileResponse _es4DeleteProfile__return = port.es4DeleteProfile(_es4DeleteProfile_parameters);
        System.out.println("ES4DeleteProfile.result=" + new Gson().toJson(_es4DeleteProfile__return));
    	
    }
    @Test
    public void smsrChangeTest() throws Exception{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
        System.out.println("Invoking es4SmsrChange...");
        
        com.whty.efs.webservice.es.message.ES4SMSRChangeRequest _es4SMSRChange_parameters = new ES4SMSRChangeRequest();
        _es4SMSRChange_parameters.setEid("89001012012341234012345678901224".getBytes());
        _es4SMSRChange_parameters.setTargetSmsrId("2");
        _es4SMSRChange_parameters.setFunctionCallIdentifier("02");
        _es4SMSRChange_parameters.setValidityPeriod(new BigInteger("2"));
        com.whty.efs.webservice.es.message.ES4SMSRChangeResponse _es4SMSRChange__return = port.es4SMSRChange(_es4SMSRChange_parameters);
        System.out.println("ES4SMSRChange.result=" + new Gson().toJson(_es4SMSRChange__return));
    	
    }
    @Test
    public void prepareSmsrChangeTest() throws Exception{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
        System.out.println("Invoking es4PrepareSmsrChange...");
        
        com.whty.efs.webservice.es.message.ES4PrepareSMSRChangeRequest _es4PrepareSMSRChange_parameters = new ES4PrepareSMSRChangeRequest();
        _es4PrepareSMSRChange_parameters.setEid("89001012012341234012345678901224".getBytes());
        _es4PrepareSMSRChange_parameters.setCurrentSMSRid("1");
        _es4PrepareSMSRChange_parameters.setFunctionCallIdentifier("02");
        _es4PrepareSMSRChange_parameters.setValidityPeriod(new BigInteger("2"));
        com.whty.efs.webservice.es.message.ES4PrepareSMSRChangeResponse _es4PrepareSMSRChange__return = port.es4PrepareSMSRChange(_es4PrepareSMSRChange_parameters);
        System.out.println("ES4PrepareSMSRChange.result=" + new Gson().toJson(_es4PrepareSMSRChange__return));
    	
    }
    @Test
    public void enableProfileTest() throws Exception{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
        System.out.println("Invoking es4EnableProfile...");
        
        com.whty.efs.webservice.es.message.ES4EnableProfileRequest _es4EnableProfile_parameters = new ES4EnableProfileRequest();
        _es4EnableProfile_parameters.setEid("89001012012341234012345678901224".getBytes());
        _es4EnableProfile_parameters.setIccid("00");
        _es4EnableProfile_parameters.setFunctionCallIdentifier("02");
        _es4EnableProfile_parameters.setValidityPeriod(new BigInteger("2"));
        com.whty.efs.webservice.es.message.ES4EnableProfileResponse _es4EnableProfile__return = port.es4EnableProfile(_es4EnableProfile_parameters);
        System.out.println("ES4EnableProfile.result=" + new Gson().toJson(_es4EnableProfile__return));
    	
    }
    @Test
    public void disableProfileTest() throws Exception{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
        System.out.println("Invoking es4DisableProfile...");
        
        com.whty.efs.webservice.es.message.ES4DisableProfileRequest _es4DisableProfile_parameters = new ES4DisableProfileRequest();
        _es4DisableProfile_parameters.setEid("89001012012341234012345678901224".getBytes());
        _es4DisableProfile_parameters.setIccid("00");
        _es4DisableProfile_parameters.setFunctionCallIdentifier("02");
        _es4DisableProfile_parameters.setValidityPeriod(new BigInteger("2"));
        com.whty.efs.webservice.es.message.ES4DisableProfileResponse _es4DisableProfile__return = port.es4DisableProfile(_es4DisableProfile_parameters);
        System.out.println("ES4DisableProfile.result=" + new Gson().toJson(_es4DisableProfile__return));
    	
    }
    @Test
    public void auditEisTest() throws Exception{
    	URL wsdlURL = new URL(_url);
    	ES4MnoService ss = new ES4MnoService(wsdlURL, SERVICE_NAME);
        ES4Mno port = ss.getMnoES4Port();  
    	System.out.println("Invoking es4AuditEIS...");
    	List<String> iccidList = new ArrayList<String>();
    	iccidList.add("00");
    	iccidList.add("01");
        com.whty.efs.webservice.es.message.ES4AuditEISRequest _es4AuditEIS_parameters = new ES4AuditEISRequest();
        _es4AuditEIS_parameters.setEid("89001012012341234012345678901224".getBytes());
        _es4AuditEIS_parameters.setIccid(iccidList);
        com.whty.efs.webservice.es.message.ES4AuditEISResponse _es4AuditEIS__return = port.es4AuditEIS(_es4AuditEIS_parameters);
        System.out.println("es4AuditEIS.result=" + _es4AuditEIS__return);
    	
    }

}
