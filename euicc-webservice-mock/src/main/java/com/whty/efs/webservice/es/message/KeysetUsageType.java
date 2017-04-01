
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>KeysetUsageType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="KeysetUsageType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="SCP03"/&gt;
 *     &lt;enumeration value="SCP80"/&gt;
 *     &lt;enumeration value="SCP81"/&gt;
 *     &lt;enumeration value="TokenGeneration"/&gt;
 *     &lt;enumeration value="ReceiptVerification"/&gt;
 *     &lt;enumeration value="CA"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "KeysetUsageType", namespace = "")
@XmlEnum
public enum KeysetUsageType {


    /**
     * For identifying a keyset for SCP03, as defined in Global Platform Card Specifications.
     * 
     */
    @XmlEnumValue("SCP03")
    SCP_03("SCP03"),

    /**
     * For identifying a keyset for SCP80, as defined in Global Platform and ETSI 102.225.
     * 
     */
    @XmlEnumValue("SCP80")
    SCP_80("SCP80"),

    /**
     * For identifying a keyset for SCP81, as defined in Global Platform Card Specifications.
     * 
     */
    @XmlEnumValue("SCP81")
    SCP_81("SCP81"),

    /**
     * For identifying a keyset for Token generation used in Delegated Management, as defined in Global Platform Card Specifications.
     * 
     */
    @XmlEnumValue("TokenGeneration")
    TOKEN_GENERATION("TokenGeneration"),

    /**
     * For identifying a keyset for Receipt verification used in Delegated Management, as defined in Global Platform Card Specifications.
     * 
     */
    @XmlEnumValue("ReceiptVerification")
    RECEIPT_VERIFICATION("ReceiptVerification"),

    /**
     * For identifying a keyset for Controlling Authority (CA) usage, as defined in Global Platform card - UICC configuration.
     * 
     */
    CA("CA");
    private final String value;

    KeysetUsageType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static KeysetUsageType fromValue(String v) {
        for (KeysetUsageType c: KeysetUsageType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
