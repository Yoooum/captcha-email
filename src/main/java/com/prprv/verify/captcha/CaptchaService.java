package com.prprv.verify.captcha;

/**
 * @author Yoooum
 */
public interface CaptchaService {
    String sendCaptchaCode(String username, String email);
    boolean verifyCaptchaCode(String email, String captcha);
}
