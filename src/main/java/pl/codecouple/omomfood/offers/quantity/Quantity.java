package pl.codecouple.omomfood.offers.quantity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.codecouple.omomfood.offers.Offer;

import javax.persistence.Embeddable;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Quantity {

    /** Quantity of {@link Offer}. */
    private int quantity;
    /** {@link Boolean} flag which store information about quantity. */
    private boolean unlimited;

}
