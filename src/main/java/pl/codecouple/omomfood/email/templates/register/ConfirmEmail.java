package pl.codecouple.omomfood.email.templates.register;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import pl.codecouple.omomfood.email.BaseEmail;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

/**
 * This is {@link ConfirmEmail} class as confirm email.
 * Base email has three fields, with title, content and footer.
 * In this class is extra filed with information about confirm link content.
 * This class is used for confirmation email.
 *
 * @author Krzysztof Chruściel
 */
@Data
public class ConfirmEmail extends BaseEmail {

    /** Email confirm content. */
    public static final String EMAIL_CONFIRM_CONTENT = "email.confirm.content";
    /** Email confirm link content. */
    public static final String EMAIL_CONFIRM_LINK = "email.confirm.link";

    /** Email confirm link. */
    private String confirmLink;

    /**
     * Constructor of {@link ConfirmEmail} class.
     *
     * @param resourceMessagesService instance.
     * @param confirmLinkID with confirmation ID.
     */
    @Autowired
    public ConfirmEmail(final ResourceMessagesService resourceMessagesService,
                        final String confirmLinkID) {
        super(resourceMessagesService, EMAIL_CONFIRM_CONTENT);
        this.confirmLink = getResourceMessagesService().getParametrizedMessages(EMAIL_CONFIRM_LINK,
                                                                                new Object[]{getTitle(),
                                                                                confirmLinkID});
    }
}
