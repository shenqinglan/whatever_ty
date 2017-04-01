// Copyright (C) 2012 WHTY
package com.whty.euicc.data.pojo;

import java.io.Serializable ;
import java.io.UnsupportedEncodingException ;
import java.util.Date ;
import java.util.Map ;

public class BusinessLog implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -9040362985372687363L ;

	/**
     * SEQ_BUS_LOG
     */
    private Long id;

    private Long businessId;

    private String xmlId;

    private String step;

    /**
     * 系统参数表维护，系统参数类别名称TSM_BUS_COMMAND<br>
     * 0：命令类型正确，1：命令类型错误<br>
     *
     */
    private String result;

    private String message;

    private Date excuteDate;

    /**
     * JSON格式（尽量不要存放后续步骤没有用到的参数，每个步骤以及原子任务需要存放的参数在步骤和原子任务说明处需要定义好）
     */
    private byte[] param;

    private String paramString;

    private Map<String, Object> paramMap;
    private String apdu;

    private String stepDesc;


    public String getStepDesc() {
        return stepDesc;
    }

    public void setStepDesc(String stepDesc) {
        this.stepDesc = stepDesc;
    }

    /**
     * @return SEQ_BUS_LOG
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            SEQ_BUS_LOG
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Long getBusinessId() {
        return businessId;
    }

    public void setBusinessId(Long businessId) {
        this.businessId = businessId;
    }

    public String getXmlId() {
        return xmlId;
    }

    public void setXmlId(String xmlId) {
        this.xmlId = xmlId;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    /**
     * @return 系统参数表维护，系统参数类别名称TSM_BUS_COMMAND<br>
     *         0：命令类型正确，1：命令类型错误<br>
     *
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result
     *            系统参数表维护，系统参数类别名称TSM_BUS_COMMAND<br>
     *            0：命令类型正确，1：命令类型错误<br>
     *
     */
    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getExcuteDate() {
        return excuteDate;
    }

    public void setExcuteDate(Date excuteDate) {
        this.excuteDate = excuteDate;
    }

    /**
     * @return JSON格式（尽量不要存放后续步骤没有用到的参数，每个步骤以及原子任务需要存放的参数在步骤和原子任务说明处需要定义好）
     */
    public byte[] getParam() {
        return param;
    }

    /**
     * @param param
     *            JSON格式（尽量不要存放后续步骤没有用到的参数，每个步骤以及原子任务需要存放的参数在步骤和原子任务说明处需要定义好）
     */
    public void setParam(byte[] param) {
//        this.param = new byte[param.length];
//        for(int i = 0 ; i < param.length; i++){
//    		this.param[i] = param[i];
//    	}
    	this.param = param;
    }

    public String getParamString() {
        try {
            paramString = new String(param, "GBK");
        }
        catch (UnsupportedEncodingException e) {
            return new String(param);
        }
        paramString = paramString.replace("\"", "'");
        return paramString;
    }

    public void setParamString(String paramString) {
        this.paramString = paramString;
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
      }

    public void setParamMap(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
      }

	public String getApdu() {
		return apdu;
	}

	public void setApdu(String apdu) {
		this.apdu = apdu;
	}

}
