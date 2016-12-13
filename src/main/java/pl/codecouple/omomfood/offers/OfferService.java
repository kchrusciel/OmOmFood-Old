package pl.codecouple.omomfood.offers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
public interface OfferService {

    /**
     * This method takes all offers which contains city value from DB sorted by date ASC.
     * This method is without paging.
     *
     * @param city which is used to search.
     * @return <code>List</code> with all offers.
     */
    List<Offer> getAllOffersByCity(String city);

    /**
     * This method takes all offers which contains city value from DB sorted by date ASC.
     * This method is with paging.
     *
     * @param city which is used to search.
     * @param pageable page object.
     * @return <code>Page</code> with offers and paging.
     */
    Page<Offer> getAllOffersByCity(String city, Pageable pageable);

    List<Offer> getAllOffersByCityAndDate(String city, LocalDateTime eventDate);
    Page<Offer> getAllOffersByCityAndDate(String city, LocalDateTime eventDate, Pageable pageable);

    List<Offer> getAllOffersByDateSortedByDate(LocalDateTime eventDate);
    Page<Offer> getAllOffersByDateSortedByDate(LocalDateTime eventDate, Pageable pageable);

    /**
     * This method takes all offers from DB sorted by date ASC.
     * This method is without paging.
     *
     * @return <code>List</code> with offers.
     */
    List<Offer> getAllOffersSortedByDate();

    /**
     * This method takes all offers from DB sorted by date ASC.
     * This method is with paging.
     *
     * @param pageable page object.
     * @return <code>Page</code> with offers and paging.
     */
    Page<Offer> getAllOffersSortedByDate(Pageable pageable);

    /**
     * This method takes all offers from DB.
     *
     * @return <code>List</code> with all offers without sorting.
     */
    List<Offer> getAllOffers();

    /**
     * This method takes all offers from DB.
     *
     * @param pageable page object.
     * @return <code>List</code> with all offers without sorting.
     */
    Page<Offer> getAllOffers(Pageable pageable);

    List<Offer> getAllMyOffers(String email);

    Page<Offer> getAllMyOffers(String email, Pageable pageable);

    Offer getOfferById(long id);

    /**
     * This method adds {@link Offer} to DB.
     * @param offer which will be added to DB.
     */
    void addOffer(Offer offer);

    /**
     * This method delete all {@link Offer} from DB.
     */
    void deleteOffers();

}
