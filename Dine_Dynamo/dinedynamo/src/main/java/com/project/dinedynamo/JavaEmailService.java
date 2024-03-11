package com.project.dinedynamo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class JavaEmailService {

    @Autowired
    JavaMailSender mailService;

    public void message(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("tarunmenon383@mgail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailService.send(message);
    }
}
