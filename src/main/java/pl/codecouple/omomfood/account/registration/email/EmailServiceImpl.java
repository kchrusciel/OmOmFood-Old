package pl.codecouple.omomfood.account.registration.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    private final TemplateEngine templateEngine;

    private final ResourceMessagesService resourceMessagesService;

    @Autowired
    public EmailServiceImpl(JavaMailSender javaMailSender, TemplateEngine templateEngine, ResourceMessagesService resourceMessagesService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.resourceMessagesService = resourceMessagesService;
    }

    @Override
    public void sendEmail(String title, String to, String content) {

        MimeMessage mail = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setTo(to);
            helper.setReplyTo("test@omom.pl");
            helper.setFrom("test@omom.pl");
            helper.setSubject(title);
            helper.setText(content);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mail);

    }



    @Override
    public void sendConfirmationEmail(String to, String content) {
        String title = resourceMessagesService.getMessage("email.confirm.title");
        sendEmail(title, to, prepareContentForConfirmationEmail(content));
    }

    @Override
    public void sendWelcomeEmail(String to) {
        String title = resourceMessagesService.getMessage("email.account.title");
        String content = resourceMessagesService.getMessage("email.account.content");
        sendEmail(title, to, prepareContentForWelcomeEmail(content));
    }

    String prepareContentForConfirmationEmail(String confirm_link_id){
        String title = resourceMessagesService.getMessage("CompanyName");
        String content = resourceMessagesService.getMessage("email.confirm.content");
        String confirm_link = resourceMessagesService.getParametrizedMessages("email.confirm.link", new Object[]{title, confirm_link_id});
        String footer = resourceMessagesService.getParametrizedMessages("email.footer",  new Object[]{title});
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("content", content);
        context.setVariable("confirm_link", confirm_link);
        context.setVariable("footer", footer);
        return templateEngine.process("email/email_template", context);
    }

    String prepareContentForWelcomeEmail(String content){
        String title = resourceMessagesService.getMessage("CompanyName");
        String footer = resourceMessagesService.getParametrizedMessages("email.footer",  new Object[]{title});
        Context context = new Context();
        context.setVariable("title", title);
        context.setVariable("content", content);
        context.setVariable("footer", footer);
        return templateEngine.process("email/email_template", context);
    }

}
