package com.whty.euicc.init;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import ch.qos.logback.core.util.StatusPrinter;


import com.whty.cache.Configurator;
/**
 * 加载配置
 * @author Administrator
 *
 */
public class InitLoad {
	private static Logger logger = LoggerFactory.getLogger(InitLoad.class);
	
	private static final String EUICC_HOME = "EUICC_HOME";
	
	public static void initHomePath() {
		String path = System.getenv(EUICC_HOME);
		if (null == path) {
			path = System.getProperty(EUICC_HOME);
			if (null == path) {
				path = new File("").getAbsolutePath();
				System.setProperty(EUICC_HOME, path);
			}
		}
		logger.info("EUICC_HOME:\t{}", path);
	}  
	
	public static void initLoadByStart(){
		String path = System.getProperty(EUICC_HOME);
		initLog(path);
		initCache(path);
		loadSpringContext(path);
	}
	
	private static void initLog(String path) {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		try {
			JoranConfigurator configurator = new JoranConfigurator();
			configurator.setContext(context);
			context.reset();
			configurator.doConfigure(path + "/conf/logback.xml");
		} catch (JoranException je) {
			je.printStackTrace();
		}
		StatusPrinter.printInCaseOfErrorsOrWarnings(context);
		logger.info("initLog complete.");
	}

	/**
	 * 加载cache组件的配置文件
	 * 
	 * @param rootPath
	 *            项目根路径
	 * @author gaof
	 * @throws java.net.MalformedURLException
	 */
	private static void initCache(String path) {
		try {
			File homeDir = new File(path);
            File file = new File(homeDir,"/conf/properties/cache.cfg");
            InputStream inputStream = new FileInputStream(file);
			Configurator.configure(inputStream);
			/*CacheUtil.put("abc", "abcval");
            String val = CacheUtil.getString("abc");
            StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
            byte[] b = stringRedisSerializer.serialize("abc");
            System.out.println(CacheUtil.getString("abc"));*/
		} catch (Exception e) {
			logger.error("读取cache配置文件出错", e);
		}
	}

	private static ApplicationContext loadSpringContext(String path) {
		File homeDir = new File(path);
		File root = new File(homeDir, "/conf/TlsServer.xml");
		return new FileSystemXmlApplicationContext("file:" + root.getAbsolutePath());
	}
}
