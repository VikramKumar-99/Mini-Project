package com.demo.project.security.services;







import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.stereotype.Service;

import com.demo.project.models.Employee;

@Service
public class EmailService {
	@Autowired
    private JavaMailSender javaMailSender;
	

	
	@Autowired
	public EmailService( JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	
	public void sendForgotPasswordEmail(Employee employee, String text)throws MailException {

        SimpleMailMessage msg = new SimpleMailMessage();
        
        msg.setTo(employee.getEmail());

        msg.setSubject("The Forgot password request" + employee.getUsername());
        msg.setText(text);

        javaMailSender.send(msg);

    }
	
	public void sendEmail(Employee employee,String Subject, String text)throws MailException {

        SimpleMailMessage msg = new SimpleMailMessage();
        
        msg.setTo(employee.getEmail());

        msg.setSubject(Subject);
        msg.setText(text);

        javaMailSender.send(msg);

    }


	public void sendTestEmail() {
		// TODO Auto-generated method stub
		
	}
	
}

