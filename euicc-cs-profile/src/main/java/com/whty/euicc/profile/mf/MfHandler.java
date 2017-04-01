package com.whty.euicc.profile.mf;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import com.whty.euicc.profile.parent.JavaBean;
import com.whty.euicc.profile.util.Tool;


import static com.whty.euicc.profile.util.Tool.*;
public class MfHandler {
	/**
	 * 用来存储解析的每一个元素
	 */
	private static List<Object> list = new ArrayList<Object>();
	private static boolean flag = true;
	/**
	 * 每次读取一行存入 str中
	 */
	private static String str = null;
	
	private static final String classPath = "com.whty.euicc.profile.mf.bean.";
	
	

	/**
	 * 递归处理asn
	 * @param br
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static void recursion(BufferedReader br) throws Exception {
		if (flag) {
			str = br.readLine().trim();
		}

		str = str.trim();

		if (!str.contains("{") || (str.contains("{") && str.contains("}"))) { // 判断是否为复杂结构

			do {

				str = str.trim();
				if (str.contains("{") && !str.contains("}")) { // 判断是否为复杂结构
					flag = false;
					recursion(br);
				}

				// 读到文件末尾，打印编码结构并结束程序
				if (str == null) {
					
					boolean count = false;
					List<String> len = new ArrayList<String>(); //存储复杂类型的元素
					StringBuffer t = new StringBuffer();
					JavaBean beanLast =  (JavaBean) list.get(list.size() - 1);
					if(!"1".equals(beanLast.getNumber())){
						add(beanLast,len,t);
						t.setLength(0);
						count = true;
					}
					
					
					for (int i = list.size() - 2; i > 0; i--) {
						JavaBean b = (JavaBean) list.get(i);
						
						if(!"".equals(b.getNumber()) && !isStart(b.getTag())){
							b.setLength("00");
						}else if(!"".equals(b.getNumber()) || count){  
							count = true;
							if(!isStart(b.getTag())){  //遇到复杂类型就为计算其length值并赋值
								int tempLength =  len.get(len.size()-1).length()/2;
								//判断length长度并且赋值
								countLength(tempLength,b);
								
								len.remove(len.size()-1);//赋值完成后删除len最后一个元素
								
								for(int a=0;a<len.size();a++){
									String s = len.get(a)+b.getTag()+b.getLength();
									len.set(a, s);
								}
								if(len.size() ==1){
									count = false;
								}
							}else if(!"".equals(b.getNumber())){//遇到标记 （即复杂结构）
								//遇到新的复杂结构，就将其添加都len中
								add(b,len,t);
								int num = b.getNumber().length();
								for(int a=0;a<len.size()-num;a++){
									String s = len.get(a)+t.toString();
									len.set(a, s);
								}
								t.setLength(0);
								
							}else{
								t.append(b.getTag()).append(b.getLength()).append(b.getValue());
								if(len.size() == 0){
									len.add(t.toString());
								}else{
									for(int j = 0;j<len.size();j++){
										String s = len.get(j)+t.toString();
										len.set(j, s);
									}
								}
								t.setLength(0);
							}
							t.setLength(0);
						}
						
					}
					
					//计算总length
					int length = countTotal(list);
					
					JavaBean bean = (JavaBean) list.get(0);
					
					//判断length长度并且赋值
					countLength(length,bean);
					
					
					// 打印编码结构
					String toDer = toDerStr(list);
					System.out.println("---------------------------------");
					System.out.println(toDer);

					System.exit(0);

				}

				// 遇到“}”和“}，”表示不用解析，跳过
				if ("}".equals(str) || "},".equals(str)) {
					JavaBean bean = (JavaBean)list.get(list.size()-1);
					bean.setNumber(bean.getNumber()+ "1");
					list.set(list.size()-1, bean);
					continue;
				}

				// 将读取出来的字符串分割
				String[] tlv = partition(str);

				// 将分割好的字符串规范格式
				tlv[0] = stringFormat(tlv[0]);
				
				Class c = Class.forName(classPath + tlv[0]);
				Object obj = c.newInstance();

				// 给简单类型value和length赋值
				initValue(obj, tlv[1], c);
				
				list.add(obj);


			} while ((str = br.readLine()) != null);

		} else {

			if (str.contains("::=")) {
				String name = mysubString(str, "::=", ": {");
				name = stringFormat(name);
				Class c = Class.forName(classPath + name+"_PE");
				Object obj = c.newInstance();
				list.add(obj);
			} else {
				String name = mysubString(str, "", " ");
				name = stringFormat(name);
				Class c = Class.forName(classPath + name+"_1");
				Object obj = c.newInstance();
				list.add(obj);
				flag = true;

			}

			recursion(br);
		}
	}

	

	/**
	 * 遇到一个新的赋值结构，就将其添加都len中
	 * @param b 
	 * @param len 存储复杂类型里面的元素
	 * @param t
	 */
	public static void add(JavaBean b, List<String> len,StringBuffer t){
		int num = b.getNumber().length();
		String[] temp = new String[num];
		t.append(b.getTag()).append(b.getLength()).append(b.getValue());
		for(int a = 0;a<num;a++){
			temp[a] = "";
			temp[a]+=t;
			len.add(temp[a]);
		}
	}
	
	
	/**
	 * 计算总的length
	 * @param list 纯属所以元素的集合
	 * @return 返回总长度
	 */
	public static int countTotal(List<Object> list){
		String total = "";
		for(int i=list.size()-1;i>0;i--){
			JavaBean totalBean = (JavaBean)list.get(i);
			total = total+totalBean.getTag()+totalBean.getLength()+totalBean.getValue();
		}
		total = total.replace("null","");
		return total.length() / 2;
	}

	/**
	 * 判断length为长类型还是短类型，并计算length的值
	 * @param length length的十进制表示
	 * @param bean 
	 */
	public static void countLength(int length,JavaBean bean ){
		if(length>127){
			bean.setLength(Tool.lengthFormat(length));
		}else{
			bean.setLength(toHex(String.valueOf(length)));
		}
	}
	
	
	
	//public static String 

}
