package pl.codecouple.omomfood.offers.price;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
@NoArgsConstructor
@Embeddable
public class Price {

    /** Offer price with specific precision. */
    @Column(nullable= false, precision=5, scale=2)    // Creates the database field with this size.
    @Digits(integer=5, fraction=2)
    private BigDecimal value;

    private String currencyCode;



}
