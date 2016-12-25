package pl.codecouple.omomfood.email.templates.forget.password;

import lombok.Data;
import pl.codecouple.omomfood.email.BaseEmail;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
public class ForgetPasswordEmail extends BaseEmail {

    /** Email confirm content. */
    public static final String EMAIL_FORGET_PASSWORD_CONTENT = "email.forget.password.content";
    /** Email confirm link content. */
    public static final String EMAIL_FORGET_CONFIRM_LINK = "email.forget.confirm.link";

    /** Email reset token link. */
    private String resetTokenLinkID;

    /**
     * Constructor of {@link ForgetPasswordEmail} class.
     *
     * @param resourceMessagesService instance.
     * @param resetTokenLinkID with token link ID.
     */
    public ForgetPasswordEmail(final ResourceMessagesService resourceMessagesService,
                               final String resetTokenLinkID) {
        super(resourceMessagesService, EMAIL_FORGET_PASSWORD_CONTENT);
        this.resetTokenLinkID = getResourceMessagesService().getParametrizedMessages(EMAIL_FORGET_CONFIRM_LINK,
                                                                                     new Object[]{getTitle(),
                                                                                     resetTokenLinkID});
    }
}
