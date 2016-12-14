package pl.codecouple.omomfood.account.registration.email.templates;

import lombok.Data;
import pl.codecouple.omomfood.utils.ResourceMessagesService;
import pl.codecouple.omomfood.utils.ResourceMessagesServiceImpl;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
public class BaseEmail {

    public static final String EMAIL_TITLE = "CompanyName";
    public static final String EMAIL_FOOTER = "email.footer";

    protected String title;
    protected String content;
    protected String footer;

    /** {@link ResourceMessagesService} resource messages service instance. */
    protected final ResourceMessagesService resourceMessagesService;

    /**
     * Constructor of {@link BaseEmail} class.
     *
     */
    public BaseEmail(final String content) {
        this.resourceMessagesService = new ResourceMessagesServiceImpl();
        this.title = resourceMessagesService.getMessage(EMAIL_TITLE);
        this.content = resourceMessagesService.getMessage(content);
        this.footer = resourceMessagesService.getParametrizedMessages(EMAIL_FOOTER,  new Object[]{this.title});
    }
}
