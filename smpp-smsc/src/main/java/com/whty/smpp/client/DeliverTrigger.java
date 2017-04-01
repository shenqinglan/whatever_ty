package com.whty.smpp.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class DeliverTrigger {
	
	/** 
	 * @author Administrator
	 * @date 2017-1-5
	 * @param args
	 * @throws FileNotFoundException 
	 * @Description TODO(这里用一句话描述这个方法的作用)
	 */
	public static void main(String[] args) {
		
	}
	
	@Test
	public void startDeliver() {
		Properties prop = new Properties(); 
		try {
			File file = new File("conf/deliverStart.props");  
            if (!file.exists())  
                file.createNewFile();  
			InputStream in = new FileInputStream(file);
			prop.load(in);
			in.close();
			FileOutputStream oFile = new FileOutputStream("conf/deliverStart.props");
			prop.setProperty("deliverStart", "TRUE");
			prop.store(oFile, "The New properties file");
			oFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
	
	@Test
	public void stopDeliver() throws InterruptedException {
		Properties prop = new Properties(); 
		try {
			File file = new File("conf/deliverStop.props");  
            if (!file.exists())  
                file.createNewFile();  
			InputStream in = new FileInputStream(file);
			prop.load(in);
			in.close();
			FileOutputStream oFile = new FileOutputStream("conf/deliverStop.props");
			prop.setProperty("deliverStop", "TRUE");
			prop.store(oFile, "The New properties file");
			oFile.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}    
	}
}

