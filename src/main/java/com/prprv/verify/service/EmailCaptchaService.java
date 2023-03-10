package com.prprv.verify.service;

import com.prprv.verify.captcha.CaptchaGenerate;
import com.prprv.verify.captcha.CaptchaService;
import com.prprv.verify.template.EmailCaptchaTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author Yoooum
 */
public class EmailCaptchaService implements CaptchaService {

    final private CaptchaGenerate captchaGenerate;
    final private JavaMailSender sender;
    public EmailCaptchaService(CaptchaGenerate captchaGenerate, JavaMailSender sender) {
        this.captchaGenerate = captchaGenerate;
        this.sender = sender;
    }

    private String now() {
        // 获取当前时间，格式化为字符串，转换为中国时区
        return LocalDateTime.now()
                .atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of("Asia/Shanghai"))
                .toLocalDateTime()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                + " (CN)";
    }

    @Override
    public String sendCaptchaCode(String username, String email) {
        String code = captchaGenerate.generate(email);
        MimeMessage mimeMessage = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

        String html = new EmailCaptchaTemplate()
                .template()
                .replace("{{ username }}", username)
                .replace("{{ code }}", code)
                .replace("{{ time }}", this.now());

        try {
            helper.setFrom("prprv事务局<no-reply@prprv.com>");
            helper.setTo("%s<%s>".formatted(username, email));
            helper.setSubject("[prprv]有新的登入活动");
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
