package uni.project.online.shop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendAccountActivation(String token, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Account activation");
        String link = "http://localhost:5173/email-confirmation?token=" + token;
        message.setText("Click link to activate account: " + link);
        emailSender.send(message);
    }

    public void sendResetPasswordEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Reset password");
        String link = "http://localhost:8080/auth/reset-forgotten-password?token=" + token;
        message.setText("Click link to reset password: " + link);
        emailSender.send(message);
    }

    public void sendPromoCode(String code, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Sign up for newsletter promo code");
        message.setText("Thank you for your subscribe! Enjoy your 5% of promo code: " + code);
        emailSender.send(message);
    }

    public void sendOrderCompleted(String email, String emailText) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Order completed");
        message.setText(emailText);
        emailSender.send(message);
    }
}
