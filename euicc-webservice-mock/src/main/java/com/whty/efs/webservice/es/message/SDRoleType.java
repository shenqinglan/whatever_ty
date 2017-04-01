
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SDRoleType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="SDRoleType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ISD-R"/&gt;
 *     &lt;enumeration value="ECASD"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "SDRoleType", namespace = "")
@XmlEnum
public enum SDRoleType {


    /**
     * Type defined for ISD-R. Indicates a SD allocated to SM-SR role
     * 
     */
    @XmlEnumValue("ISD-R")
    ISD_R("ISD-R"),

    /**
     * Type defined for a eSIM Certificate Authority Security Domain. Indicates ta SD allocated to a ECA role
     * 
     */
    ECASD("ECASD");
    private final String value;

    SDRoleType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SDRoleType fromValue(String v) {
        for (SDRoleType c: SDRoleType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
