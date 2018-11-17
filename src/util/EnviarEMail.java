/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

/**
 *
 * @author Pituba
 */
public class EnviarEMail {    

    
    public EnviarEMail(String email, String assuntoenviado, String mensagemenviada) throws MessagingException {
        try{
        String host = "smtp.gmail.com";
        String user = "prefeiturasjn.2018@gmail.com";
        String pass = "nsjpref2018";
        String to = email;
        String assunto = assuntoenviado;
        String mensagem = mensagemenviada; 
        boolean sessionDebug = false;
              
        Properties propriedades = System.getProperties();
        
        propriedades.put("mail.smtp.starttls.enable", "true");
        propriedades.put("mail.smtp.host", host);
        propriedades.put("mail.smtp.port", "587");
        propriedades.put("mail.smtp.auth", "true");
        propriedades.put("mail.smtp.starttls.required", "true");
        
        java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        Session mailSession = Session.getDefaultInstance(propriedades, null);
        mailSession.setDebug(sessionDebug);
        Message msg = new MimeMessage(mailSession);
        msg.setFrom(new InternetAddress(user));
        InternetAddress[] address = {new InternetAddress(to)};
        msg.setRecipients(Message.RecipientType.TO, address);
        msg.setSubject(assunto);
        msg.setSentDate(new Date());
        msg.setText(mensagem);
        
        Transport transport = mailSession.getTransport("smtp");
        transport.connect(host, user, pass);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        JOptionPane.showMessageDialog(null, "O Código de validação foi enviado ao seu e-mail!");
    }catch(Exception ex){
        JOptionPane.showMessageDialog(null, "Ocorreu um erro, por favor confira o endereço de e-mail e tente novamente! ERRO: " + ex); 
    }
  }
}
