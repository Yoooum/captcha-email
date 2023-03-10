package com.prprv.verify;

import com.prprv.verify.captcha.CaptchaGenerate;
import com.prprv.verify.captcha.CaptchaService;
import com.prprv.verify.captcha.InMemoryCaptchaRepository;
import com.prprv.verify.captcha.StringCaptchaGenerate;
import com.prprv.verify.service.SimpleCaptchaService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootApplication
public class EmailCaptchaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmailCaptchaApplication.class, args);
    }

    @Bean
    public CaptchaGenerate captchaGenerate() {
        InMemoryCaptchaRepository repository = new InMemoryCaptchaRepository();
        return new StringCaptchaGenerate(repository);
    }

    @Bean
    public CaptchaService simpleCaptchaService(JavaMailSender sender) {
        return new SimpleCaptchaService(captchaGenerate(), sender);
    }

}
