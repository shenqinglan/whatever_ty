package com.whty.euicc.bizflow.engine;

import java.io.File;
import java.io.FilenameFilter;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.whty.cache.CacheUtil;
import com.whty.euicc.bizflow.template.BizTemplate;
import com.whty.euicc.bizflow.template.BizTemplateException;
import com.whty.euicc.bizflow.template.BizTemplateUtil;
import com.whty.euicc.bizflow.template.XmlForm;
import com.whty.euicc.bizflow.template.XmlStep;
import com.whty.euicc.common.exception.NullParameterException;

@Service
public class BizTemplateManager {
	private static Logger log = LoggerFactory.getLogger(BizTemplateManager.class);
	/**
	 * 模板编码
	 */
	@Value("${tpl.charset}")
	private String charset;

	private BizTemplateCachePloy cachePloy;

	/**
	 * 初始化业务模板
	 * 
	 * @throws NullParameterException
	 */
	@PostConstruct
	public void initBusinessTemplate() {
		
		buildCachePloy();
		
		String tsmHome = getTsmHome();

		File dir = new File(tsmHome, "templateflow");
		if (dir.exists()) {
			init(dir, charset);
		} else {
			log.debug("模板目录({})不存在", dir.getAbsolutePath());
		}
	}

	private void buildCachePloy() {
		this.cachePloy = new BizTemplateCachePloy() {

			@Override
			public BizTemplate get(String key) {
				return (BizTemplate) CacheUtil.get(key, BizTemplate.class);
			}

			@Override
			public void set(String key, BizTemplate bt) {
				CacheUtil.put(key, bt);
				CacheUtil.persist(key);
			}
		};
	}

	/**
	 * 获取EUICC_HOME
	 * 
	 * @return
	 */
	private static String getTsmHome() {
		String tsmHome = System.getProperty("EUICC_HOME");
		if (tsmHome == null) {
			String dv = BizTemplateManager.class.getResource("/").toExternalForm();
			tsmHome = System.getProperty("EUICC_HOME", dv);
		}
		return tsmHome;
	}

	/**
	 * 初始化加载模板数据
	 * 
	 * @param tlpDir
	 * @throws BizTemplateException
	 */
	private void init(File tmpDir, String charset) {
		File[] files = tmpDir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});

		for (File file : files) {
			try {
				BizTemplate bt = BizTemplateUtil.parseXmlBusTemplate(file, charset);
				XmlStep step = bt.getCurXmlStep();
				XmlForm form = step.getXmlForm();
				String cmd = form.getCommand();

				this.cachePloy.set(buildKey(cmd), bt);
				log.debug("成功加载模板文件({})", file.getName());
			} catch (BizTemplateException e) {
				log.debug("失败加载模板文件(" + file.getName() + ")", e);
			}
		}

	}

	public BizTemplate getBizTemplate(String msgType) {
		return this.cachePloy.get(buildKey(msgType));
	}

	private static String buildKey(String key) {
		return "TSM_BIZ_TPL_" + key;
	}
}
