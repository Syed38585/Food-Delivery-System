package com.Messaging.MailService;

import com.Messaging.Dto.MailDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class Mailservice {

    @Autowired
    JavaMailSender mailSender;


    @Value("${spring.mail.username}")
    private String sender_mail;

    @Async
    public void sendmail(MailDto dto) {
        SimpleMailMessage message = new SimpleMailMessage();

        if (dto == null || dto.getUser_mail() == null || dto.getContent() == null) {
            throw new IllegalArgumentException("Invalid mail data");
        }
        message.setFrom(sender_mail);
        message.setTo(dto.getUser_mail());
        message.setText(dto.getContent());
        message.setSubject("REGISTRATION ON TOMATO");

        try {
            mailSender.send(message);
        } catch (MailException e) {
            throw new RuntimeException(e);
        }
    }
}
