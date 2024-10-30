package com.pp.referrallink.service;

import com.pp.referrallink.entity.ContactForm;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

  private final JavaMailSender mailSender;

  public EmailService(@Autowired JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void sendContactEmail(ContactForm contactForm) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo("referrallink.company@gmail.com"); // Corporate email
    message.setSubject("Customer request " + new Random().nextInt(1, 100000));
    message.setText("Message from: " + contactForm.getEmail() + "\n\n" + contactForm.getMessage());

    mailSender.send(message);
  }
}