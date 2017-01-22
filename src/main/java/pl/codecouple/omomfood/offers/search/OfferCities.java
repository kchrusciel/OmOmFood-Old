package pl.codecouple.omomfood.offers.search;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * This is {@link OfferCities} container for search purposes.
 * This class have two fileds. {@link OfferCities#city} with city value
 * and {@link OfferCities#cityCount} with city count.
 *
 * @author Krzysztof Chruściel
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OfferCities {

    /** City from {@link pl.codecouple.omomfood.offers.Offer}.*/
    private String city;
     /** City count.*/
    private int cityCount;

}
