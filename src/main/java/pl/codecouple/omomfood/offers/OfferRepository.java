package pl.codecouple.omomfood.offers;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.codecouple.omomfood.account.users.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by krzysztof.chrusciel on 2016-09-01.
 */
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByCityContainingAndEventDateAllIgnoreCaseOrderByEventDateAsc(String city, LocalDateTime eventDate);
    List<Offer> findByCityContainingAllIgnoreCaseOrderByEventDateAsc(String city);
    List<Offer> findAllByOrderByEventDateAsc();
    List<Offer> findByOwner(User user);

}
