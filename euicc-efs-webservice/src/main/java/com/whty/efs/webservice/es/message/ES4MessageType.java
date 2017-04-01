
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ES4-MessageType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ES4-MessageType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ES4-GetEISRequest"/&gt;
 *     &lt;enumeration value="ES4-GetEISResponse"/&gt;
 *     &lt;enumeration value="ES4-AuditEISRequest"/&gt;
 *     &lt;enumeration value="ES4-AuditEISResponse"/&gt;
 *     &lt;enumeration value="ES4-UpdatePolicyRulesRequest"/&gt;
 *     &lt;enumeration value="ES4-UpdatePolicyRulesResponse"/&gt;
 *     &lt;enumeration value="ES4-UpdateSubscriptionAddressRequest"/&gt;
 *     &lt;enumeration value="ES4-UpdateSubscriptionAddressResponse"/&gt;
 *     &lt;enumeration value="ES4-EnableProfileRequest"/&gt;
 *     &lt;enumeration value="ES4-EnableProfileResponse"/&gt;
 *     &lt;enumeration value="ES4-DisableProfileRequest"/&gt;
 *     &lt;enumeration value="ES4-DisableProfileResponse"/&gt;
 *     &lt;enumeration value="ES4-DeleteProfileRequest"/&gt;
 *     &lt;enumeration value="ES4-DeleteProfileResponse"/&gt;
 *     &lt;enumeration value="ES4-PrepareSMSRChangeRequest"/&gt;
 *     &lt;enumeration value="ES4-PrepareSMSRChangeResponse"/&gt;
 *     &lt;enumeration value="ES4-SMSRChangeRequest"/&gt;
 *     &lt;enumeration value="ES4-SMSRChangeResponse"/&gt;
 *     &lt;enumeration value="ES4-HandleProfileDisabledNotification"/&gt;
 *     &lt;enumeration value="ES4-HandleProfileEnabledNotification"/&gt;
 *     &lt;enumeration value="ES4-HandleProfileDeletedNotification"/&gt;
 *     &lt;enumeration value="ES4-HandleSMSRChangeNotification"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ES4-MessageType")
@XmlEnum
public enum ES4MessageType {

    @XmlEnumValue("ES4-GetEISRequest")
    ES_4_GET_EIS_REQUEST("ES4-GetEISRequest"),
    @XmlEnumValue("ES4-GetEISResponse")
    ES_4_GET_EIS_RESPONSE("ES4-GetEISResponse"),
    @XmlEnumValue("ES4-AuditEISRequest")
    ES_4_AUDIT_EIS_REQUEST("ES4-AuditEISRequest"),
    @XmlEnumValue("ES4-AuditEISResponse")
    ES_4_AUDIT_EIS_RESPONSE("ES4-AuditEISResponse"),
    @XmlEnumValue("ES4-UpdatePolicyRulesRequest")
    ES_4_UPDATE_POLICY_RULES_REQUEST("ES4-UpdatePolicyRulesRequest"),
    @XmlEnumValue("ES4-UpdatePolicyRulesResponse")
    ES_4_UPDATE_POLICY_RULES_RESPONSE("ES4-UpdatePolicyRulesResponse"),
    @XmlEnumValue("ES4-UpdateSubscriptionAddressRequest")
    ES_4_UPDATE_SUBSCRIPTION_ADDRESS_REQUEST("ES4-UpdateSubscriptionAddressRequest"),
    @XmlEnumValue("ES4-UpdateSubscriptionAddressResponse")
    ES_4_UPDATE_SUBSCRIPTION_ADDRESS_RESPONSE("ES4-UpdateSubscriptionAddressResponse"),
    @XmlEnumValue("ES4-EnableProfileRequest")
    ES_4_ENABLE_PROFILE_REQUEST("ES4-EnableProfileRequest"),
    @XmlEnumValue("ES4-EnableProfileResponse")
    ES_4_ENABLE_PROFILE_RESPONSE("ES4-EnableProfileResponse"),
    @XmlEnumValue("ES4-DisableProfileRequest")
    ES_4_DISABLE_PROFILE_REQUEST("ES4-DisableProfileRequest"),
    @XmlEnumValue("ES4-DisableProfileResponse")
    ES_4_DISABLE_PROFILE_RESPONSE("ES4-DisableProfileResponse"),
    @XmlEnumValue("ES4-DeleteProfileRequest")
    ES_4_DELETE_PROFILE_REQUEST("ES4-DeleteProfileRequest"),
    @XmlEnumValue("ES4-DeleteProfileResponse")
    ES_4_DELETE_PROFILE_RESPONSE("ES4-DeleteProfileResponse"),
    @XmlEnumValue("ES4-PrepareSMSRChangeRequest")
    ES_4_PREPARE_SMSR_CHANGE_REQUEST("ES4-PrepareSMSRChangeRequest"),
    @XmlEnumValue("ES4-PrepareSMSRChangeResponse")
    ES_4_PREPARE_SMSR_CHANGE_RESPONSE("ES4-PrepareSMSRChangeResponse"),
    @XmlEnumValue("ES4-SMSRChangeRequest")
    ES_4_SMSR_CHANGE_REQUEST("ES4-SMSRChangeRequest"),
    @XmlEnumValue("ES4-SMSRChangeResponse")
    ES_4_SMSR_CHANGE_RESPONSE("ES4-SMSRChangeResponse"),
    @XmlEnumValue("ES4-HandleProfileDisabledNotification")
    ES_4_HANDLE_PROFILE_DISABLED_NOTIFICATION("ES4-HandleProfileDisabledNotification"),
    @XmlEnumValue("ES4-HandleProfileEnabledNotification")
    ES_4_HANDLE_PROFILE_ENABLED_NOTIFICATION("ES4-HandleProfileEnabledNotification"),
    @XmlEnumValue("ES4-HandleProfileDeletedNotification")
    ES_4_HANDLE_PROFILE_DELETED_NOTIFICATION("ES4-HandleProfileDeletedNotification"),
    @XmlEnumValue("ES4-HandleSMSRChangeNotification")
    ES_4_HANDLE_SMSR_CHANGE_NOTIFICATION("ES4-HandleSMSRChangeNotification");
    private final String value;

    ES4MessageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ES4MessageType fromValue(String v) {
        for (ES4MessageType c: ES4MessageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
