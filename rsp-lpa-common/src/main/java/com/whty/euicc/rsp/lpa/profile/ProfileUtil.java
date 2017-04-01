package com.whty.euicc.rsp.lpa.profile;

import java.util.ArrayList;

import com.whty.euicc.rsp.lpa.profile.profileBean.TLVBean;

/**
 * 生成Segmented Bound Profile Package（未分包）
 * 
 * @author Administrator
 */

public class ProfileUtil {
	/**
	 * 生成SBPP
	 * @param BPP
	 * @return
	 */
	public ArrayList<String[]> generateSBPP(String BPP){
		if(!BPP.substring(0, 4).equals("BF36")){
			throw new RuntimeException("Incorrect format in BPP");
		}
		//动态数组整合各部分String数组
		ArrayList<String[]> array = new ArrayList<String[]>();
		int endOfSeg = 0;//分段标志点
		String[] str1 = new String[1];
		str1[0] = headAndInitChannel(BPP);
		array.add(str1);
		endOfSeg += str1[0].length();
		String[] str2 = new String[1];
		str2[0] = configIsdp(BPP.substring(endOfSeg));
		array.add(str2);
		endOfSeg += str2[0].length();
		String[] str3 = metaData(BPP.substring(endOfSeg));
		array.add(str3);
		for(int i=0;i<str3.length;i++){
			endOfSeg += str3[i].length();
		}
		int beginOfSeg = BPP.substring(endOfSeg).indexOf("A2");
		//判断tag是否为“A2”，含有则说明有PPK
		if(beginOfSeg == 0){
			String[] str4 = new String[1];
			str4[0] = ppk(BPP.substring(endOfSeg));
			array.add(str4);
			endOfSeg += str4[0].length();
		}
		String[] str5 = ppp(BPP.substring(endOfSeg));
		array.add(str5);
		return array;
	}
	
	/**
	 * 拆包出 BPP头 和 initialiseSecureChannel部分
	 * @param data
	 * @return
	 */
	private String headAndInitChannel(String data) {
		//先找head
		//判断tag是否为“BF36”
		int beginOfSeg = data.indexOf("BF36");
		if(!(beginOfSeg == 0)){
			throw new RuntimeException("Incorrect format in  BPP");
		}
		data += "FFFF";//增加endTag
		TLVBean tlv = null;
		//判断该字符串是否符合TLV结构
		tlv = selectTlv(data, "BF36", "FFFF");
		if(tlv == null){
			throw new RuntimeException("Incorrect format in  BPP");
		}
		String head = tlv.getTaglen();
		data = tlv.getValue();
		//再找InitChannel
		beginOfSeg = data.indexOf("BF23");
		if(!(beginOfSeg == 0)){
			throw new RuntimeException("Incorrect format in InitialiseSecureChannel of BPP");
		}
		tlv = null;
		//判断该字符串是否符合TLV结构
		tlv = selectTlv(data, "BF23", "A0");
		if(tlv == null){
			throw new RuntimeException("Incorrect format in InitialiseSecureChannel of BPP");
		}
		String initChannel = tlv.getTaglen() + tlv.getValue();
		System.out.println("headAndInitChannel: " + head + initChannel);
		//对字符串进行限定长度的分包
		return head + initChannel;
	}
	
	/**
	 * 拆包出 configureISDP部分
	 * @param data
	 * @return
	 */
	private String configIsdp(String data) {
		int beginOfSeg = data.indexOf("A0");
		//判断tag是否为“A0”
		if(!(beginOfSeg == 0)){
			throw new RuntimeException("Incorrect format in configIsdp of BPP");
		}
		String str = null;
		TLVBean tlv = null;
		//判断该字符串是否符合TLV结构
		tlv = selectTlv(data, "A0", "A1");
		if(tlv == null){
			throw new RuntimeException("Incorrect format in configIsdp of BPP");
		}
		str = tlv.getTaglen() + tlv.getValue();
		System.out.println("configIsdp: " + str);
		//对字符串进行限定长度的分包
		return str;
	}
	
	
	/**
	 * 拆包出 metaData部分
	 * @param data
	 * @return
	 */
	private String[] metaData(String data){
		//先找head of metaData 
		//判断tag是否为“A1”
		int beginOfSeg = data.indexOf("A1");
		if(!(beginOfSeg == 0)){
			throw new RuntimeException("Incorrect format in head of metaData");
		}
		TLVBean tlv = null;
		//判断该字符串是否符合TLV结构
		tlv = selectTlv(data, "A1", "A2");
		if(tlv == null){//可能是没有PPK
			tlv = selectTlv(data, "A1", "A3");
			if(tlv == null){
				throw new RuntimeException("Incorrect format in head of metaData");
			}
		}
		String head = tlv.getTaglen();
		System.out.println("headOfMetadata: " + head);
		data = tlv.getValue();
		//再找body of metaData 
		ArrayList<String> array = new ArrayList<String>();
		
		beginOfSeg = data.indexOf("88");
		if(!(beginOfSeg == 0)){
			throw new RuntimeException("Incorrect format in body of metaData");
		}
		tlv = null;
		data += "FFFF";//增加endTag
		//判断该字符串是否符合TLV结构
		TLVBean str = null;
		while(str == null){
			tlv = selectTlv(data, "88", "FFFF");
			str = tlv;
			if(str == null){//判断是否到达最后一个‘88’包
				tlv = selectTlv(data, "88", "88");
				if(tlv == null){
					throw new RuntimeException("Incorrect format in body of metaData");
				}
				String segBody =tlv.getTaglen() + tlv.getValue();
				array.add(segBody);
				data = data.substring(segBody.length());
			}else{
				array.add(tlv.getTaglen() + tlv.getValue());
			}
			System.out.println("bodyOfMetadata: " + tlv.getTaglen() + tlv.getValue());
		}
		String[] body = array.toArray(new String[0]);
		ArrayList<String> array2 = new ArrayList<String>();
		array2.add(head);
		for(int i=0,n = body.length;i<n;i++){
			array2.add(body[i]);
		}
		return array2.toArray(new String[0]);
	}
	/**
	 * 拆包出replaceSessionKeys部分
	 * @param data
	 * @return
	 */
	private String ppk(String data) {
		int beginOfSeg = data.indexOf("A2");
		//判断tag是否为“A2”
		if(!(beginOfSeg == 0)){
			throw new RuntimeException("Incorrect format in ppk of BPP");
		}
		String str = null;
		TLVBean tlv = null;
		//判断该字符串是否符合TLV结构
		tlv = selectTlv(data, "A2", "A3");
		if(tlv == null){
			throw new RuntimeException("Incorrect format in ppk of BPP");
		}
		str = tlv.getTaglen() + tlv.getValue();
		System.out.println("ppk: " + str);
		//对字符串进行限定长度的分包
		return str;
	}
	
	/**
	 * 拆包出ppp部分
	 * @param data
	 * @return
	 */
	private String[] ppp(String data){
		//先找head of PPP 
		//判断tag是否为“A3” 
		int beginOfSeg = data.indexOf("A3");
		if(!(beginOfSeg == 0)){
			throw new RuntimeException("Incorrect format in head of ppp");
		}
		TLVBean tlv = null;
		//判断该字符串是否符合TLV结构
		data += "FFFF";//增加endTag
		tlv = selectTlv(data, "A3", "FFFF");
		if(tlv == null){
			throw new RuntimeException("Incorrect format in head of ppp");
		}
		String head = tlv.getTaglen();
		System.out.println("headOfPPP: " + head);
		data = tlv.getValue();
		//再找body of PPP 
		ArrayList<String> array = new ArrayList<String>();
		
		beginOfSeg = data.indexOf("86");
		if(!(beginOfSeg == 0)){
			throw new RuntimeException("Incorrect format in body of ppp");
		}
		tlv = null;
		data += "FFFF";//增加endTag
		//判断该字符串是否符合TLV结构
		TLVBean str = null;
		while(str == null){
			tlv = selectTlv(data, "86", "FFFF");
			str = tlv;
			if(str == null){//判断是否到达最后一个‘86’包
				tlv = selectTlv(data, "86", "86");
				if(tlv == null){
					throw new RuntimeException("Incorrect format in body of ppp");
				}
				String segBody =tlv.getTaglen() + tlv.getValue();
				array.add(segBody);
				data = data.substring(segBody.length());
			}else{
				array.add(tlv.getTaglen() + tlv.getValue());
			}
			System.out.println("bodyOfPPP: " + tlv.getTaglen() + tlv.getValue());
		}
		String[] body = array.toArray(new String[0]);
		ArrayList<String> array2 = new ArrayList<String>();
		array2.add(head);
		for(int i=0,n = body.length;i<n;i++){
			array2.add(body[i]);
		}
		return array2.toArray(new String[0]);
	}
	
	/**
	 * 对数据（超过长度限制）进行分包
	 * @param data
	 * @return
	 */
	private String[] lengthLimit(String data){
		int len = data.length();
		int n = len / 510;//分包个数
		boolean mod = (len % 510 != 0);//取余
		String[] str = mod ? new String[n+1] : new String[n];
		//取前面完整的包
		if (n != 0) {
			for (int i = 0; i < n; i++) {
				str[i] = data.substring(i * 510, (i + 1) * 510);
			}
		}
		//如果只有一个包(n=0) || 取最后一个包
		if (mod) {
			str[n] = data.substring(n * 510, len);
		}
		return str;
	}
	
	/**
	 * 从数据中取出tag为给定值的TLV结构（后一个TLV结构的tag已知）
	 * @param inputData
	 * @param beginTag
	 * @param endTag
	 * @return
	 */
	public TLVBean selectTlv(String inputData ,String beginTag ,String endTag) {
		int beginOfSeg = inputData.indexOf(beginTag);
		String s = inputData.substring(beginOfSeg + beginTag.length()); 
		if(s.substring(0, 2).equals("83")){
			int num83 = Integer.parseInt("83", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 8), 16)*2 + 8;
			if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 8),s.substring(8, num));
			}else if((num83 <= s.length())&&(s.substring(num83).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"83",s.substring(2, num83));
			} 
		}else if(s.substring(0, 2).equals("82")){
			int num82 = Integer.parseInt("82", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 6), 16)*2 + 6;
			if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 6),s.substring(6, num));
			}else if((num82 <= s.length())&&(s.substring(num82).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"82",s.substring(2, num82));
			} 
		}else if(s.substring(0, 2).equals("81")){
			int num81 = Integer.parseInt("81", 16)*2 + 2;
			int num = Integer.parseInt(s.substring(2, 4), 16)*2 + 4;
			if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 4),s.substring(4, num));
			}else if((num81 <= s.length())&&(s.substring(num81).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+"81",s.substring(2, num81));
			} 
		}else{
			int num = Integer.parseInt(s.substring(0, 2), 16)*2 + 2;
			if((num <= s.length())&&(s.substring(num).indexOf(endTag) == 0)){
				return new TLVBean(beginTag+s.substring(0, 2),s.substring(2, num));
			}
		}
		return null;
	}
}
