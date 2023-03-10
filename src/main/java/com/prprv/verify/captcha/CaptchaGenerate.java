package com.prprv.verify.captcha;

/**
 * @author Yoooum
 */
public interface CaptchaGenerate {
    String generate(String email);
    boolean expired(String email, String code);
    boolean contains(String email);
}
