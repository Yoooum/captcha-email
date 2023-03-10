package com.prprv.verify.captcha;

import com.prprv.verify.captcha.CaptchaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 验证码存储在内存中的实现，用于存储验证码和过期时间 <br>
 * @author Yoooum
 */
@Slf4j
public class InMemoryCaptchaRepository implements CaptchaRepository {
    // 验证码存储，结构: Map<email, code.expireTime>
    private final Map<String, String> captchaMap = new HashMap<>();
    // 默认5分钟过期
    private static final long EXPIRE_TIME = 5 * 60 * 1000;

    /**
     * 检查邮箱对应的验证码是否过期，如果过期则删除
     * @param email 需要检查的邮箱
     * @return 返回未过期的验证码，如果过期则返回 {@code null}
     */
    private String check(String email) {
        String unchecked = captchaMap.get(email);
        if (unchecked == null) {
            return null;
        }
        // 分割验证码和过期时间，判断是否过期，分割符为"."
        String[] split = unchecked.split("\\.");
        if (split.length != 2) {
            log.warn("captchaMap contains invalid captcha: {}", unchecked);
            captchaMap.remove(email);
            return null;
        }
        String code = split[0];
        long exp = Long.parseLong(split[1]);
        if (System.currentTimeMillis() > exp) {
            log.debug("captcha for {} is expired", email);
            captchaMap.remove(email);
            return null;
        }
        return code;
    }

    /**
     * 检查所有验证码是否过期，如果过期则删除
     */
    private void check() {
        for (String email : captchaMap.keySet()) {
            this.check(email);
        }
    }

    /**
     * 存储验证码
     * @param email 邮箱
     * @param code 验证码
     * @return 返回之前存储的验证码，如果没有则返回 {@code null}
     */
    @Override
    public String put(String email, String code) {
        // 检查所有验证码是否过期，每存100个验证码就检查一次，并且概率是1/3
        if (captchaMap.size() % 100 == 0 && Math.random() < 0.33) {
            log.debug("captchaMap size: {}, check all captcha", captchaMap.size());
            this.check();
            log.debug("remove over, now captchaMap size: {}", captchaMap.size());
        }
        Assert.notNull(email, "email must not be null");
        Assert.notNull(code, "code must not be null");
        long exp = System.currentTimeMillis() + EXPIRE_TIME;
        String captcha = "%s.%d".formatted(code, exp);
        return captchaMap.put(email, captcha);
    }

    /**
     * 获取验证码
     * @param email 邮箱
     * @return 返回验证码，如果过期则返回 {@code null}
     */
    @Override
    public String get(String email) {
        Assert.hasText(email, "email must not be empty");
        return this.check(email);
    }

    /**
     * 删除验证码
     * @param email 邮箱
     * @return 返回之前存储的验证码，如果没有则返回 {@code null}
     */
    @Override
    public String remove(String email) {
        Assert.hasText(email, "email must not be empty");
        return captchaMap.remove(email);
    }

    /**
     * 判断邮箱是否存在验证码
     * @param email 邮箱
     * @return 如果存在则返回 {@code true}，否则返回 {@code false}
     */
    @Override
    public boolean contains(String email) {
        Assert.hasText(email, "email must not be empty");
        this.check(email);
        return captchaMap.containsKey(email);
    }
}
