
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ES3-MessageType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ES3-MessageType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ES3-GetEISRequest"/&gt;
 *     &lt;enumeration value="ES3-GetEISResponse"/&gt;
 *     &lt;enumeration value="ES3-AuditEISRequest"/&gt;
 *     &lt;enumeration value="ES3-AuditEISResponse"/&gt;
 *     &lt;enumeration value="ES3-CreateISDPRequest"/&gt;
 *     &lt;enumeration value="ES3-CreateISDPResponse"/&gt;
 *     &lt;enumeration value="ES3-SendDataRequest"/&gt;
 *     &lt;enumeration value="ES3-SendDataResponse"/&gt;
 *     &lt;enumeration value="ES3-ProfileDownloadCompletedRequest"/&gt;
 *     &lt;enumeration value="ES3-ProfileDownloadCompletedResponse"/&gt;
 *     &lt;enumeration value="ES3-UpdatePolicyRulesRequest"/&gt;
 *     &lt;enumeration value="ES3-UpdatePolicyRulesResponse"/&gt;
 *     &lt;enumeration value="ES3-UpdateSubscriptionAddressRequest"/&gt;
 *     &lt;enumeration value="ES3-UpdateSubscriptionAddressResponse"/&gt;
 *     &lt;enumeration value="ES3-EnableProfileRequest"/&gt;
 *     &lt;enumeration value="ES3-EnableProfileResponse"/&gt;
 *     &lt;enumeration value="ES3-DisableProfileRequest"/&gt;
 *     &lt;enumeration value="ES3-DisableProfileResponse"/&gt;
 *     &lt;enumeration value="ES3-DeleteISDPRequest"/&gt;
 *     &lt;enumeration value="ES3-DeleteISDPResponse"/&gt;
 *     &lt;enumeration value="ES3-HandleProfileDisabledNotification"/&gt;
 *     &lt;enumeration value="ES3-HandleProfileEnabledNotification"/&gt;
 *     &lt;enumeration value="ES3-HandleProfileDeletedNotification"/&gt;
 *     &lt;enumeration value="ES3-HandleSMSRChangeNotification"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ES3-MessageType")
@XmlEnum
public enum ES3MessageType {

    @XmlEnumValue("ES3-GetEISRequest")
    ES_3_GET_EIS_REQUEST("ES3-GetEISRequest"),
    @XmlEnumValue("ES3-GetEISResponse")
    ES_3_GET_EIS_RESPONSE("ES3-GetEISResponse"),
    @XmlEnumValue("ES3-AuditEISRequest")
    ES_3_AUDIT_EIS_REQUEST("ES3-AuditEISRequest"),
    @XmlEnumValue("ES3-AuditEISResponse")
    ES_3_AUDIT_EIS_RESPONSE("ES3-AuditEISResponse"),
    @XmlEnumValue("ES3-CreateISDPRequest")
    ES_3_CREATE_ISDP_REQUEST("ES3-CreateISDPRequest"),
    @XmlEnumValue("ES3-CreateISDPResponse")
    ES_3_CREATE_ISDP_RESPONSE("ES3-CreateISDPResponse"),
    @XmlEnumValue("ES3-SendDataRequest")
    ES_3_SEND_DATA_REQUEST("ES3-SendDataRequest"),
    @XmlEnumValue("ES3-SendDataResponse")
    ES_3_SEND_DATA_RESPONSE("ES3-SendDataResponse"),
    @XmlEnumValue("ES3-ProfileDownloadCompletedRequest")
    ES_3_PROFILE_DOWNLOAD_COMPLETED_REQUEST("ES3-ProfileDownloadCompletedRequest"),
    @XmlEnumValue("ES3-ProfileDownloadCompletedResponse")
    ES_3_PROFILE_DOWNLOAD_COMPLETED_RESPONSE("ES3-ProfileDownloadCompletedResponse"),
    @XmlEnumValue("ES3-UpdatePolicyRulesRequest")
    ES_3_UPDATE_POLICY_RULES_REQUEST("ES3-UpdatePolicyRulesRequest"),
    @XmlEnumValue("ES3-UpdatePolicyRulesResponse")
    ES_3_UPDATE_POLICY_RULES_RESPONSE("ES3-UpdatePolicyRulesResponse"),
    @XmlEnumValue("ES3-UpdateSubscriptionAddressRequest")
    ES_3_UPDATE_SUBSCRIPTION_ADDRESS_REQUEST("ES3-UpdateSubscriptionAddressRequest"),
    @XmlEnumValue("ES3-UpdateSubscriptionAddressResponse")
    ES_3_UPDATE_SUBSCRIPTION_ADDRESS_RESPONSE("ES3-UpdateSubscriptionAddressResponse"),
    @XmlEnumValue("ES3-EnableProfileRequest")
    ES_3_ENABLE_PROFILE_REQUEST("ES3-EnableProfileRequest"),
    @XmlEnumValue("ES3-EnableProfileResponse")
    ES_3_ENABLE_PROFILE_RESPONSE("ES3-EnableProfileResponse"),
    @XmlEnumValue("ES3-DisableProfileRequest")
    ES_3_DISABLE_PROFILE_REQUEST("ES3-DisableProfileRequest"),
    @XmlEnumValue("ES3-DisableProfileResponse")
    ES_3_DISABLE_PROFILE_RESPONSE("ES3-DisableProfileResponse"),
    @XmlEnumValue("ES3-DeleteISDPRequest")
    ES_3_DELETE_ISDP_REQUEST("ES3-DeleteISDPRequest"),
    @XmlEnumValue("ES3-DeleteISDPResponse")
    ES_3_DELETE_ISDP_RESPONSE("ES3-DeleteISDPResponse"),
    @XmlEnumValue("ES3-HandleProfileDisabledNotification")
    ES_3_HANDLE_PROFILE_DISABLED_NOTIFICATION("ES3-HandleProfileDisabledNotification"),
    @XmlEnumValue("ES3-HandleProfileEnabledNotification")
    ES_3_HANDLE_PROFILE_ENABLED_NOTIFICATION("ES3-HandleProfileEnabledNotification"),
    @XmlEnumValue("ES3-HandleProfileDeletedNotification")
    ES_3_HANDLE_PROFILE_DELETED_NOTIFICATION("ES3-HandleProfileDeletedNotification"),
    @XmlEnumValue("ES3-HandleSMSRChangeNotification")
    ES_3_HANDLE_SMSR_CHANGE_NOTIFICATION("ES3-HandleSMSRChangeNotification");
    private final String value;

    ES3MessageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ES3MessageType fromValue(String v) {
        for (ES3MessageType c: ES3MessageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
