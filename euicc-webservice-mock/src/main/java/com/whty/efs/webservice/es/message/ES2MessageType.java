
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ES2-MessageType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ES2-MessageType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ES2-DeleteProfileRequest"/&gt;
 *     &lt;enumeration value="ES2-DeleteProfileResponse"/&gt;
 *     &lt;enumeration value="ES2-GetEISRequest"/&gt;
 *     &lt;enumeration value="ES2-GetEISResponse"/&gt;
 *     &lt;enumeration value="ES2-DownloadProfileRequest"/&gt;
 *     &lt;enumeration value="ES2-DownloadProfileResponse"/&gt;
 *     &lt;enumeration value="ES2-UpdatePolicyRequest"/&gt;
 *     &lt;enumeration value="ES2-UpdatePolicyResponse"/&gt;
 *     &lt;enumeration value="ES2-EnableProfileRequest"/&gt;
 *     &lt;enumeration value="ES2-EnableProfileResponse"/&gt;
 *     &lt;enumeration value="ES2-DisableProfileRequest"/&gt;
 *     &lt;enumeration value="ES2-DisableProfileResponse"/&gt;
 *     &lt;enumeration value="ES2-AuditEISRequest"/&gt;
 *     &lt;enumeration value="ES2-AuditEISResponse"/&gt;
 *     &lt;enumeration value="ES2-UpdateSubscriptionAddressRequest"/&gt;
 *     &lt;enumeration value="ES2-UpdateSubscriptionAddressResponse"/&gt;
 *     &lt;enumeration value="ES2-HandleProfileDisabledNotification"/&gt;
 *     &lt;enumeration value="ES2-HandleProfileEnabledNotification"/&gt;
 *     &lt;enumeration value="ES2-HandleProfileDeletedNotification"/&gt;
 *     &lt;enumeration value="ES2-HandleSMSRChangeNotification"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ES2-MessageType")
@XmlEnum
public enum ES2MessageType {

    @XmlEnumValue("ES2-DeleteProfileRequest")
    ES_2_DELETE_PROFILE_REQUEST("ES2-DeleteProfileRequest"),
    @XmlEnumValue("ES2-DeleteProfileResponse")
    ES_2_DELETE_PROFILE_RESPONSE("ES2-DeleteProfileResponse"),
    @XmlEnumValue("ES2-GetEISRequest")
    ES_2_GET_EIS_REQUEST("ES2-GetEISRequest"),
    @XmlEnumValue("ES2-GetEISResponse")
    ES_2_GET_EIS_RESPONSE("ES2-GetEISResponse"),
    @XmlEnumValue("ES2-DownloadProfileRequest")
    ES_2_DOWNLOAD_PROFILE_REQUEST("ES2-DownloadProfileRequest"),
    @XmlEnumValue("ES2-DownloadProfileResponse")
    ES_2_DOWNLOAD_PROFILE_RESPONSE("ES2-DownloadProfileResponse"),
    @XmlEnumValue("ES2-UpdatePolicyRequest")
    ES_2_UPDATE_POLICY_REQUEST("ES2-UpdatePolicyRequest"),
    @XmlEnumValue("ES2-UpdatePolicyResponse")
    ES_2_UPDATE_POLICY_RESPONSE("ES2-UpdatePolicyResponse"),
    @XmlEnumValue("ES2-EnableProfileRequest")
    ES_2_ENABLE_PROFILE_REQUEST("ES2-EnableProfileRequest"),
    @XmlEnumValue("ES2-EnableProfileResponse")
    ES_2_ENABLE_PROFILE_RESPONSE("ES2-EnableProfileResponse"),
    @XmlEnumValue("ES2-DisableProfileRequest")
    ES_2_DISABLE_PROFILE_REQUEST("ES2-DisableProfileRequest"),
    @XmlEnumValue("ES2-DisableProfileResponse")
    ES_2_DISABLE_PROFILE_RESPONSE("ES2-DisableProfileResponse"),
    @XmlEnumValue("ES2-AuditEISRequest")
    ES_2_AUDIT_EIS_REQUEST("ES2-AuditEISRequest"),
    @XmlEnumValue("ES2-AuditEISResponse")
    ES_2_AUDIT_EIS_RESPONSE("ES2-AuditEISResponse"),
    @XmlEnumValue("ES2-UpdateSubscriptionAddressRequest")
    ES_2_UPDATE_SUBSCRIPTION_ADDRESS_REQUEST("ES2-UpdateSubscriptionAddressRequest"),
    @XmlEnumValue("ES2-UpdateSubscriptionAddressResponse")
    ES_2_UPDATE_SUBSCRIPTION_ADDRESS_RESPONSE("ES2-UpdateSubscriptionAddressResponse"),
    @XmlEnumValue("ES2-HandleProfileDisabledNotification")
    ES_2_HANDLE_PROFILE_DISABLED_NOTIFICATION("ES2-HandleProfileDisabledNotification"),
    @XmlEnumValue("ES2-HandleProfileEnabledNotification")
    ES_2_HANDLE_PROFILE_ENABLED_NOTIFICATION("ES2-HandleProfileEnabledNotification"),
    @XmlEnumValue("ES2-HandleProfileDeletedNotification")
    ES_2_HANDLE_PROFILE_DELETED_NOTIFICATION("ES2-HandleProfileDeletedNotification"),
    @XmlEnumValue("ES2-HandleSMSRChangeNotification")
    ES_2_HANDLE_SMSR_CHANGE_NOTIFICATION("ES2-HandleSMSRChangeNotification");
    private final String value;

    ES2MessageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ES2MessageType fromValue(String v) {
        for (ES2MessageType c: ES2MessageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
