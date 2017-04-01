package com.whty.smpp.socket.message;
/**
 * 
 * @ClassName PduResponse
 * @author Administrator
 * @date 2017-3-10 下午1:46:03
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