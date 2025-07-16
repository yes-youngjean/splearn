package tobyspring.splearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tobyspring.splearn.application.required.EmailSender;
import tobyspring.splearn.domain.Email;
import tobyspring.splearn.domain.PasswordEncoder;

@Configuration
public class AppConfig {

    @Bean
    public EmailSender emailSender(){

        return (Email email,
                String subject,
                String body) -> {
            System.out.printf("[EmailSender] To: %s, Subject: %s, Body: %s%n", email.address(), subject, body);
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
    }

}
