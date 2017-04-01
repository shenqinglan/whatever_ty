package com.whty.euicc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.junit.Test;

import com.whty.euicc.common.utils.HttpUtil;

public class StressTest {
	private String EISUrl = "http://localhost:8080/euicc-ms-container/stressTest/insertEuiccCard";
	private String ProfileUrl = "http://localhost:8080/euicc-ms-container/stressTest/insertProfile";

	@Test
	public void EIS()throws Exception{
		String path = StressTest.class.getClassLoader().getResource("EIS.json").toURI().getPath();
        byte[] msgBype =HttpUtil.doPost(EISUrl, getJson(path));
        System.out.println(new String(msgBype));
	}
	@Test
	public void Profile()throws Exception{
		String path = StressTest.class.getClassLoader().getResource("PROFILE.json").toURI().getPath();
        byte[] msgBype =HttpUtil.doPost(ProfileUrl, getJson(path));
        System.out.println(new String(msgBype));
	}
	
	private byte[] getJson(String filePath) {
		try {
            String encoding="utf-8";
            File file=new File(filePath);
            if(file.isFile() && file.exists()){ //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                new FileInputStream(file),encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                StringBuffer sb = new StringBuffer();
                String lineTxt = null;
                while((lineTxt = bufferedReader.readLine()) != null){
                	sb.append(lineTxt);
                }
                read.close();
                return sb.toString().getBytes();
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
