package com.prprv.verify.service;

import com.prprv.verify.captcha.CaptchaGenerate;
import com.prprv.verify.captcha.CaptchaService;
import com.prprv.verify.template.SimpleCaptchaTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author Yoooum
 */
public class SimpleCaptchaService implements CaptchaService {

    final private CaptchaGenerate captchaGenerate;
    final private JavaMailSender sender;
    public SimpleCaptchaService(CaptchaGenerate captchaGenerate, JavaMailSender sender) {
        this.captchaGenerate = captchaGenerate;
        this.sender = sender;
    }

    @Override
    public String sendCaptchaCode(String username, String email) {
        String code = captchaGenerate.generate(email);

        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        String html = new SimpleCaptchaTemplate().template(code);

        try {
            helper.setFrom("prprv事务局<no-reply@prprv.com>");
            helper.setTo("%s<%s>".formatted(username, email));
            helper.setSubject("[prprv]验证码");
            helper.setText(html, true);
            new Thread(() -> sender.send(mimeMessage)).start();
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return code;
    }

    @Override
    public boolean verifyCaptchaCode(String email, String captcha) {
        return captchaGenerate.expired(email, captcha);
    }
}
