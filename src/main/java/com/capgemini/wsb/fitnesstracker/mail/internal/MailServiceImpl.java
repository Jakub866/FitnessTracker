package com.capgemini.wsb.fitnesstracker.mail.internal;

import com.capgemini.wsb.fitnesstracker.mail.api.EmailDto;
import com.capgemini.wsb.fitnesstracker.mail.api.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class MailServiceImpl implements EmailSender {

    private final JavaMailSender mailSender;

    @Override
    public void send(EmailDto emailDto) {
        final SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailDto.getRecipient());
        message.setSubject(emailDto.getSubject());
        message.setText(emailDto.getBody());
        System.out.println("Sending email to: " + emailDto.getRecipient() + " with subject: " + emailDto.getSubject() + " and body: " + emailDto.getBody());
//        mailSender.send(message); Odkomentować w celu wysłania maila
    }
}