package com.whty.efs.packets.message;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * 消息数据JSON转换帮助类
 * 
 * @author baojw@whty.com.cn
 * @date 2014年9月26日 下午1:28:32
 */
public class MessageHelper {
	private static final Logger logger = LoggerFactory.getLogger(MessageHelper.class);

	public final static Map<String, Class<?>> pool = new HashMap<String, Class<?>>();

	static {
		// 加载所有Body的实现类到映射池

		// 1.通过注解加载
		try {
			MessageHelper.scanClass("com/whty/efs/packets/message/");
			MessageHelper.scanClass("com/whty/efs/plugins/pboc/mtps/message/");
		} catch (IOException e) {
			logger.info("load all implement class of the Body.", e);
		}
		
		// 2.通过配置文件加载

		InputStream in = MessageHelper.class.getClassLoader().getResourceAsStream("config/properties/msgtype.properties");
		if (null != in) {
			Properties prop = new Properties();
			try {
				prop.load(in);

				Enumeration<Object> enu = prop.keys();
				while (enu.hasMoreElements()) {
					String msgType = (String) enu.nextElement();
					String className = prop.getProperty(msgType);
					if (msgType == null || msgType.trim().length() == 0 || className == null || className.trim().length() == 0) {
						continue;
					}

					try {
						pool.put(msgType, Class.forName(className));
					} catch (ClassNotFoundException e) {
						logger.info("load MsgType:" + msgType + "class:" + className, e);
					}
				}

			} catch (IOException e) {
				logger.info("load all implement class of the Body.", e);
			} finally {
				if (null != in) {
					try {
						in.close();
					} catch (IOException e) {
					}
				}
			}
		}
	}


	/**
	 * 消息数据JSON转换实例
	 */
	public final static GsonBuilder GSONBUILDER4MSG = new GsonBuilder().registerTypeAdapter(EuiccEntity.class, new JsonDeserializer<EuiccEntity<Body>>() {

		@Override
		public EuiccEntity<Body> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			
			// 报文对象
			JsonObject jsonObject = json.getAsJsonObject();

			// 报文头
			JsonObject jsonHeader = jsonObject.getAsJsonObject("header");
			if (null == jsonHeader) {
				throw new JsonParseException("The header data must not be null.");
			}

			// 构造报文头
			Header header = context.deserialize(jsonHeader, Header.class);			
			
			if(!StringUtils.hasText(header.getMsgType())){
				throw new JsonParseException("The \"msgType\" must not be empty.");
			}
			
			Class<?> clazz = pool.get(header.getMsgType());
			if (null == clazz) {
				throw new JsonParseException("The class of '" + header.getMsgType() + "' not found.");
			}
			
			JsonObject jsonBody = jsonObject.getAsJsonObject("body");
			if (null == jsonBody) {
				throw new JsonParseException("The body data must not be null.");
			} 
			// 构造报文体
			Body pbody = context.deserialize(jsonBody, clazz);

			return new EuiccEntity<Body>(header,pbody);
		}
	});

	private static void scanClass(String basePackage) throws IOException {
		String dir = StringUtils.replace(basePackage, ".", "/");

		if (!dir.endsWith("/")) {
			dir += "/";
		}

		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

		Resource[] resources = resolver.getResources("classpath*:" + dir + "**/**.class");
		for (Resource resource : resources) {
			Class<?> clazz = forClass(resource, basePackage);
			if (null != clazz) {
				register(clazz);
			}
		}
	}

	private static Class<?> forClass(Resource resource, String dir) {
		try {
			String path = resource.getURI().toString();
			int start = path.lastIndexOf(dir);
			int end = path.lastIndexOf(".class");

			String className = path.substring(start, end);
			className = StringUtils.replace(className, "/", ".");

			return Class.forName(className);
		} catch (IOException e) {
			logger.debug("ScanClass:", e);
		} catch (ClassNotFoundException e) {
			logger.debug("ScanClass:", e);
		}
		return null;
	}

	private static void register(Class<?> clazz) {
		if (Body.class.isAssignableFrom(clazz) && clazz != Body.class && clazz.isAnnotationPresent(MsgType.class)) {
			MsgType mt = clazz.getAnnotation(MsgType.class);
			pool.put(mt.value(), clazz);
		}
	}

}
