package com.whty.smpp.esme.message;
/**
 * @ClassName PduResponse
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public abstract class PduResponse extends Pdu {

    private String resultMessage;
    
    public PduResponse(int commandId, String name) {
        super(commandId, name, false);
    }

    public void setResultMessage(String value) {
        this.resultMessage = value;
    }

    public String getResultMessage() {
        return this.resultMessage;
    }
    
}