package com.whty.euicc.packets.thirdpartymsg;

import java.nio.ByteBuffer;

import com.whty.euicc.common.utils.ByteUtil;
import com.whty.euicc.common.utils.Converts;
import com.whty.euicc.packets.message.EuiccMsg;
import com.whty.euicc.packets.message.request.RequestMsgBody;

/**
 * 电子卡包与TSM核心系统约定的报文格式
 * @author dengzm
 *
 */
public class F17Msg {
	/**
	 *  （前缀）1字节二进制码，固定为0x02
	 */
	private byte[] F1;
	/**
	 *  密钥会话标识 1字节BCD码，用于保存Tathing平台为手机客户端分配的对称密钥会话标识 每进行一次交易都需要重新申请该标识
	 */
	private byte[] F2;
	/**
	 *  phoneNum的MD5后的值，用于标示是那个客户端的卡片发上来的请求
	 */
	private byte[] F3;
	/**
	 *  （交易报文长度）4字节BCD码，指示F4域的长度，右对齐，不足补0
	 */
	private byte[] F4;
	/**
	 *  （交易报文）该域用于保存交易报文数据，在密钥交换阶段该域数值为使用登录时返回的会话密钥使用3des算法加密后的数据
	 */
	private byte[] F5;
	/**
	 *  （校验）1字节二进制校验码，对F1,F2,F3，F4及F5域进行校验，采用异或和校验方式
	 */
	private byte[] F6;
	/**
	 *  （后缀）1字节二进制码，固定为0x03
	 */
	private byte[] F7;
	
	/**
	 * 请求报文体
	 */
	private EuiccMsg<RequestMsgBody> reqMsg;
	
	public byte[] getF1() {
		return F1;
	}

	public void setF1(byte[] f1) {
		F1 = f1;
	}

	public byte[] getF2() {
		return F2;
	}

	public void setF2(byte[] f2) {
		F2 = f2;
	}

	public byte[] getF3() {
		return F3;
	}

	public void setF3(byte[] f3) {
		F3 = f3;
	}

	public byte[] getF4() {
		return F4;
	}

	private void setF4(byte[] f4) {
		F4 = f4;
	}

	public byte[] getF5() {
		return F5;
	}

	public void setF5(byte[] f5) {
		F5 = f5;
		// 设置F4域
		byte[] f4 = Converts.intToString(f5.length/2, 8).getBytes();
		this.setF4(f4);
		// 设置F6域
		int clcLengh = 2+2+32+8+f5.length;
		ByteBuffer bb = ByteBuffer.allocate(clcLengh);
		bb.put(F1);
		bb.put(F2);
		bb.put(F3);
		bb.put(F4);
		bb.put(F5);
		String a=new String(F1)+new String(F2)+new String(F3)+new String(F4)+new String(F5);
		byte t = ByteUtil.calcLRC(Converts.stringToBytes(a));
		this.setF6(Converts.bytesToHex(new byte[] { t }).getBytes());
//		this.setF6(ByteUtil.byteToHex(ByteUtil.calcLRC(bb.array())));
	}
	
	public static void main(String[] args) {
		String data="0202A80A1E77F5EB318A60033422D23A5074000005737b22686561646572223a7b2276657273696f6e223a2231222c2273656e6454696d65223a22323031352d30322d32382030363a35323a3437222c226d736754797065223a2270686f6e652e3030322e3030312e3031222c2274726164654e4f223a223262643764663837303265363163373630366431333431353266613964313232222c2274726164655265664e4f223a223262643764663837303265363163373630366431333431353266613964313232222c2264657669636554797065223a2232227d2c22626f6479223a7b22726573756c74223a223030222c2272616e646f6d223a2234453941453036443430454438383346313843383838304531454437434438363130434330303435443635344239303237423739363442314546444231394335324644364443314536324630433438443031383642313731464337424331353735383636423843444531453243393742433232393245354430313043444142463937333245393036463238364331363034453832434433364342383632394333393631373535454638414431454243323330303843313143323541363845363542323943464439433039304346413830313833463433313337434130354241393736343436374433374446413942433634453630343330304245373135423631222c226e616d65223a22616161222c22696454797065223a223031222c2269644e756d626572223a22616161222c227273706e4d7367223a7b22737473223a2230303030222c22656e64466c6167223a223030227d7d7d";
		byte[] b=Converts.stringToBytes(data);
		@SuppressWarnings("unused")
		byte a=ByteUtil.calcLRC(b);
		byte[] b1=new byte[2];
		b1[0]=48;
		b1[1]=50;
		System.out.println(new String(b1));
		
	}

	public byte[] getF6() {
		return F6;
	}

	private void setF6(byte[] f6) {
		F6 = f6;
	}

	public byte[] getF7() {
		return F7;
	}

	public void setF7(byte[] f7) {
		F7 = f7;
	}
	
	public byte[] toBytes(byte[] f5){
		// 设置F5域
		setF5(f5);
		//
		int clcLengh = 2+2+32+8+F5.length+2+2;
		ByteBuffer bb = ByteBuffer.allocate(clcLengh);
		bb.put(F1);
		bb.put(F2);
		bb.put(F3);
		bb.put(F4);
		bb.put(F5);
		bb.put(F6);
		bb.put(F7);
		return bb.array();
	}
	

	public EuiccMsg<RequestMsgBody> getReqMsg() {
		return reqMsg;
	}

	public void setReqMsg(EuiccMsg<RequestMsgBody> reqMsg) {
		this.reqMsg = reqMsg;
	}
}
