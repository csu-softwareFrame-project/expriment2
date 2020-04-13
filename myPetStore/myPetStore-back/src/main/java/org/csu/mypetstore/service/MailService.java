package org.csu.mypetstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service("mailService")
public class MailService {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;


    //发送普通邮件
    public void sendSimpleMail(String to,String title,String content) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper;
        helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(from);//发送人
        helper.setTo(to);//邮件接收人
        helper.setCc(from);//抄送邮件接收人
        helper.setSubject(title);//邮件标题
        helper.setText(content, true);//可以使用html
        mailSender.send(mimeMessage);
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom(from);
//        message.setTo(to);//发送对象
//        message.setSubject(title);//标题
//        message.setText(content);//正文
//        mailSender.send(message);
    }

}

