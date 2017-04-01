
package com.whty.efs.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.whty.efs.client package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AppQueryRequest_QNAME = new QName("http://www.tathing.com", "AppQueryRequest");
    private final static QName _AppQueryResponse_QNAME = new QName("http://www.tathing.com", "AppQueryResponse");
    private final static QName _AppPersonialRequest_QNAME = new QName("http://www.tathing.com", "AppPersonialRequest");
    private final static QName _AppPersonialResponse_QNAME = new QName("http://www.tathing.com", "AppPersonialResponse");
    private final static QName _Version_QNAME = new QName("http://www.tathing.com", "version");
    private final static QName _Sender_QNAME = new QName("http://www.tathing.com", "sender");
    private final static QName _Receiver_QNAME = new QName("http://www.tathing.com", "receiver");
    private final static QName _SendTime_QNAME = new QName("http://www.tathing.com", "sendTime");
    private final static QName _MsgType_QNAME = new QName("http://www.tathing.com", "msgType");
    private final static QName _TradeNO_QNAME = new QName("http://www.tathing.com", "tradeNO");
    private final static QName _TradeRefNO_QNAME = new QName("http://www.tathing.com", "tradeRefNO");
    private final static QName _SessionID_QNAME = new QName("http://www.tathing.com", "sessionID");
    private final static QName _DeviceType_QNAME = new QName("http://www.tathing.com", "deviceType");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.whty.efs.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AppQueryRequest }
     * 
     */
    public AppQueryRequest createAppQueryRequest() {
        return new AppQueryRequest();
    }

    /**
     * Create an instance of {@link AppQueryResponse }
     * 
     */
    public AppQueryResponse createAppQueryResponse() {
        return new AppQueryResponse();
    }

    /**
     * Create an instance of {@link AppPersonialRequest }
     * 
     */
    public AppPersonialRequest createAppPersonialRequest() {
        return new AppPersonialRequest();
    }

    /**
     * Create an instance of {@link AppPersonialResponse }
     * 
     */
    public AppPersonialResponse createAppPersonialResponse() {
        return new AppPersonialResponse();
    }

    /**
     * Create an instance of {@link RspnMsg }
     * 
     */
    public RspnMsg createRspnMsg() {
        return new RspnMsg();
    }

    /**
     * Create an instance of {@link Rapdu }
     * 
     */
    public Rapdu createRapdu() {
        return new Rapdu();
    }

    /**
     * Create an instance of {@link Capdu }
     * 
     */
    public Capdu createCapdu() {
        return new Capdu();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AppQueryRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "AppQueryRequest")
    public JAXBElement<AppQueryRequest> createAppQueryRequest(AppQueryRequest value) {
        return new JAXBElement<AppQueryRequest>(_AppQueryRequest_QNAME, AppQueryRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AppQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "AppQueryResponse")
    public JAXBElement<AppQueryResponse> createAppQueryResponse(AppQueryResponse value) {
        return new JAXBElement<AppQueryResponse>(_AppQueryResponse_QNAME, AppQueryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AppPersonialRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "AppPersonialRequest")
    public JAXBElement<AppPersonialRequest> createAppPersonialRequest(AppPersonialRequest value) {
        return new JAXBElement<AppPersonialRequest>(_AppPersonialRequest_QNAME, AppPersonialRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AppPersonialResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "AppPersonialResponse")
    public JAXBElement<AppPersonialResponse> createAppPersonialResponse(AppPersonialResponse value) {
        return new JAXBElement<AppPersonialResponse>(_AppPersonialResponse_QNAME, AppPersonialResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "version")
    public JAXBElement<Integer> createVersion(Integer value) {
        return new JAXBElement<Integer>(_Version_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "sender")
    public JAXBElement<String> createSender(String value) {
        return new JAXBElement<String>(_Sender_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "receiver")
    public JAXBElement<String> createReceiver(String value) {
        return new JAXBElement<String>(_Receiver_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "sendTime")
    public JAXBElement<XMLGregorianCalendar> createSendTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_SendTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "msgType")
    public JAXBElement<String> createMsgType(String value) {
        return new JAXBElement<String>(_MsgType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "tradeNO")
    public JAXBElement<Integer> createTradeNO(Integer value) {
        return new JAXBElement<Integer>(_TradeNO_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "tradeRefNO")
    public JAXBElement<Integer> createTradeRefNO(Integer value) {
        return new JAXBElement<Integer>(_TradeRefNO_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "sessionID")
    public JAXBElement<Integer> createSessionID(Integer value) {
        return new JAXBElement<Integer>(_SessionID_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tathing.com", name = "deviceType")
    public JAXBElement<String> createDeviceType(String value) {
        return new JAXBElement<String>(_DeviceType_QNAME, String.class, null, value);
    }

}
