package me.magicall.net.mail.notok;

/**  
 * 发送邮件需要使用的基本信息  
 */
import java.util.Properties;

public class MailSenderInfo {
	// 发送邮件的服务器的IP和端口   
	private String mailServerHost;
	private String mailServerPort = "25";
	// 邮件发送者的地址   
	private String fromAddress;
	// 邮件接收者的地址   
	private String toAddress;
	// 登陆邮件发送服务器的用户名和密码   
	private String userName;
	private String password;
	// 是否需要身份验证   
	private boolean validate = false;
	// 邮件主题   
	private String subject;
	// 邮件的文本内容   
	private String content;
	// 邮件附件的文件名   
	private String[] attachFileNames;

	/**
	 * 获得邮件会话属性
	 */
	public Properties getProperties() {
		final Properties p = new Properties();
		p.put("mail.smtp.host", mailServerHost);
		p.put("mail.smtp.port", mailServerPort);
		p.put("mail.smtp.auth", validate ? "true" : "false");
		return p;
	}

	public String getMailServerHost() {
		return mailServerHost;
	}

	public void setMailServerHost(final String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return mailServerPort;
	}

	public void setMailServerPort(final String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(final boolean validate) {
		this.validate = validate;
	}

	public String[] getAttachFileNames() {
		return attachFileNames;
	}

	public void setAttachFileNames(final String... fileNames) {
		attachFileNames = fileNames;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(final String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(final String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String textContent) {
		content = textContent;
	}
}