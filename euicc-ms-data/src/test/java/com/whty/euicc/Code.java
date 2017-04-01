package com.whty.euicc;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dzmsoft.plugin.gencode.GenUtils;

public class Code{
	
	//生成java
	@Test
	public void generateJavaCode(){
		//要生成的java项目对应目录
		//String  javaProgramPath="C:/Users/Administrator/Workspaces/MyEclipse 10/euicc-ms-data";
		String  javaProgramPath="C:/Users/Administrator/Desktop/eUICC/code";
		List<String> beanNameList=new ArrayList<String>();
		beanNameList.add("EuiccProfile");
		GenUtils.genSeriveAndController(beanNameList, "com.whty.euicc.data",javaProgramPath);
	}
	

	//生成jsp和js
	@Test
	public void generateJSPANDJSCode(){
		//jsp路径
		String jspSrc="C:/Users/Administrator/Workspaces/MyEclipse 10/euicc-ms-container/src/main/webapp/WEB-INF/views/modules";
		//js路径
		String jsSrc="C:/Users/Administrator/Workspaces/MyEclipse 10/euicc-ms-container/src/main/webapp/resources/pages/scripts";

		List<String> beanNameList=new ArrayList<String>();
		beanNameList.add("profileMgr");
		GenUtils.genJSPANDJS(beanNameList,"",jspSrc,jsSrc);
	}
	
	@Test
	public void test(){
		List<String> list = new ArrayList<String>();
		list.add("123"); 
		list.add("234"); 
		System.out.println(list.contains("1"));
	}
}
