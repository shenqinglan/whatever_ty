package com.whty.smpp.pdu;

import java.io.Serializable;
/**
 * @ClassName Pdu
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
abstract public class Pdu implements Serializable {

	/**
	 * @Fields serialVersionUID TODO(用一句话描述这个变量表示什么) 
	 */
	private static final long serialVersionUID = 1L;

	public Pdu() {
		
	}
	// pdu消息头
	private int cmd_len;
	private int cmd_id;
	private int cmd_status;
	private int seq_no;

	public boolean equals(Object o) {
		if (o instanceof Pdu) {
			Pdu p = (Pdu) o;
			if (p.getSeq_no() == this.seq_no)
				return true;
		}
		return false;
	}
	
	/**
	 * @return
	 */
	public int getCmd_id() {
		return cmd_id;
	}

	/**
	 * @return
	 */
	public int getCmd_len() {
		return cmd_len;
	}

	/**
	 * @return
	 */
	public int getCmd_status() {
		return cmd_status;
	}

	/**
	 * @return
	 */
	public int getSeq_no() {
		return seq_no;
	}

	/**
	 * @param i
	 */
	public void setCmd_id(int i) {
		cmd_id = i;
	}

	/**
	 * @param i
	 */
	public void setCmd_len(int i) {
		cmd_len = i;
	}

	/**
	 * @param i
	 */
	public void setCmd_status(int i) {
		cmd_status = i;
	}

	/**
	 * @param i
	 */
	public void setSeq_no(int i) {
		seq_no = i;
	}
	
	public String toString() {
		return "cmd_len="+cmd_len+","+
		"cmd_id="+cmd_id+","+
		"cmd_status="+cmd_status+","+
		"seq_no="+seq_no;
	}
}