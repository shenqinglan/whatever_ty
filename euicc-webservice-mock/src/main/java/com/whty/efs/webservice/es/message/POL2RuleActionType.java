
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>POL2RuleActionType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="POL2RuleActionType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="ENABLE"/&gt;
 *     &lt;enumeration value="DISABLE"/&gt;
 *     &lt;enumeration value="DELETE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "POL2RuleActionType")
@XmlEnum
public enum POL2RuleActionType {

    ENABLE,
    DISABLE,
    DELETE;

    public String value() {
        return name();
    }

    public static POL2RuleActionType fromValue(String v) {
        return valueOf(v);
    }

}
