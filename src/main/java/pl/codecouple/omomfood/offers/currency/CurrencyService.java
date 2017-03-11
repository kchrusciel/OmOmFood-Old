package pl.codecouple.omomfood.offers.currency;

import pl.codecouple.omomfood.offers.Offer;

import java.util.Currency;

/**
 * Created by Krzysztof Chruściel.
 */
public interface CurrencyService {

    Currency getCurrency();
    String getCurrencySymbol();
    String getCalculatedPrice(Offer offer);
    String getCalculatedPrice();

}
