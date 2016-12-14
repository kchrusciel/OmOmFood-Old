package pl.codecouple.omomfood.account.registration.email.templates;

import lombok.Data;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
public class WelcomeEmail extends BaseEmail {

    public static final String EMAIL_ACCOUNT_CONTENT = "email.account.content";

    /**
     * Constructor of {@link WelcomeEmail} class.
     *
     */
    public WelcomeEmail() {
        super(EMAIL_ACCOUNT_CONTENT);
    }
}
