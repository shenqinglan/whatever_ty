
package com.whty.efs.webservice.message;

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class Adapter2
    extends XmlAdapter<String, Date>
{


    public Date unmarshal(String value) {
        return (com.whty.efs.webservice.jaxb.XSDDateCustomBinder.parseDate(value));
    }

    public String marshal(Date value) {
        return (com.whty.efs.webservice.jaxb.XSDDateCustomBinder.printDate(value));
    }

}
