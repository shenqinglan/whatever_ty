package gsta.com.packet.message.response.base;

/**
 * @ClassName StatusCodeData
 * @author Administrator
 * @date 2016-12-6 下午3:43:44
 * "required" : ["subjectCode", "reasonCode"]
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class StatusCodeData {
	
	/**
	 * "type" : "string",
	 * "description" : "OID of the subject code"
	 */
	private String subjectCode;
	
	/**
	 * "type" : "string",
	 * "description" : "OID of the reason code"
	 */
	private String reasonCode;
	
	/**
	 * "type" : "string",
	 * "description" : "Textual and human readable explanation"
	 */
	private String message;
	
	/**
	 * "type" : "string",
	 * "description" : "Identifier of the subject"
	 */
	private String subjectIdentifier;
	
	public StatusCodeData() {
		super();
	}
	
	public StatusCodeData(String subjectCode, String reasonCode, String message) {
		super();
		this.subjectCode = subjectCode;
		this.reasonCode = reasonCode;
		this.message = message;
	}

	/**
	 * @param subjectCode
	 * @param reasonCode
	 * @param message
	 * @param subjectIdentifier
	 */
	public StatusCodeData(String subjectCode, String reasonCode,
			String message, String subjectIdentifier) {
		super();
		this.subjectCode = subjectCode;
		this.reasonCode = reasonCode;
		this.message = message;
		this.subjectIdentifier = subjectIdentifier;
	}

	public String getSubjectCode() {
		return subjectCode;
	}
	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}
	public String getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	/**
	 * @return the subjectIdentifier
	 */
	public String getSubjectIdentifier() {
		return subjectIdentifier;
	}
	/**
	 * @param subjectIdentifier the subjectIdentifier to set
	 */
	public void setSubjectIdentifier(String subjectIdentifier) {
		this.subjectIdentifier = subjectIdentifier;
	}
	
}
