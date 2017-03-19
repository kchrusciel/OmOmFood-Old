package pl.codecouple.omomfood.offers.currency;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.codecouple.omomfood.offers.Offer;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Value("${currency.conversion}")
    private boolean currencyConversion;

    @Override
    public Currency getCurrency() {
        return Currency.getInstance(new Locale("pl", "PL"));
    }

    @Override
    public String getCurrencySymbol() {
        return Currency.getInstance(LocaleContextHolder.getLocale()).getSymbol();
    }

    @Override
    @Cacheable("prices")
    public String getCalculatedPrice(Offer offer) {
        final String offerBaseCurrencyCode = offer.getPrice().getCurrencyCode();

        if(!currencyConversion){
            return String.valueOf(offer.getPrice().getValue().intValue()) + offerBaseCurrencyCode;
        }

        Currency localeCurrency = getCurrency();
        final String localeCurrencyCode = localeCurrency.getCurrencyCode();

        if(offerBaseCurrencyCode.equals(localeCurrencyCode)){
            return String.valueOf(offer.getPrice().getValue().intValue()) + localeCurrency.getSymbol();
        }

        return convertCurrency(offer, offerBaseCurrencyCode, localeCurrency, localeCurrencyCode);
    }

    private String convertCurrency(Offer offer, String offerBaseCurrencyCode, Currency localeCurrency, String localeCurrencyCode) {
        final String uri = "http://api.fixer.io/latest?base=%s&symbols=%s";
        String result = new RestTemplate().getForObject(String.format(uri, offerBaseCurrencyCode, localeCurrencyCode), String.class);
        JSONObject object = new JSONObject(result);

        return String.valueOf(getMultipleRates(offer, localeCurrencyCode, object).intValue()) + localeCurrency.getSymbol();
    }

    private BigDecimal getMultipleRates(Offer offer, String localeCurrencyCode, JSONObject object) {
        return offer.getPrice().getValue().multiply(object.getJSONObject("rates").getBigDecimal(localeCurrencyCode));
    }

    @Override
    public String getCalculatedPrice() {
        return null;
    }
}
