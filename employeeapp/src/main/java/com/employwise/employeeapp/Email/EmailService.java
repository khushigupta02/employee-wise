package com.employwise.employeeapp.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendManagerNotification(String toEmail, String employeeName, String phone, String email) {
        String subject = "New Employee Assigned";
        String body = employeeName + " will now work under you. Mobile number is " + phone + " and email is " + email;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("khushi.codes04@gmail.com");
        message.setTo(toEmail);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }
}
