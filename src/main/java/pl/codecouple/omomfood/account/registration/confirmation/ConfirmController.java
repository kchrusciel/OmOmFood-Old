package pl.codecouple.omomfood.account.registration.confirmation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.email.service.EmailService;
import pl.codecouple.omomfood.messages.Message;
import pl.codecouple.omomfood.messages.MessageService;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

import java.time.LocalDateTime;

/**
 * This is {@link ConfirmController} for confirmation purposes.
 * We got one public endpoint "/confirm". GET method gets request param
 * with confirmation id. Based on the confirmation id, the user is retrieved.
 * If {@link User} is found and confirmation status is set to false user will
 * be activated. If {@link User} is not found then will be returned page with
 * specific message.
 *
 * @author Krzysztof Chruściel
 */
@Slf4j
@Controller
public class ConfirmController {

    /** Email confirmation error message id.*/
    public static final String EMAIL_CONFIRM_ERROR_MESSAGE_ID = "email.confirm.error";
    /** Email confirmation message id.*/
    public static final String EMAIL_CONFIRMED_MESSAGE_ID = "email.confirmed.message";
    /** Welcome message title message id.*/
    public static final String MESSAGES_WELCOME_TITLE_MESSAGE_ID = "messages.welcome.title";
    /** Welcome message content message id.*/
    public static final String MESSAGES_WELCOME_CONTENT_MESSAGE_ID = "messages.welcome.content";
    /** Model message id.*/
    public static final String MODEL_MESSAGE_ID = "message";

    /** Template name which is returned after registration.*/
    public static final String TEMPLATE_NAME_MESSAGES = "messages";

    /** {@link AccountService} account service instance. */
    private AccountService accountService;
    /** {@link ResourceMessagesService} resource messages service instance. */
    private ResourceMessagesService resourceMessagesService;
    /** {@link EmailService} email service instance. */
    private EmailService emailService;
    /** {@link MessageService} message service instance. */
    private MessageService messageService;

    /**
     * Constructor of {@link ConfirmController} class.
     *
     * @param emailService for email operations.
     * @param accountService for account operations.
     * @param resourceMessagesService for messages operations.
     * @param messageService for messages operations.
     *
     */
    @Autowired
    public ConfirmController(final AccountService accountService,
                             final ResourceMessagesService resourceMessagesService,
                             final EmailService emailService,
                             final MessageService messageService) {
        this.accountService = accountService;
        this.resourceMessagesService = resourceMessagesService;
        this.emailService = emailService;
        this.messageService = messageService;
    }

    /**
     * This is GET "/confirm" endpoint which is used for
     * confirmation process for {@link User}. Also this endpoint
     * is used for template showing.
     *
     * @param confirmId from request.
     * @param model object which takes return values.
     * @return <code>String</code> with template name.
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmID(@RequestParam(value = "id") final String confirmId,
                            final Model model){

        log.info("Start confirmation");
        log.debug("Confirmation ID:" + confirmId);

        User user = accountService.getUserByConfirmationId(confirmId);
        if(user == null){
            model.addAttribute(MODEL_MESSAGE_ID, resourceMessagesService.getMessage(EMAIL_CONFIRM_ERROR_MESSAGE_ID));
            return TEMPLATE_NAME_MESSAGES;
        }

        log.info("Active user");
        activeUser(user);

        log.info("Send welcome message");
        sendWelcomeMessage(user);

        model.addAttribute(MODEL_MESSAGE_ID, getEmailConfirmedMessage(user.getUsername()));
        return TEMPLATE_NAME_MESSAGES;
    }

    /**
     * This method do activation on {@link User}. If user is not
     * active, confirmation status is set to true and confirmation
     * id is set to null this mean confirmation id has been used.
     * After that, welcome email is send.
     * @param user for which confirmation process will be made.
     */
    private void activeUser(final User user){
        log.debug("Found user:" + user);
        if(!user.isConfirmationStatus()){
            user.setConfirmationStatus(true);
            user.setConfirmationId(null);
            accountService.addUser(user);
            emailService.sendWelcomeEmail(user.getEmail());
        }
    }

    /**
     * This method calculate confirmation message.
     * @param userName added to message.
     * @return <code>String</code> with message about confirmation.
     */
    private String getEmailConfirmedMessage(final String userName){
        return resourceMessagesService.getParametrizedMessages(
                EMAIL_CONFIRMED_MESSAGE_ID,
                new Object[]{userName});
    }

    /**
     * This method create welcome message and send this message to {@link User}.
     * @param recipient for which message will be send.
     */
    private void sendWelcomeMessage(final User recipient) {
        Message welcomeMessage = createWelcomeMessage(recipient);
        messageService.sendMessage(welcomeMessage);
    }

    /**
     * This method creates welcome message for new {@link User}.
     *
     * @param recipient for which message will be send.
     * @return <code>{@link Message}</code> with welcome content.
     */
    private Message createWelcomeMessage(final User recipient){
        String welcomeMessageTitle = getWelcomeMessageTitle();
        String welcomeMessageContent = getWelcomeMessageContent();
        return new Message(welcomeMessageTitle,
                           welcomeMessageContent,
                           LocalDateTime.now(),
                           accountService.getAdminUser(),
                           recipient);
    }

    /**
     * This method return {@link ConfirmController#MESSAGES_WELCOME_TITLE_MESSAGE_ID} message.
     * @return <code>{@link String}</code> with welcome message title.
     */
    private String getWelcomeMessageTitle() {
        return resourceMessagesService.getMessage(MESSAGES_WELCOME_TITLE_MESSAGE_ID);
    }

    /**
     * This method return {@link ConfirmController#MESSAGES_WELCOME_CONTENT_MESSAGE_ID} message.
     * @return <code>{@link String}</code> with welcome message content.
     */
    private String getWelcomeMessageContent() {
        return resourceMessagesService.getMessage(MESSAGES_WELCOME_CONTENT_MESSAGE_ID);
    }

}
