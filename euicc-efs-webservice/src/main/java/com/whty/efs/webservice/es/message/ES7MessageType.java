
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ES7-MessageType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="ES7-MessageType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ES7-CreateAdditionalKeySetRequest"/&gt;
 *     &lt;enumeration value="ES7-CreateAdditionalKeySetResponse"/&gt;
 *     &lt;enumeration value="ES7-AuthenticateSMSRRequest"/&gt;
 *     &lt;enumeration value="ES7-AuthenticateSMSRResponse"/&gt;
 *     &lt;enumeration value="ES7-HandoverEUICCRequest"/&gt;
 *     &lt;enumeration value="ES7-HandoverEUICCResponse"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "ES7-MessageType")
@XmlEnum
public enum ES7MessageType {

    @XmlEnumValue("ES7-CreateAdditionalKeySetRequest")
    ES_7_CREATE_ADDITIONAL_KEY_SET_REQUEST("ES7-CreateAdditionalKeySetRequest"),
    @XmlEnumValue("ES7-CreateAdditionalKeySetResponse")
    ES_7_CREATE_ADDITIONAL_KEY_SET_RESPONSE("ES7-CreateAdditionalKeySetResponse"),
    @XmlEnumValue("ES7-AuthenticateSMSRRequest")
    ES_7_AUTHENTICATE_SMSR_REQUEST("ES7-AuthenticateSMSRRequest"),
    @XmlEnumValue("ES7-AuthenticateSMSRResponse")
    ES_7_AUTHENTICATE_SMSR_RESPONSE("ES7-AuthenticateSMSRResponse"),
    @XmlEnumValue("ES7-HandoverEUICCRequest")
    ES_7_HANDOVER_EUICC_REQUEST("ES7-HandoverEUICCRequest"),
    @XmlEnumValue("ES7-HandoverEUICCResponse")
    ES_7_HANDOVER_EUICC_RESPONSE("ES7-HandoverEUICCResponse"),
    @XmlEnumValue("ES7-HandleSMSRChangeNotification")
    ES_7_HANDLE_SMSR_CHANGE_NOTIFICATION("ES7-HandleSMSRChangeNotification");
    private final String value;

    ES7MessageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ES7MessageType fromValue(String v) {
        for (ES7MessageType c: ES7MessageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
