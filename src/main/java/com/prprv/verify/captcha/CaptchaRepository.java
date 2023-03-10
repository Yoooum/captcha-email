package com.prprv.verify.captcha;

/**
 * @author Yoooum
 */
public interface CaptchaRepository {
    /**
     * 保存验证码，k-v形式
     * @param email 邮箱
     * @param code 验证码
     * @return 验证码
     */
    String put(String email, String code);

    /**
     * 获取验证码
     * @param email 邮箱
     * @return 验证码，过期或不存在返回 {@code null}
     */
    String get(String email);

    /**
     * 移除验证码
     * @param email 邮箱
     * @return 验证码，过期或不存在返回 {@code null}
     */
    String remove(String email);

    /**
     * 验证码是否存在
     * @param email 邮箱
     * @return 是否存在
     */
    boolean contains(String email);
}
