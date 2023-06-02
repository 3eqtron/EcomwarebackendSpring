package com.eqtron.Management.System.utils;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EmailUtils {
    @Autowired
    private JavaMailSender emailsender;

    public void sendsimplemessage(String to, String subject, String text, List<String>list){
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom("brenda.gutkowski@ethereal.email");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        if (list != null && list.size()>0)
        message.setCc(getCcArray(list));
        emailsender.send(message);

    }
    private String[] getCcArray(List<String>cclist){
        String[]cc=new String[cclist.size()];
        for (int i=0;i<cclist.size();i++){
            cc[i]=cclist.get(i);
        }

        return cc;
    }
    public void forgotmail(String to,String subject,String password)throws MessagingException {
        MimeMessage message =emailsender.createMimeMessage();
        MimeMessageHelper helper =new MimeMessageHelper(message,true);
        helper.setFrom("brenda.gutkowski@ethereal.email");
        helper.setTo(to);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Vos Informations de Management System</b><br><b>Email: </b> " + to + " <br><b>Mot de Passe: </b> " + password + "<br><a href=\"http://localhost:4200/\">Clicker ici pour se connecter</a></p>";
    message.setContent(htmlMsg,"text/html");
    emailsender.send(message);
    }
}
