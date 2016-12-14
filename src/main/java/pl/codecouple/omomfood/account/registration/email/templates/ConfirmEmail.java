package pl.codecouple.omomfood.account.registration.email.templates;

import lombok.Data;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
public class ConfirmEmail extends BaseEmail {

    public static final String EMAIL_CONFIRM_CONTENT = "email.confirm.content";
    public static final String EMAIL_CONFIRM_LINK = "email.confirm.link";

    private String confirmLink;

    /**
     * Constructor of {@link ConfirmEmail} class.
     *
     */
    public ConfirmEmail(final String confirmLinkID) {
        super(EMAIL_CONFIRM_CONTENT);
        this.confirmLink = resourceMessagesService.getParametrizedMessages(EMAIL_CONFIRM_LINK, new Object[]{title, confirmLinkID});
    }
}
