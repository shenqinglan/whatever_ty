package com.whty.efs.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlUtil {
	
	   /*
	    * 输入流转化为字符串
	    */
	   public static String inputStream2String(InputStream is) throws IOException{
		   BufferedReader in = new BufferedReader(new InputStreamReader(is));
		   StringBuffer buffer = new StringBuffer();
		   String line = "";
		   while ((line = in.readLine()) != null){
		     buffer.append(line);
		   }
		   return buffer.toString();
	   }

	   /*
	    * 解析xml文件，返回指定标签的文本内容
	    */
	   public static String parseXml(String xml, String tag1, String tag2){
		   String resultData = null;
	       Document doc = null;
	       try {

	           doc = DocumentHelper.parseText(xml); // 将字符串转为XML

	           Element rootElt = doc.getRootElement(); // 获取根节点

	           Iterator iter = rootElt.elementIterator("Body"); // 获取根节点下的子节点Body

	           // 遍历body节点
	           while (iter.hasNext()) {

	               Element recordEle = (Element) iter.next();

	               Iterator iters = recordEle.elementIterator(tag1); // 获取子节点Body下的子节点tag1

	               // 遍历Body节点下的tag1节点
	               while (iters.hasNext()) {

	                   Element itemEle = (Element) iters.next();

	                   resultData = itemEle.elementTextTrim(tag2); // 拿到Body下的子节点tag1下的子节点tag2的值

	               }
	           }
	           
	       } catch (Exception e) {
	           e.printStackTrace();

	       }
			return resultData;
	   }
	   
}
