package pl.codecouple.omomfood.offers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.codecouple.omomfood.account.users.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by krzysztof.chrusciel on 2016-09-01.
 */
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByCityContainingAndEventDateAllIgnoreCaseOrderByEventDateAsc(String city, LocalDateTime eventDate);
    Page<Offer> findByCityContainingAndEventDateAllIgnoreCaseOrderByEventDateAsc(String city, LocalDateTime eventDate, Pageable pageable);

    /**
     * This method takes all offers which contains city value from DB sorted by date ASC.
     * This method is without paging.
     *
     * @param city which is used to search.
     * @return <code>List</code> with all offers.
     */
    List<Offer> findByCityContainingAllIgnoreCaseOrderByEventDateAsc(String city);

    /**
     * This method takes all offers which contains city value from DB sorted by date ASC.
     * This method is with paging.
     *
     * @param city which is used to search.
     * @param pageable page object.
     * @return <code>Page</code> with offers and paging.
     */
    Page<Offer> findByCityContainingAllIgnoreCaseOrderByEventDateAsc(String city, Pageable pageable);

    List<Offer> findByEventDateOrderByEventDateAsc(LocalDateTime eventDate);
    Page<Offer> findByEventDateOrderByEventDateAsc(LocalDateTime eventDate, Pageable pageable);

    /**
     * This method takes all offers from DB sorted by date ASC.
     * This method is without paging.
     *
     * @return <code>List</code> with offers.
     */
    List<Offer> findAllByOrderByEventDateAsc();

    /**
     * This method takes all offers from DB sorted by date ASC.
     * This method is with paging.
     *
     * @param pageable page object.
     * @return <code>Page</code> with offers and paging.
     */
    Page<Offer> findAllByOrderByEventDateAsc(Pageable pageable);

    List<Offer> findByOwnerOrderByEventDateAsc(User user);
    Page<Offer> findByOwnerOrderByEventDateAsc(User user, Pageable pageable);

}
