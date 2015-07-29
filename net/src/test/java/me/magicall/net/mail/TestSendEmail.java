package me.magicall.net.mail;

import me.magicall.net.mail.notok.MailSenderInfo;
import me.magicall.net.mail.notok.SimpleMailSender;

import org.junit.Test;

public class TestSendEmail {

	@Test
	public void a() {
		//这个类主要是设置邮件  
		final MailSenderInfo mailInfo = new MailSenderInfo();
		mailInfo.setMailServerHost("smtp.163.com");
		mailInfo.setMailServerPort("25");
		mailInfo.setValidate(true);
		mailInfo.setUserName("lwj621@163.com");
		mailInfo.setPassword("1QAZSE4163");//您的邮箱密码   
		mailInfo.setFromAddress("lwj621@163.com");
		mailInfo.setToAddress("lwj621@163.com");
		mailInfo.setSubject("设置邮箱标题");
		mailInfo.setContent("设置邮箱内容");
		//这个类主要来发送邮件  
		final SimpleMailSender sms = new SimpleMailSender();
		sms.sendTextMail(mailInfo);//发送文体格式   
		SimpleMailSender.sendHtmlMail(mailInfo);//发送html格式  
	}
}
