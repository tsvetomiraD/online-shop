package uni.project.online.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Value("${spring.mail.host}")
    String host;
    @Value("${spring.mail.port}")
    String port;
    @Value("${spring.mail.username}")
    String username;
    @Value("${spring.mail.password}")
    String password;
    @Value("${spring.mail.properties.mail.smtp.auth}")
    String enableAuth;
    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    String enableTls;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(Integer.parseInt(port));
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties mailSenderProps = new Properties();
        mailSenderProps.put("mail.smtp.auth", Boolean.parseBoolean(enableAuth));
        mailSenderProps.put("mail.smtp.starttls.enable", Boolean.parseBoolean(enableTls));
        mailSender.setJavaMailProperties(mailSenderProps);

        return mailSender;
    }
}
