
package com.whty.efs.webservice.es.message;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;extension base="{}BaseRequestType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Eid" type="{}EIDType"/&gt;
 *         &lt;element name="KeyVersionNumber" type="{}KeysetVersionType"/&gt;
 *         &lt;element name="InitialSequenceCounter" type="{http://www.w3.org/2001/XMLSchema}integer"/&gt;
 *         &lt;element name="ECCKeyLength" type="{}ECCKeyLengthType"/&gt;
 *         &lt;element name="ScenarioParameter" type="{http://www.w3.org/2001/XMLSchema}byte"/&gt;
 *         &lt;element name="HostId" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="EphemeralPublicKey" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *         &lt;element name="Signature" type="{http://www.w3.org/2001/XMLSchema}hexBinary"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/extension&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "eid",
    "keyVersionNumber",
    "initialSequenceCounter",
    "eccKeyLength",
    "scenarioParameter",
    "hostId",
    "ephemeralPublicKey",
    "signature"
})
@XmlRootElement(name = "ES7-CreateAdditionalKeySetRequest")
public class ES7CreateAdditionalKeySetRequest
    extends BaseRequestType
{

    @XmlElement(name = "Eid", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] eid;
    @XmlElement(name = "KeyVersionNumber")
    @XmlSchemaType(name = "integer")
    protected int keyVersionNumber;
    @XmlElement(name = "InitialSequenceCounter", required = true)
    protected BigInteger initialSequenceCounter;
    @XmlElement(name = "ECCKeyLength", required = true)
    @XmlSchemaType(name = "string")
    protected ECCKeyLengthType eccKeyLength;
    @XmlElement(name = "ScenarioParameter")
    protected byte scenarioParameter;
    @XmlElement(name = "HostId", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] hostId;
    @XmlElement(name = "EphemeralPublicKey", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] ephemeralPublicKey;
    @XmlElement(name = "Signature", required = true, type = String.class)
    @XmlJavaTypeAdapter(HexBinaryAdapter.class)
    @XmlSchemaType(name = "hexBinary")
    protected byte[] signature;

    /**
     * 获取eid属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getEid() {
        return eid;
    }

    /**
     * 设置eid属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEid(byte[] value) {
        this.eid = value;
    }

    /**
     * 获取keyVersionNumber属性的值。
     * 
     */
    public int getKeyVersionNumber() {
        return keyVersionNumber;
    }

    /**
     * 设置keyVersionNumber属性的值。
     * 
     */
    public void setKeyVersionNumber(int value) {
        this.keyVersionNumber = value;
    }

    /**
     * 获取initialSequenceCounter属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getInitialSequenceCounter() {
        return initialSequenceCounter;
    }

    /**
     * 设置initialSequenceCounter属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setInitialSequenceCounter(BigInteger value) {
        this.initialSequenceCounter = value;
    }

    /**
     * 获取eccKeyLength属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ECCKeyLengthType }
     *     
     */
    public ECCKeyLengthType getECCKeyLength() {
        return eccKeyLength;
    }

    /**
     * 设置eccKeyLength属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ECCKeyLengthType }
     *     
     */
    public void setECCKeyLength(ECCKeyLengthType value) {
        this.eccKeyLength = value;
    }

    /**
     * 获取scenarioParameter属性的值。
     * 
     */
    public byte getScenarioParameter() {
        return scenarioParameter;
    }

    /**
     * 设置scenarioParameter属性的值。
     * 
     */
    public void setScenarioParameter(byte value) {
        this.scenarioParameter = value;
    }

    /**
     * 获取hostId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getHostId() {
        return hostId;
    }

    /**
     * 设置hostId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostId(byte[] value) {
        this.hostId = value;
    }

    /**
     * 获取ephemeralPublicKey属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getEphemeralPublicKey() {
        return ephemeralPublicKey;
    }

    /**
     * 设置ephemeralPublicKey属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEphemeralPublicKey(byte[] value) {
        this.ephemeralPublicKey = value;
    }

    /**
     * 获取signature属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public byte[] getSignature() {
        return signature;
    }

    /**
     * 设置signature属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSignature(byte[] value) {
        this.signature = value;
    }

	public ECCKeyLengthType getEccKeyLength() {
		return eccKeyLength;
	}

	public void setEccKeyLength(ECCKeyLengthType eccKeyLength) {
		this.eccKeyLength = eccKeyLength;
	}

}
