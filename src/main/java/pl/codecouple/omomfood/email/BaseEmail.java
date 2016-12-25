package pl.codecouple.omomfood.email;

import lombok.Data;
import pl.codecouple.omomfood.utils.ResourceMessagesService;

/**
 * This is {@link BaseEmail} class as base email.
 * Base email has three fields, with title, content and footer.
 * This class is used as base for other implementations.
 *
 * @author Krzysztof Chruściel
 */
@Data
public class BaseEmail {

    /** Email company name content. */
    public static final String EMAIL_TITLE = "CompanyName";
    /** Email footer content. */
    public static final String EMAIL_FOOTER = "email.footer";

    /** Email title. */
    private String title;
    /** Email content. */
    private String content;
    /** Email footer. */
    private String footer;

    /** {@link ResourceMessagesService} resource messages service instance. */
    private ResourceMessagesService resourceMessagesService;

    /**
     * Constructor of {@link BaseEmail} class.
     *
     * @param content with email content.
     * @param resourceMessagesService instance.
     */
    public BaseEmail(final ResourceMessagesService resourceMessagesService,
                     final String content) {
        this.resourceMessagesService = resourceMessagesService;
        this.title = resourceMessagesService.getMessage(EMAIL_TITLE);
        this.content = resourceMessagesService.getMessage(content);
        this.footer = resourceMessagesService.getParametrizedMessages(EMAIL_FOOTER,  new Object[]{this.title});
    }
}
