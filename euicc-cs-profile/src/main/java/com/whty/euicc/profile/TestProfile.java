package com.whty.euicc.profile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import com.whty.euicc.profile.basehandler.BaseHandler;

public class TestProfile {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		try {
			String fileName = args.length > 0 ? args[0] : "chinaUnicom.asn";
			System.out.println(fileName);
			File file = new File(fileName);
			if(!file.exists()){
				file = new File(new File("").getAbsolutePath(), fileName);
				if(!file.exists()){
					System.out.println("file not exists!!");
					return;
				}
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			System.out.println(new BaseHandler().handler(br));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
