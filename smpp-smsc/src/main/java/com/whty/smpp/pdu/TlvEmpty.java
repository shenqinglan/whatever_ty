package com.whty.smpp.pdu;
/**
 * @ClassName TlvEmpty
 * @author Administrator
 * @date 2017-1-23 下午3:23:32
 * @Description TODO(这里用一句话描述这个类的作用)
 */
public class TlvEmpty extends Tlv {
	
	private short tag;
	private short len;
	
	public TlvEmpty(short t, short l) {
		tag = t;
		len = l;
	}
	
	/**
	 * @return Returns the len.
	 */
	public short getLen() {
		return len;
	}
	/**
	 * @param len The len to set.
	 */
	public void setLen(short len) {
		this.len = len;
	}
	/**
	 * @return Returns the tag.
	 */
	public short getTag() {
		return tag;
	}
	/**
	 * @param tag The tag to set.
	 */
	public void setTag(short tag) {
		this.tag = tag;
	}
	
	public String toString() {
		return "tag="+tag+",len="+len;
	}
}