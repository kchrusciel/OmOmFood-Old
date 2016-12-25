package pl.codecouple.omomfood.email.templates.register;

import lombok.Data;
import pl.codecouple.omomfood.email.BaseEmail;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

/**
 * This is {@link WelcomeEmail} class as welcome email.
 * Base email has three fields, with title, content and footer.
 * In this class is extra filed with information about welcome email content.
 * This class is used for welcome email.
 *
 * @author Krzysztof Chruściel
 */
@Data
public class WelcomeEmail extends BaseEmail {

    /** Email account content. */
    public static final String EMAIL_ACCOUNT_CONTENT = "email.account.content";

    /**
     * Constructor of {@link WelcomeEmail} class.
     *
     */
    public WelcomeEmail(final ResourceMessagesService resourceMessagesService) {
        super(resourceMessagesService, EMAIL_ACCOUNT_CONTENT);
    }
}
