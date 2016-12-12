package pl.codecouple.omomfood.offers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
public interface OfferService {

    List<Offer> getAllOffersByCity(String city);
    List<Offer> getAllOffersByCityAndDate(String city, LocalDateTime eventDate);
    Page<Offer> getAllOffersByDateSortedByDate(LocalDateTime eventDate, Pageable pageable);
    List<Offer> getAllOffersSortedByDate();
    List<Offer> getAllOffers();
    Page<Offer> getAllOffers(Pageable pageable);
    List<Offer> getAllMyOffers(String email);

    Offer getOfferById(long id);
    void addOffer(Offer offer);
    void deleteOffers();

}
