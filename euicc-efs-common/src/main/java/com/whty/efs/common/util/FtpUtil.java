package com.whty.efs.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.whty.oms.common.Constant;

/** 
 * 
 * @author luolai 
 */

/**
 * ftp上传，下载
 * 
 * @author why 2009-07-30
 * 
 */
public class FtpUtil {
	
	private static Logger logger = LoggerFactory.getLogger(FtpUtil.class);

	private FTPClient ftpClient = null;
	private String hostname;
	private int port;
	private String username;
	private String password;



	// 构造方法

	public FtpUtil(String hostname, int port, String username, String password, String remoteDir) {
		this(hostname, port, username, password);
		if (remoteDir == null) {
			remoteDir = "/";
		}
	}

	public FtpUtil(String hostname, int port, String username, String password) {
		this.hostname = hostname;
		this.port = port;
		this.username = username;
		this.password = password;
	}

	/**
	 * FTP登陆
	 * 
	 * @throws IOException
	 */
	public void login(String remoteFileDir) throws Exception {
		ftpClient = new FTPClient();
		ftpClient.configure(getFTPClientConfig());
		ftpClient.setDefaultPort(port);
		ftpClient.setControlEncoding("UTF-8");
		ftpClient.connect(hostname);
		if (!ftpClient.login(username, password)) {
			throw new Exception("FTP登陆失败，请检测登陆用户名和密码是否正确!");
		}
		ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
		boolean result = ftpClient.changeWorkingDirectory(remoteFileDir);
		System.out.println(result);
	}

	/**
	 * 得到配置
	 * 
	 * @return
	 */
	private FTPClientConfig getFTPClientConfig() {
		// 创建配置对象
		FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
		conf.setServerLanguageCode(FTPClient.DEFAULT_CONTROL_ENCODING);
		return conf;
	}

	/**
	 * 关闭FTP服务器
	 */
	public void closeServer() {
		try {
			if (ftpClient != null) {
				ftpClient.logout();
			}
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
	}

	/**
	 * 链接是否已经打开
	 * 
	 * @return
	 */
	public boolean serverIsOpen() {
		if (ftpClient == null) {
			return false;
		}
		return !ftpClient.isConnected();
	}

	/**
	 * 列表FTP文件
	 * 
	 * @param regEx
	 * @return
	 */
	public String[] listFiles(String regEx) {
		String[] names;
		try {
			names = ftpClient.listNames(regEx);
			if (names == null)
				return new String[0];
			return names;
		} catch (IOException e) {
			logger.error("", e);
		}
		return new String[0];
	}

	/**
	 * 取得FTP操作类的句柄
	 * 
	 * @return
	 */
	public FTPClient getFtpClient() {
		return ftpClient;
	}

	/**
	 * 上传
	 * 
	 * @throws Exception
	 */
	public boolean upload(InputStream localIn, String remoteFilePath) throws Exception {

		// this.createDir(new File(remoteFilePath).getParent());
		boolean result = ftpClient.storeFile(remoteFilePath, localIn);
		return result;
	}

	/**
	 * 是否存在FTP目录
	 * 
	 * @param dir
	 * @param ftpClient
	 * @return
	 */
	public boolean isDirExist(String dir) {
		try {
			int retCode = ftpClient.cwd(dir);
			return FTPReply.isPositiveCompletion(retCode);
		} catch (Exception e) {
			logger.error("", e);
			return false;
		}
	}

	/**
	 * 创建FTP多级目录
	 * 
	 * @param remoteFilePath
	 * @throws IOException
	 */
	public void createDir(String dir) throws IOException {
		// dir = dir.replaceAll("\\\\", "/");
		if (dir != null && !isDirExist(dir)) {
			File file = new File(dir);
			this.createDir(file.getParent());
			ftpClient.makeDirectory(dir);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param remoteFilePath
	 */
	public boolean delFile(String remoteFilePath) {
		try {
			return ftpClient.deleteFile(remoteFilePath);
		} catch (IOException e) {
			logger.error("", e);
		}
		return false;
	}

	/**
	 * 下载
	 * 
	 * @throws Exception
	 */
	public void download(String localFilePath, String remoteFilePath) throws Exception {
		OutputStream localOut = new FileOutputStream(localFilePath);
		this.download(localOut, remoteFilePath);
		localOut.close();
	}

	/**
	 * 下载
	 * 
	 * @throws Exception
	 */
	public boolean download(OutputStream localOut, String remoteFilePath) throws Exception {
		boolean result = ftpClient.retrieveFile(remoteFilePath, localOut);
		return result;

	}

	public FTPFile getFile(String remoteFilePath) throws Exception {
		FTPFile ftpFile = null;
		try {
			String fileName = remoteFilePath.substring(remoteFilePath.lastIndexOf("/") + 1, remoteFilePath.length());
			String parentPath = remoteFilePath.substring(0, remoteFilePath.lastIndexOf("/"));
			FTPFile[] fs = ftpClient.listFiles(parentPath);
			for (FTPFile ff : fs) {
				if (ff.getName().equals(fileName)) {
					ftpFile = ff;
				}
			}
		} catch (IOException e) {
			logger.error("", e);
		}
		return ftpFile;

	}

	/**
	 * 取得相对于当前连接目录的某个目录下所有文件列表
	 * 
	 * @param path
	 * @return
	 */
	// public List getFileList(String path) {
	// List list = new ArrayList();
	// DataInputStream dis;
	// try {
	// System.out.println(this.path + path);
	// dis = new DataInputStream(ftpClient.nameList(this.path + path));
	// String filename = "";
	// while ((filename = dis.readLine()) != null) {
	// String sfilename = new String(filename.getBytes("ISO-8859-1"), "utf-8");
	// list.add(sfilename);
	// }
	// } catch (IOException e) {
	// logger.error("", e);
	// }
	// return list;
	// }


}
