package pl.codecouple.omomfood.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class ResourceMessagesServiceImpl implements ResourceMessagesService {

    /** {@link Locale} object instance. */
    private final Locale locale;

    /** {@link MessageSource} message source instance. */
    private final MessageSource messageSource;

    /**
     * Constructor of {@link ResourceMessagesServiceImpl} class.
     *
     * @param messageSource with messages from properties.
     */
    @Autowired
    public ResourceMessagesServiceImpl(final MessageSource messageSource) {
        this.messageSource = messageSource;
        this.locale = LocaleContextHolder.getLocale();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getMessage(final String id) {
        return messageSource.getMessage(id, null, locale);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getParametrizedMessages(final String id, final Object[] objects) {
        return messageSource.getMessage(id, objects, locale);
    }
}
