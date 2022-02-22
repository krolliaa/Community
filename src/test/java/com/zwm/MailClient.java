package com.zwm;

import com.zwm.util.MailServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@SpringBootTest
public class MailClient {

    @Autowired
    private MailServer mailServer;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    public void sendTextMail() {
        mailServer.sendMessage("xixilala0627@163.com", "I Love You", "Hello World!");
    }

    @Test
    public void sendHtmlMail() {
        Context context = new Context();
        context.setVariable("username", "Lucky");
        String content = templateEngine.process("/mail/demo", context);
        System.out.println(content);
        mailServer.sendMessage("992555989@qq.com", "This is HTML!", content);
    }
}
