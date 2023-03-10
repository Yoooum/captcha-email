package com.prprv.verify.template;

import com.prprv.verify.captcha.EmailTemplate;

/**
 * @author Yoooum
 */
public class EmailCaptchaLocationTemplate implements EmailTemplate {
    @Override
    public String template(String... args) {
        return  """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <meta charset="UTF-8">
                    <title>邮件验证通知</title>
                    <style>
                        p {
                            padding: 0;
                            margin: 0;
                            font-family: "lucida Grande",Verdana,"Microsoft YaHei",sans-serif;
                            font-size: 14px;
                            color: #2a2a2a;
                        }
                        #code {
                            width: 120px;
                            font-size: 20px;
                            font-weight: bold;
                            padding: 5px;
                            margin: 10px 0;
                            background-color: #f0f0f0;
                            color: #707070;
                            border: 1px solid #707070;
                            text-align: center;
                        }
                        .t {
                            margin-top: 10px;
                        }
                        #h {
                            font-size: 16px;
                            color: #707070;
                            font-weight: bold;
                        }
                        .b {
                            color: #707070;
                            font-weight: bold;
                        }
                    </style>
                </head>
                <body>
                <div>
                    <p id="h">{{ username }} 您好！</p>
                    <p>您登录所需的令牌验证码为：</p>
                    <div id="code">{{ code }}</div>
                    <p>您会收到这封邮件，是由于有人在尝试登录您的账户，且提供了正确的账户和密码。</p>
                    <p class="t">prprv验证码是完成登录所必需的。<span class="b">没有人能够不访问这封电子邮件就访问您的帐户。</span></p>
                    <p class="t">位置： %s</p>
                    <p>时间： {{ time }}</p>
                    <p>IP地址： %s</p>
                    <p class="t"><span class="b">如果您未曾尝试登录</span>，那么请更改您的密码，并考虑更改您的电子邮件密码，以确保您的帐户安全。</p>
                    <p class="t">祝您愉快，</p>
                    <p>prprv事务局</p>
                </div>
                </body>
                </html>
                """;
    }

}
