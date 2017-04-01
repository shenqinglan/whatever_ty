// Copyright (C) 2012 WHTY
package com.whty.euicc.bizflow.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import com.whty.euicc.common.exception.InvalidParameterException;

public class BizTemplateUtil {

	private static final Logger logger = LoggerFactory.getLogger(BizTemplateUtil.class);

	/**
	 * @throws BizTemplateException 
	 * 根据step找到当前xmlstep片段
	 * 
	 * @author dengjun
	 * @since 2014-9-27
	 * @param xbt
	 * @param fileName
	 * @param step
	 * @return
	 * @throws BizTemplateException
	 * @throws
	 */
	public static XmlStep findXmlStep(BizTemplate xbt, String step) throws InvalidParameterException, BizTemplateException {
		TplAssert.isNotNull(xbt,"模板对象为空");

		TplAssert.isTrue(xbt.getStatus(),"The XmlBusTemplate object is disable.");

		List<XmlStep> xmlStepList = xbt.getXmlStep();
		
		TplAssert.isNotEmpty(xmlStepList,"The XmlBusTemplate object is empty.");
		
		TplAssert.isNotNull(step,"步骤数为空");

		for (XmlStep xmlStep : xmlStepList) {
			if (step.equals(xmlStep.getStepid())) {
				return xmlStep;
			}
		}

		throw new BizTemplateException(BizTemplateException.ERROR_600004, "parameter:step=" + step);
	}
	

	/**
	 * 通过给web端读取并校验，不可缓存
	 * 
	 * @throws XmlPullParserException
	 * @throws java.io.IOException
	 */
	public static BizTemplate parseXmlBusTemplate(File xmlFile, String charset) throws BizTemplateException {

		InputStream is = null;
		try {
			is = new FileInputStream(xmlFile);

			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(true);
			XmlPullParser xpp = factory.newPullParser();
			xpp.setInput(is, charset);

			BizTemplate xbt = null;
			int eventType = xpp.getEventType();
			do {
				if (XmlPullParser.START_TAG == eventType) {
					String tagName = xpp.getName();

					if ("businesstemplet".equalsIgnoreCase(tagName)) {

						xbt = buildXmlBusTemplet(xpp);
					} else if ("step".equalsIgnoreCase(tagName)) {

						xbt.addXmlStep(buildXmlStep(xpp));
					} else if ("from".equalsIgnoreCase(tagName)) {

						xbt.getCurXmlStep().setXmlForm(buildXmlForm(xpp));
					} else if ("job".equalsIgnoreCase(tagName)) {

						xbt.getCurXmlStep().setXmlJob(buildXmlJob(xpp));
					} else if ("to".equalsIgnoreCase(tagName)) {

						xbt.getCurXmlStep().setXmlTo(buildXmlTo(xpp));
					} else if ("result".equalsIgnoreCase(tagName)) {
						xbt.getCurXmlStep().addXmlResult(buildXmlResult(xpp));
					}
				}
				eventType = xpp.next();
			} while (XmlPullParser.END_DOCUMENT != eventType);

			return xbt;
		} catch (FileNotFoundException e) {
			throw new BizTemplateException(BizTemplateException.ERROR_600000, e);
		} catch (XmlPullParserException e) {
			throw new BizTemplateException(BizTemplateException.ERROR_600001, e);
		} catch (IOException e) {
			throw new BizTemplateException(BizTemplateException.ERROR_600002, e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					logger.error("Error while close the template file:" + xmlFile, e);
				}
			}
		}
	}

	private static BizTemplate buildXmlBusTemplet(XmlPullParser xpp) {
		BizTemplate xbt = new BizTemplate();

		int count = xpp.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = xpp.getAttributeName(i);
			if ("name".equalsIgnoreCase(name)) {
				xbt.setName(xpp.getAttributeValue(i));
			} else if ("status".equalsIgnoreCase(name)) {
				xbt.setStatus(Boolean.valueOf(xpp.getAttributeValue(i)));
			}
		}

		return xbt;
	}

	private static XmlStep buildXmlStep(XmlPullParser xpp) {
		XmlStep xs = new XmlStep();
		int count = xpp.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = xpp.getAttributeName(i);
			if ("stepid".equalsIgnoreCase(name)) {
				xs.setStepid(xpp.getAttributeValue(i));
			} else if ("name".equalsIgnoreCase(name)) {
				xs.setName(xpp.getAttributeValue(i));
			}
		}
		return xs;
	}

	private static XmlForm buildXmlForm(XmlPullParser xpp) {
		XmlForm xf = new XmlForm();
		int count = xpp.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = xpp.getAttributeName(i);
			if ("description".equalsIgnoreCase(name)) {
				xf.setDescription(xpp.getAttributeValue(i));
			} else if ("code".equalsIgnoreCase(name)) {
				xf.setCode(xpp.getAttributeValue(i));
			} else if ("command".equalsIgnoreCase(name)) {
				xf.setCommand(xpp.getAttributeValue(i));
			} else if ("last".equalsIgnoreCase(name)) {
				xf.setLast(xpp.getAttributeValue(i));
			}
		}
		return xf;
	}

	private static XmlJob buildXmlJob(XmlPullParser xpp) {
		XmlJob xj = new XmlJob();
		int count = xpp.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = xpp.getAttributeName(i);
			if ("description".equalsIgnoreCase(name)) {
				xj.setDescription(xpp.getAttributeValue(i));
			} else if ("code".equalsIgnoreCase(name)) {
				xj.setCode(xpp.getAttributeValue(i));
			}
		}
		return xj;
	}

	private static XmlTo buildXmlTo(XmlPullParser xpp) {
		XmlTo xt = new XmlTo();
		int count = xpp.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = xpp.getAttributeName(i);
			if ("description".equalsIgnoreCase(name)) {
				xt.setDescription(xpp.getAttributeValue(i));
			} else if ("code".equalsIgnoreCase(name)) {
				xt.setCode(xpp.getAttributeValue(i));
			} else if ("command".equalsIgnoreCase(name)) {
				xt.setCommand(xpp.getAttributeValue(i));
			}
		}
		return xt;
	}

	private static XmlResult buildXmlResult(XmlPullParser xpp) {
		XmlResult xr = new XmlResult();
		int count = xpp.getAttributeCount();
		for (int i = 0; i < count; i++) {
			String name = xpp.getAttributeName(i);
			if ("description".equalsIgnoreCase(name)) {
				xr.setDescription(xpp.getAttributeValue(i));
			} else if ("status".equalsIgnoreCase(name)) {
				xr.setStatus(xpp.getAttributeValue(i));
			} else if ("step".equalsIgnoreCase(name)) {
				xr.setStep(xpp.getAttributeValue(i));
			}
		}
		return xr;
	}
}
