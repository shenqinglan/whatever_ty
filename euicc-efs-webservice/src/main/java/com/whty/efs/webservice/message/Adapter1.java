
package com.whty.efs.webservice.message;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter1
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (com.whty.efs.webservice.jaxb.XSDDateTimeCustomBinder.parseDateTime(value));
    }

    public String marshal(Date value) {
        return (com.whty.efs.webservice.jaxb.XSDDateTimeCustomBinder.printDateTime(value));
    }

}
