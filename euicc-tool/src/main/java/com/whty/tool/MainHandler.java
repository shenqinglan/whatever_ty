package com.whty.tool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.whty.tool.handler.CdmaHandler;
import com.whty.tool.handler.CsimHandler;
import com.whty.tool.handler.GsmHandler;
import com.whty.tool.handler.MfHandler;
import com.whty.tool.handler.PinHandler;
import com.whty.tool.handler.PukHandler;
import com.whty.tool.handler.UsimHandler;
/**
 * 程序入口类
 * @author Administrator
 *
 */
public class MainHandler {
	public static void main(String[] args) throws Exception {
		List<String> apdus = readFile(args);
		int peType = Integer.parseInt(args[1]);
		int peId = Integer.parseInt(args[2]);
		switch (peType) {
		case 0:
			MfHandler.baseHandler(apdus,peId);
			break;
        case 1:
			PukHandler.pukHandler(apdus,peId);
			break;
        case 2:
        	PinHandler.pinHandler(apdus,peId);
	        break;
        case 3:
        	UsimHandler.usimHandler(apdus, peId);
        	break;
        case 4:
        	CsimHandler.csimHandler(apdus, peId);
        	break;
        case 5:
        	GsmHandler.gsmHandler(apdus,peId);//7f20
        case 6:
        	CdmaHandler.cdmaHandler(apdus, peId);//7f25
		default:
			break;
		}
	}
	/**
	 * 读取文件
	 * @param args
	 * @return
	 * @throws IOException
	 */
	public static List<String> readFile(String[] args) throws IOException {
		String fileName = args.length > 0 ? args[0] : "file.txt";
		System.out.println(fileName);
		File file = new File(fileName);
		if(!file.exists()){
			file = new File(new File("").getAbsolutePath(), fileName);
			if(!file.exists()){
				System.out.println("file no exists");
				return null;
			}
		}
		List<String> apdus = Files.readLines(file, Charsets.UTF_8);
		return apdus;
	}

}
