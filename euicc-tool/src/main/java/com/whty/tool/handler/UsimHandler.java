package com.whty.tool.handler;

import static com.whty.tool.util.Util.replaceBlank;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.whty.tool.bean.DfNameBean;
import com.whty.tool.bean.FileDescriptorBean;
import com.whty.tool.util.Constant;
import com.whty.tool.util.Util;
/**
 * usim 转换
 * @author Administrator
 *
 */
public class UsimHandler {
	static List<Object>fileIDList = new ArrayList<Object>();//存储反射地址
	/**
	 * usim 转换入口
	 * @param lines
	 * @param peID
	 * @return
	 * @throws Exception
	 */
	public static String usimHandler(List<String> lines,int peID) throws Exception{
		if(lines == null)return null;
		List<String> list = new ArrayList<String>();//存储变换完成的字符串
		List<Integer> a0EEList = new ArrayList<Integer>();//存储记录数
		StringBuilder buffer = new StringBuilder();
		for (String line : lines) {
			if(StringUtils.isBlank(line))continue;
		    line = StringUtils.upperCase(replaceBlank(line));
			String str = StringUtils.remove(line, " ");
			if (StringUtils.startsWith(str, Constant.MF_PREFIX) 
					|| StringUtils.startsWith(str, Constant.EF_PREFIX)) { //00E0
				a0EEList.clear();
				checkApduLength(str);
				org62Tlv(list, buffer);
				String strOf00E0 = parseCmdFor00E0(str,a0EEList);
				buffer.append(strOf00E0);
			}else if (StringUtils.startsWith(str, Constant.FILE_CONTENT_PREFIX) 
					|| StringUtils.startsWith(str, Constant.EILE_WITH00D6_PREFIX) ) {//00DC &00D6
				checkApduLength(str);
				parseCmdFor00DC(str,buffer,list);
			}else if (StringUtils.startsWith(str, Constant.FILE_PADDING_PREFIX)) {//A0EE
				checkApduLength(str);
				str = str.substring(10);
				if (!"".equals(str)|| !str.isEmpty()) {
					parseCmdForA0EE(str,buffer,a0EEList,list);
				}else {
					org62Tlv(list, buffer);
				}
			}
		}
		org62Tlv(list, buffer);//00e0出现在最后一行的情况
		String resultStr = addHeader(list,peID);
		File file = new File("usim_der.txt");
		Files.write(resultStr,file, Charsets.UTF_8);//写文件
		return resultStr;
	}
	
	/**
	 * 检验字符串长度是否正确
	 * @param str
	 * @throws Exception
	 */
	private static void checkApduLength(String str) throws Exception {
		int len = str.substring(10).length()/2;
		String lenCmd = Util.itoa(len);
		if (!lenCmd.equals(str.substring(8, 10))) {
			throw new RuntimeException("apdu lc is not equals the reality length");
		}
	}
	/**
	 * 添加头指令
	 * A1..A0..A1..30
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String  addHeader(List<String>list,int peId) throws Exception {
		String packagesString = Util.totalStr(list);
		String peString = Util.itoa(peId);//16
		String peHeader = Util.toTLV("A0", "80008101" + peString);
		String contentString = Util.toTLV("A1", Util.toTLV("30", packagesString));
		String resultStr = Util.toTLV("A1", peHeader + contentString);
		return resultStr;
		
	}
	/**
	 * 分割处理A0EE开头的指令
	 * @param str
	 * @param buffer
	 * @param list
	 * @param parentList
	 */
	private static void parseCmdForA0EE(String str,StringBuilder buffer,
			List<Integer> list,List<String >parentList) {
		String resultStr ="";
		String total00E0 = "";
		String strOf00E0 = buffer.toString();
		try {
		String fileIdString = fileIDList.get(fileIDList.size()-1).toString();
		Class c = (Class) fileIDList.get(0);
		Object obj = c.newInstance();
		Field[] fields = Util.getName(obj);
		resultStr = paddingBytes(str, resultStr, fileIdString, c, fields);
		if (list.size() > 0) {//填充多条00ff...ffff 或000000...
			loopOpera(list, parentList, resultStr, strOf00E0);
		}else {//单条记录，加上tagA5
			String propriInfo = Util.toTLV("A5",Util.toTLV("C1", str));
			System.out.println("A0EE开头的propertyinfo(A5开头):" + propriInfo);
			total00E0 = Util.toTLV("62", strOf00E0+propriInfo);
			parentList.add(total00E0);//将62tlv加入队列
			System.out.println("00e0和a0ee(a5) 开头：" + total00E0);
			
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
		buffer.setLength(0);
		 
	}

	/**
	 * 根据fileID 在类_7FXX中获取变量值并进行相应的填充
	 * @param str
	 * @param resultStr
	 * @param fileIdString
	 * @param c
	 * @param fields
	 * @return
	 * @throws Exception
	 */
	private static String paddingBytes(String str, String resultStr,
			String fileIdString, Class c, Field[] fields) throws Exception {
		for (Field ssString :fields) {
			if (ssString.toString().contains(fileIdString)) {
			int len = (Integer)Util.getAttribute(c,fileIdString);
			for (int i = 0; i < (len - 1)*2; i++) {
				if (str.equals("00FF")) {
					resultStr += "F";
				}else if ("00".equals(str)) {
					resultStr += "0";
				}
			}
		 }
		}
		return resultStr;
	}

	/**
	 * 处理A0EE 中需要填充多条00ff..ffff 或0000..00的数据
	 * @param list
	 * @param parentList
	 * @param resultStr
	 * @param strOf00E0
	 */
	private static void loopOpera(List<Integer> list,
			List<String> parentList, String resultStr, String strOf00E0) {
		String total00E0;
		String tlvStr="";
		int temp = list.get(list.size()-1);
		System.out.println("记录数：" + temp);
		for (int j = 0; j < temp; j++) {
			tlvStr += "00"+resultStr;
		}
		String strOfA0EE = Util.toTLV("81", tlvStr);
		System.out.println("a0ee 开头(多条记录)：" + strOfA0EE);
		total00E0 = Util.toTLV("62", strOf00E0);//00e0开头的62...和a0ee开头的多条clear 为00ff...ff字符串加入队列
		parentList.add(total00E0+strOfA0EE);
	}
	
	/**
	 * 分割00DC或00D6开头的指令
	 * @param str
	 * @throws Exception 
	 */
	public static void parseCmdFor00DC(String str,StringBuilder buffer,List<String>list) throws Exception {
		String strOf00E0 = buffer.toString();
		String totalString = "";
		if (("01".equals(str.substring(8, 10)))//fillpattern
				|| ("02".equals(str.substring(8, 10)))) {
			String propriInfo = Util.toTLV("A5",Util.toTLV("C1", str.substring(10)));
			totalString = Util.toTLV("62", strOf00E0+propriInfo);
			System.out.println("00E0开头(include A5)：" + totalString);
			list.add(totalString);
			buffer.setLength(0);
		}else if(("02".compareTo(str.substring(8, 10))<0)&&"".equals(strOf00E0)){ //fillfilecontent
			str = str.substring(10);
			totalString = Util.toTLV("81", str);
			System.out.println("00DC/00d6开头：" +totalString);
			list.add(totalString);
		}else {
			str = str.substring(10);
			totalString = Util.toTLV("81", str);
			String total00E0 = Util.toTLV("62", strOf00E0);
			System.out.println("00E0开头：" + total00E0+totalString);
			list.add(total00E0+totalString);
			buffer.setLength(0);
		}
			
	}
	
	/**
	 * 分割00E0开头的指令
	 * 62..82..83..8A..8B
	 * 62..82..83..8B
	 * @param str
	 * @throws Exception
	 */
	public static String parseCmdFor00E0(String str,List<Integer>aoEEList) throws Exception{
		List<String> list = new ArrayList<String>();
		str = str.substring(10);//62
		if (!StringUtils.startsWith(str, "62")) {
			throw new RuntimeException("This command is not start with tag62");
		}

		str = str.substring(4);//到82处
		if (!StringUtils.startsWith(str, "82")) {
			throw new RuntimeException("This command is not start with tag82");
		}

		FileDescriptorBean nextStr = org82Tlv("82",str,list);
		str = nextStr.getNextStr();
		String recoNum = nextStr.getRecordNum();
		if (!StringUtils.startsWith(str, "83")) {
			throw new RuntimeException("This command is not start with tag83");
		}
		
		str = org83Tlv("83",str,list);
		if (StringUtils.startsWith(str, "84")) {
			DfNameBean  dfName = org84Tlv("84", str);
			String strOf84 = dfName.getCurrString();
			String strOfNext = dfName.getNextString();
			parseCmdFor8A(list, strOfNext,strOf84,recoNum,aoEEList);
		}else {
			parseCmdFor8A(list, str,"",recoNum,aoEEList);
		}
			
		String total = Util.totalStr(list);
		return total;
	}
	/**
	 * 处理62tlv
	 * @param list
	 * @param buffer
	 */
	private static void org62Tlv(List<String> list, StringBuilder buffer) {
		if (!"".equals(buffer.toString())) {
			String total00E0 = Util.toTLV("62", buffer.toString());
			list.add(total00E0);
			System.out.println("00e0开头（without anything）:"+ total00E0);
			buffer.setLength(0);
		}
	}
	/**
	 * 处理82tlv
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 */
	private static FileDescriptorBean org82Tlv(String tag, String str, List<String> list) {
		String recordNum ="";
		StringBuilder cmdBuilder = new StringBuilder();
		String length = str.substring(2, 4);//16
		int tempLen = Util.atoi(length);//10
		String value = str.substring(4, 4 + tempLen*2);
		cmdBuilder.append(tag).append(length).append(value);
		list.add(cmdBuilder.toString());
		if (tempLen == 4) {
			recordNum = value.substring(value.length()-2,value.length());
		}else {
			recordNum = "";
		}
		str = str.substring((tag + length + value).length());
		return new FileDescriptorBean(recordNum, str);
	}
	/**
	 * 处理83tlv
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 * @throws Exception
	 */
	private static String org83Tlv(String tag, String str, List<String> list) throws Exception {
		StringBuilder cmdBuilder = new StringBuilder();
		String length = str.substring(2, 4);//16
		int tempLen = Util.atoi(length)*2;//10
		String value = str.substring(4, 4 + tempLen);
		cmdBuilder.append(tag).append(length).append(value);
		list.add(cmdBuilder.toString());
		reflectSearch(value);
		str = str.substring((tag + length + value).length());
		return str;
	}
	/**
	 * 通过反射找到文件填充字节长度
	 * @param str
	 * @throws Exception
	 */
	private static void reflectSearch(String str) throws Exception{
		try {
			if (str.contains("7FF0")) {
				fileIDList.clear();
				Class c = Class.forName("com.whty.tool.util.C_7FF0");
				Object obj = c.newInstance();
				fileIDList.add(c);
			}else if (str.contains("7F10")) {
				fileIDList.clear();
				Class c = Class.forName("com.whty.tool.util.C_7F10");
				Object obj = c.newInstance();
				fileIDList.add(c);
			}if (str.contains("7F20")) {
				fileIDList.clear();
				Class c = Class.forName("com.whty.tool.util.C_7F20");
				fileIDList.add(c);

			}else {
			fileIDList.add("_"+str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	/**
	 * 8A..8B..81..C6..A5
	 * 8A..8B..C6..A5
	 * 8A..8B..80..88
	 * @param list
	 * @param strOfNext
	 * @param strOf84
	 * @param recordNum
	 * @param aoEEList
	 * @throws Exception
	 */
	private static void parseCmdFor8A( List<String> list,
			String strOfNext,String strOf84,String recordNum,List<Integer>aoEEList) throws Exception {
		if (StringUtils.startsWith(strOfNext, "8A")) {
			strOfNext = remove("8A",strOfNext,list);
			parseCmdFor8B(list, strOfNext,strOf84,recordNum,aoEEList);

		}else {
			parseCmdFor8B(list, strOfNext,strOf84,recordNum,aoEEList);
		}
	}

	/**
	 * 8B..81..C6..A5
	 * 8B..C6..A5
	 * 8B..80..88
	 * @param list
	 * @param resultStr
	 * @throws Exception
	 */
	private static void parseCmdFor8B(List<String> list, String resultStr,String strOf84,
			String recordNum,List<Integer>aoEEList)throws Exception {
		if (!StringUtils.startsWith(resultStr,"8B")) {
			throw new RuntimeException("This command is not start with tag8B");
		}
		resultStr = orgTlv("8B",resultStr,list);
		if (StringUtils.startsWith(resultStr,"81")) {//optional
			resultStr = orgTlv("81",resultStr,list);
			if (!StringUtils.startsWith(resultStr,"C6")) {
				throw new RuntimeException("This command is not start with tagC6");
			}
			resultStr = orgC6Tag("C6",resultStr,strOf84,list);
			if (StringUtils.startsWith(resultStr,"A5")) {
				resultStr = remove("A5",resultStr,list);
			}
		}else if (StringUtils.startsWith(resultStr,"C6")) {//MF
			resultStr = orgC6Tag("C6",resultStr,strOf84,list);
			if (StringUtils.startsWith(resultStr,"A5")) {//optional
				resultStr = remove("A5",resultStr,list);}
		
		}else if (StringUtils.startsWith(resultStr,"80")) {//EF
			resultStr = org80Tlv("80",resultStr,recordNum,list,aoEEList);
			if (StringUtils.startsWith(resultStr,"88")) {//optional
				resultStr = orgTlv("88",resultStr,list);
				if (StringUtils.startsWith(resultStr, "A5")) {
					resultStr = orgA5Tlv("A5",resultStr,list);
				}
			}
		}else {
			throw new RuntimeException("This command is not start with essential tag C6 or 80");
		}
	} 
	/**
	 * 处理80tlv
	 * @param tag
	 * @param str
	 * @param recordNum
	 * @param list
	 * @param numList
	 * @return
	 */
	private static String org80Tlv(String tag, String str,String recordNum,
			List<String> list,List<Integer> numList) {
		StringBuilder cmdBuilder = new StringBuilder();
		String length = str.substring(2, 4);//16
		int tempLen = Util.atoi(length)*2;//10
		String value = str.substring(4, 4 + tempLen);
		cmdBuilder.append(tag).append(length).append(value);
		list.add(cmdBuilder.toString());
		if (!"".equals(recordNum)) {
			int valueOfInt = Integer.parseInt(value, 16);
			int recoNumOfInt = Integer.parseInt(recordNum, 16);
			int numOfLoop = valueOfInt / recoNumOfInt;
			numList.add(numOfLoop); //81xx循环的次数
		}
		str = str.substring((tag + length + value).length());
		return str;
	}
	/**
	 * 处理不需多余操作的tlv
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 */
	public static String orgTlv(String tag,String str,List<String> list){
		StringBuilder cmdBuilder = new StringBuilder();
		String length = str.substring(2, 4);//16
		int tempLen = Util.atoi(length)*2;//10
		String value = str.substring(4, 4 + tempLen);
		cmdBuilder.append(tag).append(length).append(value);
		list.add(cmdBuilder.toString());
		str = str.substring((tag + length + value).length());
		return str;
	}
	/**
	 * 处理A5 tlv
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 */
	private static String orgA5Tlv(String tag, String str,
			List<String> list) {
		String newTlv = "";
		String length = str.substring(2, 4);//16
		int tempLen = Util.atoi(length);//10
		String value = str.substring(4, 4 + tempLen*2);
		if (tempLen == 7) {
			value = value.substring(value.length()-8, value.length());
			newTlv = Util.toTLV("C7", value);
		}else if (tempLen == 3) {//todo
			newTlv = "";
		}else if (tempLen  == 5) {
			newTlv = "ss";//todo
		}else if (tempLen == 4) {
			newTlv = tag + length + value;
		}
		list.add(newTlv);
		str = str.substring((tag + length + value).length());
		return str;
	}
	/**
	 * 按照1110测试脚本，84tag位于8btag之后，暂不加入list
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 */
	public static DfNameBean org84Tlv(String tag,String str){
		StringBuilder cmdBuilder = new StringBuilder();
		String length = str.substring(2, 4);//16
		int tempLen = Util.atoi(length)*2;//10
		String value = str.substring(4, 4 + tempLen);
		cmdBuilder.append(tag).append(length).append(value);
		str = str.substring((tag + length + value).length());
		return new DfNameBean(cmdBuilder.toString(),str);
	}
	/**
	 * 移除TAG C6中不需要的数据
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static String orgC6Tag(String tag,String str,String strOf84,List<String > list) throws Exception{
		String lenOfC6 = str.substring(2, 4);//16
		int totalLen = (Util.atoi(lenOfC6))*2;//10
		String valueOfC6 = str.substring(4, 4 + totalLen);
		String Value0f83 = "";
		if(valueOfC6.indexOf("83") != -1){  
	        String[] str1 = valueOfC6.split("83"); 
	        for (int i = 1; i< str1.length; i++) {
				System.out.println("C6TLV中每个tag83后的length和value:" + str1[i]);
				String length = str1[i].substring(0, 2);//16
				int tempLen = (Util.atoi(length))*2;//10
				String value = str1[i].substring(2, 2 + tempLen);
				Value0f83 +=  value;
			}
	        String reOrgStrC6 = Util.toTLV(tag, Value0f83);
			list.add(strOf84 + reOrgStrC6);
			str = str.substring((tag + lenOfC6 + valueOfC6).length());
	    }else {
			throw new RuntimeException("did not get the tag 83!");
		} 
		return str;
	}
	/**
	 * 对于不需要的tlv，移除
	 * @param tag
	 * @param str
	 * @param list
	 * @return
	 */
	public static String remove(String tag,String str,List<String > list){
		String length = str.substring(2, 4);//16
		int tempLen = (Util.atoi(length))*2;//10
		String value = str.substring(4, 4 + tempLen);
		str = str.substring((tag + length + value).length());
		return str;
	}
	
}
