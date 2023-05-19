package com.kloc.emailApp.serviveImpl;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kloc.emailApp.entity.EmailDetails;
import com.kloc.emailApp.service.EmailService;

import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService{
		@Autowired
		private JavaMailSender javaMailSender;
		
	@Value("${spring.mail.username}")	private String sender;
	@Override
	public String sendSimplemail(EmailDetails details) {
		try {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(sender);
		mailMessage.setTo(details.getRecipient());
		mailMessage.setText(details.getMsgBody());
		mailMessage.setSubject(details.getSubject());
		
		
		
		javaMailSender.send(mailMessage);

		return "Mail Sent Successfully";
		}catch(Exception e) {
			return "Error while sending mail";
		}
	}

	@Override
	public String sendMailWithAttachment(EmailDetails details) {
	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	MimeMessageHelper mimeMessageHelper;
	
	try {
		mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setFrom(sender);
		mimeMessageHelper.setTo(details.getRecipient());
		mimeMessageHelper.setText(details.getMsgBody());
		mimeMessageHelper.setSubject(details.getSubject());
	 
		FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
	
		
		mimeMessageHelper.addAttachment(file.getFilename(), file);
	javaMailSender.send(mimeMessage);
	
	return "Mail Sent Successfully";
	
	}catch(Exception e) {
		return "Error while sending mail";
	}
	
	
		
	}

	@Override
	public String sendMailToMultipleUsers(EmailDetails details) {
		try {
		for(int i=0;i<details.getRecipient().length;i++) {
			
			//creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			
			mailMessage.setFrom(sender);
			mailMessage.setTo((details.getRecipient())[i]);
			mailMessage.setCc(details.getCc());
			mailMessage.setBcc(details.getBcc());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());
			
			javaMailSender.send(mailMessage);
			
		}
		return "Mail sent Successfully";
		}
		catch(Exception e) {
			return "Error while Sending Mail";
		}
	}

}
