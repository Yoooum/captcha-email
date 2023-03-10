# 邮箱验证码
```sh
# 1.改名application-d.yml为application.yml并修改邮箱配置

# 2.生成验证码  
http://127.0.0.1:8080/api/v1/captcha/new?username=xxx&email=xxx

# 3.验证验证码
http://127.0.0.1:8080/api/v1/captcha?email=xxx&code=xxx
```


