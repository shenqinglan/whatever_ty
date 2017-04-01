
package com.whty.efs.webservice.es.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>POL2RuleSubjectType的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * <p>
 * <pre>
 * &lt;simpleType name="POL2RuleSubjectType"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="PROFILE"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "POL2RuleSubjectType", namespace = "")
@XmlEnum
public enum POL2RuleSubjectType {

    PROFILE;

    public String value() {
        return name();
    }

    public static POL2RuleSubjectType fromValue(String v) {
        return valueOf(v);
    }

}
