package pl.codecouple.omomfood.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.users.User;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class OfferServiceImpl implements OfferService {

    private static final String OFFER_COUNTER_KEY = "offer";


    @Autowired
    private AccountService accountService;

    private OfferRepository offerRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    @Override
    public void addOffer(Offer offer) {
        offerRepository.save(offer);
    }

    @Override
    public void deleteOffers() {
        offerRepository.deleteAll();
    }

    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    @Override
    public List<Offer> getAllOffersByCityAndDate(String city, LocalDateTime eventDate) {
        return offerRepository.findByCityContainingAndEventDateAllIgnoreCaseOrderByEventDateAsc(city, eventDate);
    }

    @Override
    public List<Offer> getAllOffersByCity(String city) {
        return offerRepository.findByCityContainingAllIgnoreCaseOrderByEventDateAsc(city);
    }

    @Override
    public List<Offer> getAllOffersSortedByDate() {
        return offerRepository.findAllByOrderByEventDateAsc();
    }

    @Override
    public Offer getOfferById(long id) {
        return offerRepository.findOne(id);
    }

    @Override
    public List<Offer> getAllMyOffers(String email) {
        User user = accountService.getUserByEmail(email);
        return offerRepository.findByOwner(user);
    }
}

