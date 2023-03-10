package com.prprv.verify.template;

import com.prprv.verify.captcha.EmailTemplate;

/**
 * @author Yoooum
 */
public class SimpleCaptchaTemplate implements EmailTemplate {
    @Override
    public String template(String... args) {
        return """
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
                    </style>
                </head>
                <body>
                <div>
                    <p>您的验证码为：</p>
                    <div id="code">%s</div>
                </div>
                </body>
                </html>
                """.formatted(args[0]);
    }
}
