package com.pauloricardo.api.Service.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarCodigo(String email, String codigo){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(email);
        msg.setSubject("Código de Verificação");
        msg.setText(
                """
               Seu código de verificação é: %s
               
               Ele expira em 10 Minutos
                """.formatted(codigo));

                
        mailSender.send(msg);
    }

    public void resetSenha(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Recuperação de senha");
        message.setText("Seu código de recuperação é: " + code);

        mailSender.send(message);
    }
}
