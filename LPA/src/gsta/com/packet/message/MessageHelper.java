package gsta.com.packet.message;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.StringUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
		// 加载所有MsgBody的实现类到映射池

		// 1.通过注解加载
		try {
			MessageHelper.scanClass("com/whty/euicc/rsp/packets/message/");
		} catch (IOException e) {
			logger.info("load all implement class of the MsgBody.", e);
		}
		
	}
	
	public final static Gson gs = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();


	/**
	 * 消息数据JSON转换实例
	 */
	public static EuiccMsg<MsgBody> deserialize(String json, String msgType) throws JsonParseException {
		// 报文对象
		MsgBody pbody = null;
		
		Class<?> clazz = pool.get(msgType);
		if (null != clazz) {
			//throw new JsonParseException("The class of '" + header.getMsgType() + "' not found.");
			// 构造报文体
			pbody = (MsgBody) new GsonBuilder().create().fromJson(json, clazz);
		}
		return new EuiccMsg<MsgBody>(pbody);
	}

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
		if (MsgBody.class.isAssignableFrom(clazz) && clazz != MsgBody.class && clazz.isAnnotationPresent(MsgType.class)) {
			MsgType mt = clazz.getAnnotation(MsgType.class);
			pool.put(mt.value(), clazz);
		}
	}

}
