package pl.codecouple.omomfood.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This is {@link LocalDateTimeAttributeConverter} class for DB convert  purposes.
 * This class convert fields from and to DB. Converts to {@link LocalDateTime}.
 * This class is written because without this converter date is stored in wrong format.
 *
 * @author Krzysztof Chruściel
 */
@Converter(autoApply = true)
public class LocalDateTimeAttributeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Timestamp convertToDatabaseColumn(final LocalDateTime locDateTime) {
        return (locDateTime == null ? null : Timestamp.valueOf(locDateTime));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime convertToEntityAttribute(final Timestamp sqlTimestamp) {
        return (sqlTimestamp == null ? null : sqlTimestamp.toLocalDateTime());
    }
}
