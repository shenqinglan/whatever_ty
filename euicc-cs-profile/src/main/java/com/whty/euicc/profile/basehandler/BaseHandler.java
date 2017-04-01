package com.whty.euicc.profile.basehandler;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.whty.euicc.profile.parent.JavaBean;
import com.whty.euicc.profile.pukCodes.bean.Sequence_1;

import static com.whty.euicc.profile.util.Tool.*;

/**
 * 读取元素，asn转der，打印转换后的值
 * list用来存储解析的每一个元素,每次读取一行存入 str中
 * @author Administrator
 *
 */

public class BaseHandler implements IBaseHandler{
	private boolean flag = false;
	private final String classPath = "com.whty.euicc.profile.";

	private String packageName = "";
	
	private int i = 0;//标识sqnInit结构中全为0 的序列号，全为0时等于32

	private  boolean count = false;

	private  StringBuffer totalDER = new StringBuffer();
	
	List<String> tempList = new ArrayList<String>();//存储具有默认值的复杂结构，目前见到的只有sqnInit
	
	//对于默认值的元素，不做解析操作
	private static Map<String, String> argsMap; 
	static{
		argsMap = new HashMap<String, String>(); 
		argsMap.put("Lcsi", "'05'H");//MF/DF/ADF
		
		argsMap.put("PinAttributes_pin", "7"); //pincodes
		argsMap.put("MaxNumOfAttemps_retryNumLeft_pin", "51"); //pinCodes
		
		argsMap.put("MaxNumOfAttemps_retryNumLeft_puk", "170"); //pukCodes
		
		argsMap.put("KeyAccess", "'00'H"); //securityDomain
		argsMap.put("MacLength", "8");//securityDomain 
		
		argsMap.put("LifeCycleState", "'07'H");//securityDomain & application 
		
		argsMap.put("SpecialFileInformation", "'00'H");//file relates type eg.usim...
		
		argsMap.put("SqnOptions", "'02'H"); //akaparameter
		argsMap.put("SqnDelta", "'000010000000'H");//akaparameter
		argsMap.put("SqnAgeLimit", "'000010000000'H");//akaparameter
		argsMap.put("RotationConstants", "'4000204060'H");//akaparameter, only apply for Milenage
		argsMap.put("XoringConstants", "'0000000000000000000000000000000000000000000" +
				"0000000000000000000010000000000000000000000000000000200000000000000000" +
				"00000000000000400000000000000000000000000000008'H");//akaparameter, only apply for Milenage
		
		
	}
	
	/**
	 * 
	 * 
	 * @param br
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public  String handler(BufferedReader br) throws Exception {
		String str = null;
		List<Object> list = new ArrayList<Object>();
		while ((str = br.readLine()) != null) {
			if(StringUtils.isBlank(str) || StringUtils.startsWith(str, "//")){
				str = replaceBlank(str);
				continue;
			}
			str = str.trim();
			// 如果是简单类型
			if (!isConstructed(str)) {
				// 遇到“}”和“}，”表示不用解析，跳过
				if ("}".equals(str) || "},".equals(str)) {
					if (i == 32 && StringUtils.equals(tempList.get(0), "SqnInit")) {//移除不用解析的元素
//						System.out.println("sqnInit:" + (list.size() - 33));
						for (int j = list.size() - 33; j < list.size(); j++) {
							list.remove(j);
							j--;
						}
						tempList.clear();
						i = 0;
						continue;
					}else {
						JavaBean bean = (JavaBean) list.get(list.size() - 1);
						if (StringUtils.isBlank(bean.getValue())) {
							bean.setLength("00");//复杂结构内部元素为空，长度赋值为00 eg. eUICC-Mandatory-GFSTEList {}
						}
						bean.setNumber(bean.getNumber() + "1");
						list.set(list.size() - 1, bean);
						continue;
					}
					
				}
				// 将读取出来的字符串分割
				String[] tlv = partition(str);
				// 将分割好的字符串规范格式
				tlv[0] = stringFormat(tlv[0]);
				Class c = Class.forName(classPath + packageName + tlv[0]);
				Object obj = c.newInstance();
				// 给简单类型value和length赋值
				initValue(obj, tlv[1], c);
				if (!checkDefault(tlv,list)) {//检查元素的值是否为默认值
					list.add(obj);
				}
			} else {//EF元素开头（：：=）
				if (str.contains("::=")) {
					if(flag){
						printLength(list);
						list.clear();
						count = false;
					}
					String name = mysubString(str, "::=", ": {");
					packageName = name.replace("-", "_") + ".bean.";
					name = stringFormat(name);
					Class c = Class.forName(classPath + packageName + name
							+ "_PE");
					Object obj = c.newInstance();
					list.add(obj);
					if (StringUtils.equals(name, "PukCodes") ||StringUtils.equals(name, "PinCodes")) {
						tempList.add(name);
					}
					flag = true;
				} else {
					// 创建复杂结构的JavaBean,并且将创建的对象添加到list集合中
					createConstructed(str, list);
				}
			}
			str = null;
		}
		String resultStr = printLength(list).toString();
		File file = new File("profile_der.der");
		Files.write(resultStr,file, Charsets.UTF_8);//写文件
		return resultStr;
		//System.out.println(handlerDER(totalDER));
	}
	
	/**
	 * 检查元素值是否为默认
	 */
	private boolean checkDefault(String[] tlvStr,List<Object> list){
		boolean execuCode = true;
		boolean s = tempList.isEmpty();
		if (!StringUtils.equals(argsMap.get(tlvStr[0]), tlvStr[1]) 
				&& (s == true || (!StringUtils.equals(tempList.get(0), "SqnInit")))
				&& (s == true || (!StringUtils.equals(tempList.get(0), "PukCodes"))) 
				&& (s == true || (!StringUtils.equals(tempList.get(0), "PinCodes")))) {
			
			execuCode = false;
		}else if (s == false &&(StringUtils.equals(tempList.get(0), "SqnInit"))
				&&(StringUtils.equals("'000000000000'H", tlvStr[1]))) {//判断sqnInit子元素是否为默认
			i = i+1;
			execuCode = false;
		}else if (s == false && (StringUtils.equals(tempList.get(0), "PukCodes"))
				&& (!StringUtils.equals(argsMap.get(tlvStr[0]+"_puk"), tlvStr[1]))) {
			execuCode = false;
			
		}else if (s == false && (StringUtils.equals(tempList.get(0), "PinCodes"))
				&& (!StringUtils.equals(argsMap.get(tlvStr[0]+"_pin"), tlvStr[1]))) {
			execuCode = false;
			
		}
		
		return execuCode;
	}

	/**
	 * 计算length的值并将解析的结果打印出来
	 */
	public  StringBuffer printLength(List<Object> list) {
//		System.out.println("listsize in perintlength ::" + list.size());
		tempList.clear();
		List<String> len = new ArrayList<String>();
		StringBuffer t = new StringBuffer();
		
		JavaBean beanLast = (JavaBean) list.get(list.size() - 1);
		if (!"1".equals(beanLast.getNumber())) {
			add(beanLast, len, t);
			count = true;
			t.setLength(0);
		}
		for (int i = list.size() - 2; i > 0; i--) {//原来为减2，待有确切例子后，需再次验证
			JavaBean b = (JavaBean) list.get(i);

			if (!"".equals(b.getNumber()) && !isStart(b.getTag())) {
				b.setLength("00");
			} else if (!"".equals(b.getNumber()) || count) {
				count = true;
				if (!isStart(b.getTag())) { // 遇到复杂类型就为计算其length值并赋值
					int tempLength = len.get(len.size() - 1).length() / 2;
					// 判断length长度并且赋值
					countLength(tempLength, b);
					if (len.size() != 1) {
						len.remove(len.size() - 1);// 赋值完成后删除len最后一个元素
					}

					for (int a = 0; a < len.size(); a++) {
						String s = len.get(a) + b.getTag() + b.getLength();
						len.set(a, s);
					}
					if (len.size() == 1
							&& isStart(((JavaBean) list.get(i - 1)).getTag())) {
						count = false;
					}
				} else if (!"".equals(b.getNumber())) {// 遇到标记 （即复杂结构）
					// 遇到新的复杂结构，就将其添加都len中
					add(b, len, t);
					int num = b.getNumber().length();
					for (int a = 0; a < len.size() - num; a++) {
						String s = len.get(a) + t.toString();
						len.set(a, s);
					}
					t.setLength(0);
				} else {
					t.append(b.getTag()).append(b.getLength()).append(
							b.getValue());
					if (len.size() == 0) {
						len.add(t.toString());
					} else {
						for (int j = 0; j < len.size(); j++) {
							String s = len.get(j) + t.toString();
							len.set(j, s);
						}
					}
					t.setLength(0);
				}
				t.setLength(0);
			}

		}

		// 计算总length
		int length = countTotal(list);

		JavaBean bean = (JavaBean) list.get(0);

		// 判断length长度并且赋值
		countLength(length, bean);

		// 打印编码结构
		String toDer = toDerStr(list).toUpperCase();
		totalDER.append(toDer); //+ "\n"
//		System.out.println("---------------------------------");
//		System.out.println(toDer);
		return totalDER;
	}

	/**
	 * 遇到一个新的赋值结构，就将其添加都len中
	 * 
	 * @param b
	 * @param len
	 *            存储复杂类型里面的元素
	 * @param t
	 */
	public static void add(JavaBean b, List<String> len, StringBuffer t) {
		int num = b.getNumber().length();
		String[] temp = new String[num];
		t.append(b.getTag()).append(b.getLength()).append(b.getValue());
		for (int a = 0; a < num; a++) {
			temp[a] = "";
			temp[a] += t;
			len.add(temp[a]);
		}
	}

	/**
	 * 计算总的length
	 * 
	 * @param list
	 *            存储所有元素的集合
	 * @return 返回总长度
	 */
	public static int countTotal(List<Object> list) {
		String total = "";
		for (int i = list.size() - 1; i > 0; i--) {
			JavaBean totalBean = (JavaBean) list.get(i);
			total = total + totalBean.getTag() + totalBean.getLength()
					+ totalBean.getValue();
		}
		total = total.replace("null", "");
		return total.length() / 2;
	}


	/**
	 * 判断是否是复杂类型 true 是复杂类型
	 */
	public static boolean isConstructed(String str) {
		return str.contains("{") && !str.contains("}");
	}

	/**
	 * 根据类名用反射创建复杂结构的JavaBean，并且将创建的对象添加到list集合中
	 * 
	 * @param str
	 * @return 返回创建成功的对象
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void createConstructed(String str, List<Object> list)
			throws Exception {
		Object obj = null;
		if ("{".equals(str)) {
			obj = new Sequence_1();
			list.add(obj);
		} else if (str.contains(": {")) {
			String name = mysubString(str, "", ": {");
			if (name.contains(" ")) {
				String[] names = name.split(" ");
				for (String s : names) {
					s = stringFormat(s);
					Class c = Class.forName(classPath + packageName.replace("-", "_") + s + "_1");
					obj = c.newInstance();
					list.add(obj);
					
				}
			} else {
				name = stringFormat(name);
				Class c = Class.forName(classPath + packageName.replace("-", "_") + name + "_1");
				obj = c.newInstance();
				list.add(obj);
			}
		} else {//sqnInit {
			String name = mysubString(str, "", " ");
			name = stringFormat(name);
			Class c = Class.forName(classPath + packageName.replace("-", "_") + name + "_1");
			obj = c.newInstance();
			list.add(obj);
			if (StringUtils.equals(name, "SqnInit")) {
				tempList.add(name);
			}
		}
	}
	
	/**
	 * 将解析的结构变成86LV的形式
	 * @param totalDer
	 * @return
	 */
	public String handlerDER(StringBuffer totalDer){
		String temp = totalDer.toString();
		StringBuffer sb = new StringBuffer();
		int num = temp.length()/2/127;
		int count = temp.length()/2%127;
		for(int i=0;i<num;i++){
			sb.append("867F").append(temp.substring(i*127*2,(i+1)*127*2));
		}
		sb.append("86").append(toHex(String.valueOf(count))).append(temp.substring(num*2*127));
		
		return sb.toString();
	}
	
	
	
	
	
	
	

}







