package com.prprv.verify.controller;

import com.prprv.verify.captcha.CaptchaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author Yoooum
 */
@RestController
@RequestMapping("/api/v1/captcha")
public class CaptchaController {
    private final CaptchaService service;

    public CaptchaController(CaptchaService service) {
        this.service = service;
    }

    // http://localhost:8080/api/v1/captcha?email=xxx&code=xxx
    @GetMapping(params = {"email", "code"})
    public ResponseEntity<Map<String,String>> expired(String email, String code) {
        if (notEmail(email)) {
            return ResponseEntity.ok(Map.of("status", "error", "message", "邮箱格式错误"));
        }
        return service.verifyCaptchaCode(email, code)
                ? ResponseEntity.ok(Map.of("status", "ok", "message", "验证码正确"))
                : ResponseEntity.ok(Map.of("status", "error", "message", "验证码错误或已过期"));
    }

    // http://localhost:8080/api/v1/captcha/new?username=xxx&email=xxx
    @GetMapping(value = "/new",params = {"username","email"})
    public ResponseEntity<Map<String, String>> generate(String username, String email) {
        if (notEmail(email)) {
            return ResponseEntity.ok(Map.of("status", "error", "message", "邮箱格式错误"));
        }
        String code = service.sendCaptchaCode(username, email);
        return code == null
                ? ResponseEntity.ok(Map.of("status", "error", "message", "验证码生成失败"))
                : ResponseEntity.ok(Map.of("status", "ok", "message", "验证码已发送"));
    }

    private boolean notEmail(String email) {
        String exp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        return !email.matches(exp);
    }
}
