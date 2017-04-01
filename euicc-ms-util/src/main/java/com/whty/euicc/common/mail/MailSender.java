package com.whty.euicc.common.mail;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

/**
 * 邮件发送器
 * 
 * @author xiaoxiao
 *
 */
public class MailSender {

	/**
	 * 发送邮件的props文件
	 */
	private final transient Properties props = System.getProperties();

	/**
	 * 邮件服务器登录验证
	 */
	private transient MailAuthenticator authenticator;

	/**
	 * 邮箱session
	 */
	private transient Session session;

	/**
	 * 初始化邮件发送器
	 * 
	 * @param smtpHostName
	 * @param username
	 * @param password
	 */
	public MailSender(final String smtpHostName, final String username, final String password) {
		init(username, password, smtpHostName);
	}

	/**
	 * 发送邮件
	 * 
	 * @param recipient
	 * @param subject
	 * @param content
	 * @throws javax.mail.internet.AddressException
	 * @throws javax.mail.MessagingException
	 */
	public void send(String recipient, String subject, Object content) throws Exception {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		// 设置邮件主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}

	/**
	 * 群发邮件
	 * 
	 * @param recipients
	 * @param subject
	 * @param content
	 * @throws javax.mail.internet.AddressException
	 * @throws javax.mail.MessagingException
	 */
	public void send(List<String> recipients, String subject, Object content)
			throws AddressException, MessagingException {
		// 创建mime类型邮件
		final MimeMessage message = new MimeMessage(session);
		// 设置发信人
		message.setFrom(new InternetAddress(authenticator.getUsername()));
		// 设置收件人们
		final int num = recipients.size();
		InternetAddress[] addresses = new InternetAddress[num];
		for (int i = 0; i < num; i++) {
			addresses[i] = new InternetAddress(recipients.get(i));
		}
		message.setRecipients(RecipientType.TO, addresses);
		// 设置邮件主题
		message.setSubject(subject);
		// 设置邮件内容
		message.setContent(content.toString(), "text/html;charset=utf-8");
		// 发送
		Transport.send(message);
	}

	/**
	 * 初始化
	 * 
	 * @param username
	 * @param password
	 * @param smtpHostName
	 */
	private void init(String username, String password, String smtpHostName) {
		// 初始化props
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", smtpHostName);
		props.put("mail.smtp.connectiontimeout", "5000");
		props.put("mail.smtp.timeout", "5000");
		// 验证
		authenticator = new MailAuthenticator(username, password);
		// 创建session
		session = Session.getInstance(props, authenticator);
	}


	/**
	 * 服务器邮箱登陆验证
	 * @author xiaoxiao
	 *
	 */
	private class MailAuthenticator extends Authenticator {

		/**
		 * 用户名
		 */
		private String username;
		/**
		 * 密码
		 */
		private String password;

		/**
		 * 初始化用户名和密码
		 * 
		 * @param username
		 * @param password
		 */
		public MailAuthenticator(String username, String password) {
			this.username = username;
			this.password = password;
		}

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
		
		public String getUsername() {
			return username;
		}

	}
}
