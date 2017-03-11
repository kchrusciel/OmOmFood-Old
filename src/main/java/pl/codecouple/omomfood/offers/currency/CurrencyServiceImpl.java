package pl.codecouple.omomfood.offers.currency;

import org.json.JSONObject;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.codecouple.omomfood.offers.Offer;

import java.util.Currency;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {

    @Override
    public Currency getCurrency() {
        return Currency.getInstance(LocaleContextHolder.getLocale());
    }

    @Override
    public String getCurrencySymbol() {
        return Currency.getInstance(LocaleContextHolder.getLocale()).getSymbol();
    }

    @Override
    public String getCalculatedPrice(Offer offer) {
        Currency localeCurrency = getCurrency();
        final String offerBaseCurrencyCode = offer.getPrice().getCurrencyCode();
        final String localeCurrencyCode = localeCurrency.getCurrencyCode();

        if(offerBaseCurrencyCode.equals(localeCurrencyCode)){
            return String.valueOf(offer.getPrice().getValue().intValue()) + localeCurrency.getSymbol();
        }

        final String uri = "http://api.fixer.io/latest?base=" + offerBaseCurrencyCode + "&symbols=" + localeCurrencyCode;

        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        JSONObject object = new JSONObject(result);

        return String.valueOf(offer.getPrice().getValue().multiply(object.getJSONObject("rates").getBigDecimal(localeCurrencyCode)).intValue()) + localeCurrency.getSymbol();
    }

    @Override
    public String getCalculatedPrice() {
        return null;
    }
}
