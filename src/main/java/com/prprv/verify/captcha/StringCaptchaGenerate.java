package com.prprv.verify.captcha;

/**
 * @author Yoooum
 */
public class StringCaptchaGenerate implements CaptchaGenerate {
    private final CaptchaRepository repository;
    public StringCaptchaGenerate(CaptchaRepository repository) {
        this.repository = repository;
    }
    @Override
    public String generate(String email) {
        String exists = repository.get(email);
        if (exists != null) return exists;

        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            // num为0-35的随机数，0-9为数字，10-35为大写字母
            int num = (int) (Math.random() * 36);
            if (num < 10) code.append(num);
            else if (num < 36) {
                // 65-90为'A'-'Z'的ASCII码，例如num=10，num+55=65，(char)65='A'
                code.append((char) (num + 55));
            }
        }

        repository.put(email, code.toString());
        return code.toString();
    }

    @Override
    public boolean expired(String email, String code) {
        String repo = repository.get(email);
        if (repo == null) return false;
        return repo.equals(code);
    }

    @Override
    public boolean contains(String email) {
        return repository.contains(email);
    }
}
