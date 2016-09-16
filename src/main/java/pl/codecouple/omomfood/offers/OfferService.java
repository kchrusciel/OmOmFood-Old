package pl.codecouple.omomfood.offers;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
public interface OfferService {

    List<Offer> getAllOffersByCity(String city);
    List<Offer> getAllOffersByCityAndDate(String city, LocalDateTime eventDate);
    List<Offer> getAllOffersSortedByDate();
    List<Offer> getAllOffers();
    List<Offer> getAllMyOffers(String email);

    Offer getOfferById(long id);
    void addOffer(Offer offer);
    void deleteOffers();

}
