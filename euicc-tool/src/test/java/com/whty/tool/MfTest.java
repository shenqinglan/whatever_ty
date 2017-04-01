package com.whty.tool;
import java.io.BufferedReader;
import static com.whty.tool.MainHandler.readFile;
import static com.whty.tool.handler.CsimHandler.csimHandler;
import static com.whty.tool.handler.MfHandler.baseHandler;
import static com.whty.tool.handler.PinHandler.pinHandler;
import static com.whty.tool.handler.PukHandler.pukHandler;
import static com.whty.tool.handler.UsimHandler.usimHandler;
import static com.whty.tool.handler.GsmHandler.gsmHandler;
import static com.whty.tool.handler.CdmaHandler.cdmaHandler;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.junit.Test;


/**
 * 测试用例
 * @author Administrator
 *
 */
public class MfTest {
	@Test
	public void readFileTest() throws Exception {
		InputStream in = MfTest.class.getClassLoader().getResourceAsStream("application2.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
	}
	
	
	@Test
	public void str() throws Exception {
		String string = "C60990014083010183010A";
//		string = StringUtils.remove(string, " ");
//		Util util = new Util();
//		util.partiTlv(string);
		int index = string.indexOf("83");
		System.out.println(index);
//		System.out.println(s);
	}
	@Test
	public void test2() throws Exception{
		String[] args = {"1124.txt"};
		List<String> apdus = readFile(args);
		baseHandler(apdus,1);
	}
	@Test
	public void testPin() throws Exception{
		String[] args = {"1129pin.txt"};
		List<String> apdus = readFile(args);
		pinHandler(apdus,3);
	}
	@Test
	public void testPuk() throws Exception{
		String[] args = {"1129pin.txt"};
		List<String> apdus = readFile(args);
		pukHandler(apdus,2);
	}
	@Test
	public void testUsim() throws Exception{
		String[] args = {"1216.txt"};
		List<String> apdus = readFile(args);
		usimHandler(apdus,2);
	}
	@Test
	public void testCsim() throws Exception{
		String[] args = {"1216.txt"};
		List<String> apdus = readFile(args);
		csimHandler(apdus,2);
	}
	
	@Test
	public void testGsm() throws Exception{
		String[] args = {"7F20.txt"};
		List<String> apdus = readFile(args);
		gsmHandler(apdus,2);
	}
	@Test
	public void testCdma() throws Exception{
		String[] args = {"7f25.txt"};
		List<String> apdus = readFile(args);
		cdmaHandler(apdus,2);
	}
}
