package pl.codecouple.omomfood.offers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.codecouple.omomfood.account.AccountService;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.utils.UserDetailsService;

import java.time.LocalDateTime;
import java.util.List;

/**
 * This is {@link OfferServiceImpl} class implementation of {@link OfferService}
 * interface for offers operations.
 *
 * @author Krzysztof Chruściel
 */

@Service
public class OfferServiceImpl implements OfferService {


    /** {@link UserDetailsService} user details service instance. */
    private UserDetailsService userDetailsService;
    /** {@link AccountService} account service instance. */
    private AccountService accountService;
    /** {@link OfferRepository} offer repository instance. */
    private OfferRepository offerRepository;

    /**
     * Constructor of {@link OfferServiceImpl} class.
     *
     * @param offerRepository for {@link Offer} DB operations.
     * @param accountService for account operations.
     *
     */
    @Autowired
    public OfferServiceImpl(final OfferRepository offerRepository,
                            final AccountService accountService,
                            final UserDetailsService userDetailsService) {
        this.offerRepository = offerRepository;
        this.accountService = accountService;
        this.userDetailsService = userDetailsService;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addOffer(final Offer offer) {
        offerRepository.save(offer);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteOffers() {
        offerRepository.deleteAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Offer> getAllOffers(final Pageable pageable) {
        return offerRepository.findAll(pageable);
    }

    @Override
    public Page<Offer> getAllOffersByCityAndDate(final String city, final LocalDateTime eventDate, final Pageable pageable) {
        return offerRepository.findByCityContainingAndEventDateAllIgnoreCaseOrderByEventDateAsc(city, eventDate, pageable);
    }

    @Override
    public List<Offer> getAllOffersByCityAndDate(final String city, final LocalDateTime eventDate) {
        return offerRepository.findByCityContainingAndEventDateAllIgnoreCaseOrderByEventDateAsc(city, eventDate);
    }

    @Override
    public Page<Offer> getAllOffersByDateSortedByDate(final LocalDateTime eventDate, final Pageable pageable) {
        return offerRepository.findByEventDateOrderByEventDateAsc(eventDate, pageable);
    }

    @Override
    public List<Offer> getAllOffersByDateSortedByDate(final LocalDateTime eventDate) {
        return offerRepository.findByEventDateOrderByEventDateAsc(eventDate);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Offer> getAllOffersByCity(final String city, final Pageable pageable) {
        return offerRepository.findByCityContainingAllIgnoreCaseOrderByEventDateAsc(city, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Offer> getAllOffersByCity(final String city) {
        return offerRepository.findByCityContainingAllIgnoreCaseOrderByEventDateAsc(city);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Offer> getAllOffersSortedByDate(final Pageable pageable) {
        return offerRepository.findAllByOrderByEventDateAsc(pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Offer> getAllOffersSortedByDate() {
        return offerRepository.findAllByOrderByEventDateAsc();
    }

    @Override
    public Offer getOfferById(final long id) {
        return offerRepository.findOne(id);
    }

    @Override
    public List<Offer> getAllMyOffers(final String email) {
        User user = accountService.getUserByEmail(email);
        return offerRepository.findByOwnerOrderByEventDateAsc(user);
    }

    @Override
    public Page<Offer> getAllMyOffers(final String email, final Pageable pageable) {
        User user = accountService.getUserByEmail(email);
        return offerRepository.findByOwnerOrderByEventDateAsc(user, pageable);
    }

    @Override
    public boolean isOfferOwner(final Offer offer) {
        User loggedUser = userDetailsService.getLoggedUser();
        return offer.getOwner().equals(loggedUser);
    }
}

