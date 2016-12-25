package pl.codecouple.omomfood.email.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import pl.codecouple.omomfood.email.BaseEmail;
import pl.codecouple.omomfood.email.templates.forget.password.ForgetPasswordEmail;
import pl.codecouple.omomfood.email.templates.register.ConfirmEmail;
import pl.codecouple.omomfood.email.templates.register.WelcomeEmail;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * This is {@link EmailServiceImpl} class which implements {@link EmailService} for email purposes.
 * This class implements three methods. First method is used for
 * general sending. Last two are for send specific email after
 * registration and confirmation process.
 *
 * @author Krzysztof Chruściel
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {

    /** Context title id. */
    public static final String CONTEXT_TITLE_ID = "title";
    /** Context content id. */
    public static final String CONTEXT_CONTENT_ID = "content";
    /** Context footer id. */
    public static final String CONTEXT_FOOTER_ID = "footer";
    /** Context confirm_link id. */
    public static final String CONTEXT_CONFIRM_LINK_ID = "confirm_link";
    /** Context confirm_link id. */
    public static final String CONTEXT_RESET_LINK_ID = "reset_link";

    /** Template name for confirmation emails. */
    public static final String TEMPLATE_NAME_CONFIRMATION = "email/confirmation_template";
    /** Template name for reset password emails. */
    public static final String TEMPLATE_NAME_RESET_PASSWORD = "email/reset_password_template";
    /** Template name for welcome emails. */
    public static final String TEMPLATE_NAME_WELCOME = "email/welcome_template";

    /** Welcome email title message id. */
    public static final String WELCOME_EMAIL_TITLE = "email.account.title";
    /** Confirm email title message id. */
    public static final String CONFIRM_EMAIL_TITLE = "email.confirm.title";
    /** Forget password email title message id. */
    public static final String EMAIL_FORGET_PASSWORD_TITLE = "email.forget.password.title";

    /** Email address from properties.*/
    @Value("${omomfood.email}")
    private String omomFoodEmail;

    /** {@link JavaMailSender} java mail sender instance. */
    private final JavaMailSender javaMailSender;
    /** {@link TemplateEngine} template engine instance. */
    private final TemplateEngine templateEngine;
    /** {@link ResourceMessagesService} resource messages service instance. */
    private final ResourceMessagesService resourceMessagesService;

    /**
     * Constructor of {@link EmailServiceImpl} class.
     *
     * @param javaMailSender for email operations.
     * @param templateEngine for templates operations.
     * @param resourceMessagesService for messages operations.
     *
     */
    @Autowired
    public EmailServiceImpl(final JavaMailSender javaMailSender,
                            final TemplateEngine templateEngine,
                            final ResourceMessagesService resourceMessagesService) {
        this.javaMailSender = javaMailSender;
        this.templateEngine = templateEngine;
        this.resourceMessagesService = resourceMessagesService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendEmail(final String title, final String to, final String content) {
        log.info("Send email");
        log.debug("Send email to: " + to);

        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(to);
            helper.setReplyTo(omomFoodEmail);
            helper.setFrom(omomFoodEmail);
            helper.setSubject(title);
            helper.setText(content);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        javaMailSender.send(mail);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendForgetPasswordEmail(final String to, final String content) {
        String title = resourceMessagesService.getMessage(EMAIL_FORGET_PASSWORD_TITLE);
        sendEmail(title, to, prepareContentForForgetPasswordEmail(content));
    }

    /**
     * This method creates HTML email content for forget password email.
     *
     * @param resetTokenLinkID forget password email.
     * @return <code>String</code> with email content.
     */
    private String prepareContentForForgetPasswordEmail(final String resetTokenLinkID){
        return templateEngine.process(TEMPLATE_NAME_RESET_PASSWORD,
                getContextForEmail(new ForgetPasswordEmail(resourceMessagesService, resetTokenLinkID)));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void sendConfirmationEmail(final String to, final String content) {
        String title = resourceMessagesService.getMessage(CONFIRM_EMAIL_TITLE);
        sendEmail(title, to, prepareContentForConfirmationEmail(content));
    }

    /**
     * This method creates HTML email content for confirmation email.
     *
     * @param confirmLinkID for confirmation email.
     * @return <code>String</code> with email content.
     */
    private String prepareContentForConfirmationEmail(final String confirmLinkID){
        return templateEngine.process(TEMPLATE_NAME_CONFIRMATION,
                getContextForEmail(new ConfirmEmail(resourceMessagesService, confirmLinkID)));
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void sendWelcomeEmail(final String to) {
        String title = resourceMessagesService.getMessage(WELCOME_EMAIL_TITLE);
        sendEmail(title, to, prepareContentForWelcomeEmail());
    }

    /**
     * This method creates HTML email content for welcome email.
     *
     * @return <code>String</code> with email content.
     */
    private String prepareContentForWelcomeEmail(){
        return templateEngine.process(TEMPLATE_NAME_WELCOME,
                getContextForEmail(new WelcomeEmail(resourceMessagesService)));
    }

    /**
     * This method puts all data from {@link BaseEmail} object to context
     * which will be used in email template.
     *
     * @param email object with data.
     * @return <code>Context</code> object for template.
     */
    private Context getContextForEmail(final BaseEmail email){
        Context context = new Context();
        context.setVariable(CONTEXT_TITLE_ID, email.getTitle());
        context.setVariable(CONTEXT_CONTENT_ID, email.getContent());
        context.setVariable(CONTEXT_FOOTER_ID, email.getFooter());
        if(email instanceof ConfirmEmail){
            context.setVariable(CONTEXT_CONFIRM_LINK_ID, ((ConfirmEmail) email).getConfirmLink());
        }
        if(email instanceof ForgetPasswordEmail){
            context.setVariable(CONTEXT_RESET_LINK_ID, ((ForgetPasswordEmail) email).getResetTokenLinkID());
        }
        return context;
    }

}
