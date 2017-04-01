package com.whty.smpp.esme.constants;
/**
 * @ClassName Configuration
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class Configuration {

	private String name;
	private String host;
    private int port;
    private long connectTimeout;
    
    private SmppBindType type;
    private String systemId;
    private String password;
    private String systemType;
    private byte interfaceVersion;  
    private long bindTimeout;
    private Address addressRange;
    private int sendInterval;
    
    public Configuration() {
        this(SmppBindType.TRANSCEIVER, null, null, null,1000);
    }

    public Configuration(SmppBindType type, String systemId, String password, int sendInterval) {
        this(type, systemId, password, null, 1000);
    }

	public Configuration(SmppBindType type, String systemId, String password, String systemType, int sendInterval) {
    	this.type = type;
        this.systemId = systemId;
        this.password = password;
        this.systemType = systemType;
        this.sendInterval = sendInterval;
        this.interfaceVersion = SmppConstants.VERSION_3_4;
        this.bindTimeout = SmppConstants.DEFAULT_BIND_TIMEOUT;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the connectTimeout
	 */
	public long getConnectTimeout() {
		return connectTimeout;
	}

	/**
	 * @param connectTimeout the connectTimeout to set
	 */
	public void setConnectTimeout(long connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	/**
	 * @return the type
	 */
	public SmppBindType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(SmppBindType type) {
		this.type = type;
	}

	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}

	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the systemType
	 */
	public String getSystemType() {
		return systemType;
	}

	/**
	 * @param systemType the systemType to set
	 */
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}

	/**
	 * @return the interfaceVersion
	 */
	public byte getInterfaceVersion() {
		return interfaceVersion;
	}

	/**
	 * @param interfaceVersion the interfaceVersion to set
	 */
	public void setInterfaceVersion(byte interfaceVersion) {
		this.interfaceVersion = interfaceVersion;
	}

	/**
	 * @return the bindTimeout
	 */
	public long getBindTimeout() {
		return bindTimeout;
	}

	/**
	 * @param bindTimeout the bindTimeout to set
	 */
	public void setBindTimeout(long bindTimeout) {
		this.bindTimeout = bindTimeout;
	}

	/**
	 * @return the addressRange
	 */
	public Address getAddressRange() {
		return addressRange;
	}

	/**
	 * @param addressRange the addressRange to set
	 */
	public void setAddressRange(Address addressRange) {
		this.addressRange = addressRange;
	}

	/**
	 * @return the sendInterval
	 */
	public int getSendInterval() {
		return sendInterval;
	}

	/**
	 * @param sendInterval the sendInterval to set
	 */
	public void setSendInterval(int sendInterval) {
		this.sendInterval = sendInterval;
	}
}
