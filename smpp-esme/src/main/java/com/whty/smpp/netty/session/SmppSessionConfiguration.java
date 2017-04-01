package com.whty.smpp.netty.session;

import com.whty.smpp.netty.constants.Address;
import com.whty.smpp.netty.constants.SmppBindType;
import com.whty.smpp.netty.constants.SmppConstants;
/**
 * @ClassName SmppSessionConfiguration
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class SmppSessionConfiguration {
	private String name;
	private String host;
    private int port;
    private long connectTimeout;    // 连接超时
    
    private SmppBindType type;
    private String systemId;
    private String password;
    private String systemType;
    private byte interfaceVersion;  // interface version requested by us or them
    private long bindTimeout;       // length of time to wait for a bind response
    
    private int windowSize;
    private long windowWaitTimeout;
    
    // if > 0, then activated
    private long requestExpiryTimeout;
    private long windowMonitorInterval;
   
    private Address addressRange;
    
    public SmppSessionConfiguration() {
        this(SmppBindType.TRANSCEIVER, null, null, null);
    }

    public SmppSessionConfiguration(SmppBindType type, String systemId, String password) {
        this(type, systemId, password, null);
    }

	public SmppSessionConfiguration(SmppBindType type, String systemId, String password, String systemType) {
    	this.type = type;
        this.systemId = systemId;
        this.password = password;
        this.systemType = systemType;
        this.interfaceVersion = SmppConstants.VERSION_3_4;
        this.bindTimeout = SmppConstants.DEFAULT_BIND_TIMEOUT;
        this.windowSize = SmppConstants.DEFAULT_WINDOW_SIZE;
        this.windowWaitTimeout = SmppConstants.DEFAULT_WINDOW_WAIT_TIMEOUT;
        this.requestExpiryTimeout = SmppConstants.DEFAULT_REQUEST_EXPIRY_TIMEOUT;
        this.windowMonitorInterval = SmppConstants.DEFAULT_WINDOW_MONITOR_INTERVAL;
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



	public void setHost(String value) {
        this.host = value;
    }

    public String getHost() {
        return this.host;
    }

    public void setPort(int value) {
        this.port = value;
    }

    public int getPort() {
        return this.port;
    }

    public void setConnectTimeout(long value) {
        this.connectTimeout = value;
    }

    public long getConnectTimeout() {
        return this.connectTimeout;
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
	 * @return the windowWaitTimeout
	 */
	public long getWindowWaitTimeout() {
		return windowWaitTimeout;
	}

	/**
	 * @param windowWaitTimeout the windowWaitTimeout to set
	 */
	public void setWindowWaitTimeout(long windowWaitTimeout) {
		this.windowWaitTimeout = windowWaitTimeout;
	}

	/**
	 * @return the windowMonitorInterval
	 */
	public long getWindowMonitorInterval() {
		return windowMonitorInterval;
	}

	/**
	 * @param windowMonitorInterval the windowMonitorInterval to set
	 */
	public void setWindowMonitorInterval(long windowMonitorInterval) {
		this.windowMonitorInterval = windowMonitorInterval;
	}
	
	/**
	 * @return the requestExpiryTimeout
	 */
	public long getRequestExpiryTimeout() {
		return requestExpiryTimeout;
	}

	/**
	 * @param requestExpiryTimeout the requestExpiryTimeout to set
	 */
	public void setRequestExpiryTimeout(long requestExpiryTimeout) {
		this.requestExpiryTimeout = requestExpiryTimeout;
	}

	/**
	 * @return the windowSize
	 */
	public int getWindowSize() {
		return windowSize;
	}

	/**
	 * @param windowSize the windowSize to set
	 */
	public void setWindowSize(int windowSize) {
		this.windowSize = windowSize;
	}
}
