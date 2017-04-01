package com.whty.euicc.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.whty.euicc.common.caputils.CapInfo;
import com.whty.euicc.common.caputils.CapTools;
import com.whty.euicc.common.exception.BusinessException;


/**
 * 解析上传附件工具类
 * 
 * 
 */
public class AnalysisFile {

	private static final Logger logger = LoggerFactory
			.getLogger(AnalysisFile.class);

	/**
	 * 解析BIN文件
	 * 
	 * @param binFile
	 *            上传的BIN文件
	 * @return
	 * @throws BusinessException
	 */
	public static String analysisBinFile(MultipartFile binFile)
			throws BusinessException {
		String result = "";
		try {
			// 解析BIN文件
			if (binFile != null
					&& !CheckEmpty.isEmpty(binFile.getOriginalFilename())) {
				if (binFile.isEmpty()) {
					logger.info("BIN文件内容不能为空！");
					throw new BusinessException("BIN文件内容不能为空！");
				}
				logger.debug("BIN文件内容为：{}",
						Converts.bytesToHex(binFile.getBytes()));
				result = Converts.bytesToHex(binFile.getBytes());
			}
		} catch (IOException e) {
			logger.info("BIN文件读取错误！");
			throw new BusinessException("BIN文件读取错误！");
		}
		return result;
	}

	/**
	 * 解析TXT文件
	 * 
	 * @param txtFile
	 *            上传的TXT文件
	 * @return
	 * @throws BusinessException
	 */
	public static String analysisTxtFile(MultipartFile txtFile)
			throws BusinessException {
		String result = "";
		try {
			// 解析TXT文件
			InputStream fis = txtFile.getInputStream();
			InputStreamReader isr = new InputStreamReader(fis, "GBK");
			BufferedReader br = new BufferedReader(isr);
			String txtFileStr = "";
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				txtFileStr = txtFileStr + lineTxt;
			}
			result = txtFileStr.toUpperCase();
		} catch (IOException e) {
			logger.info("TXT文件读取错误！");
			throw new BusinessException("TXT文件读取错误！");
		}
		return result;
	}

	/**
	 * 解析CAP包
	 * 
	 * @param file
	 *            上传的CAP包
	 * @param type
	 *            CAP包类型（0：主包，1：从包）
	 * @return
	 * @throws BusinessException
	 */
	public static HashMap<String,Object> analysisCapFile(MultipartFile file, int type)
			throws BusinessException {
		HashMap<String,Object> example = new HashMap<String,Object>();

		CapTools ct = new CapTools();
		CapInfo capInfo = new CapInfo();
		// 文件名
		String fileName = file.getOriginalFilename();
		// 文件格式
		String fileType = fileName.substring(fileName.indexOf(".") + 1,
				fileName.length());

		// 判断文件内容是否为空
		if (file.isEmpty()) {
			example.put("errorInfo", fileName + "文件无法解析！");
			logger.debug(fileName + "文件无法解析！");
			return example;
		}

		// 判断文件是否为CAP格式
		if (!"cap".equalsIgnoreCase(fileType)) {
			if ("txt".equalsIgnoreCase(fileType)) {
				capInfo = analysisTxtCapFile(file);
				capInfo.setCapFileName(fileName);
				logger.debug(fileName + "文件解析成功！");
				example.put("capInfo", capInfo);
			}
			if ("dex".equalsIgnoreCase(fileType)) {
				capInfo = analysisDexCapFile(file);
				capInfo.setCapFileName(fileName);
				logger.debug(fileName + "文件解析成功！");
				example.put("capInfo", capInfo);
			}
			if (!"txt".equalsIgnoreCase(fileType)
					&& !"dex".equalsIgnoreCase(fileType)) {
				example.put("errorInfo", fileName + "文件不是cap格式！");
				logger.debug(fileName + "文件不是cap格式！");
				return example;
			}
		} else {
			// 读取CAP文件
			if (ct.readCapFile2(file)) {
				if (ct.packageAID == null) {
					example.put("errorInfo", fileName + "文件PackageAid缺失！");
					logger.debug(fileName + "文件PackageAid缺失！");
					return example;
				}
				capInfo.setPackageAID(ct.packageAID == null ? ""
						: ct.packageAID.toString());// 这里的写法有点多余啊，上面已经做了判断这里何必要在判断一次
				capInfo.setAppletAID(ct.appletAID);
				if (ct.appletAID.size() > 0) {
					capInfo.setInstanceAID(ct.appletAID.get(0).toString());
				} else {
					if (type == 0) {
						example.put("errorInfo", fileName + "文件InstanceAid缺失！");
						logger.debug(fileName + "文件InstanceAid缺失！");
						return example;
					} else {
						capInfo.setInstanceAID("");
					}
				}
				if (ct.fetchData()) {
					capInfo.setCapData(ct.capData);
					capInfo.setCapFileName(fileName);
					logger.debug(fileName + "文件解析成功！");
					example.put("capInfo", capInfo);
				}
			}
		}
		return example;
	}

	/**
	 * 解析txt格式的cap包
	 * 
	 * @param file
	 * @return
	 * @throws java.io.IOException
	 */
	public static CapInfo analysisTxtCapFile(
			MultipartFile file) throws BusinessException {
		CapInfo capInfo = new CapInfo();
		String txtFileStr = analysisTxtFile(file);
		// 自定义取前16个字节为packageAID
		capInfo.setPackageAID(txtFileStr.substring(0, 32));
		capInfo.setCapData(txtFileStr);
		return capInfo;
	}

	/**
	 * 解析dex格式的cap包
	 * 
	 * @param file
	 * @return
	 * @throws BusinessException
	 */
	public static CapInfo analysisDexCapFile(
			MultipartFile file) throws BusinessException {
		CapInfo capInfo = new CapInfo();
		try {
			// 解析dex文件
			String dexFileStr = Converts.bytesToHex(file.getBytes());
			dexFileStr = "04" + StringUtil.getLength(dexFileStr.length() / 2)
					+ dexFileStr;
			dexFileStr = dexFileStr.toUpperCase();
			// 自定义取前16个字节为packageAID
			capInfo.setPackageAID(dexFileStr.substring(0, 32));
			capInfo.setCapData(dexFileStr);
		} catch (IOException e) {
			logger.info("DEX文件读取错误！");
			throw new BusinessException("DEX文件读取错误！");
		}
		return capInfo;
	}

}
