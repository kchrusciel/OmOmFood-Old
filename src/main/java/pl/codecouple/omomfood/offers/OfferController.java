package pl.codecouple.omomfood.offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.account.validator.OfferValidator;
import pl.codecouple.omomfood.messages.Message;
import pl.codecouple.omomfood.messages.MessageService;
import pl.codecouple.omomfood.storage.StorageService;
import pl.codecouple.omomfood.utils.UserDetailsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Slf4j
@Controller
public class OfferController {

    /** Template name which is returned after registration.*/
    public static final String TEMPLATE_NAME_MESSAGES = "messages";
    /** Template name which is returned after search.*/
    public static final String TEMPLATE_NAME_OFFER_OFFERS = "offer/offers";
    /** Template name which is returned after one {@link Offer} choosing.*/
    public static final String TEMPLATE_NAME_OFFER_OFFER = "offer/offer";

    /** Page ID for model.*/
    public static final String MODEL_PAGE_ID = "page";
    /** Offers ID for model.*/
    public static final String MODEL_OFFERS_ID = "offers";

    /** "/offers" endpoint.*/
    public static final String OFFERS_ENDPOINT = "/offers";

    /** "city" request param key.*/
    public static final String REQUEST_PARAM_CITY = "city";
    /** "date" request param key.*/
    public static final String REQUEST_PARAM_DATE = "date";

    /** {@link OfferService} offer service instance. */
    private OfferService offerService;
    /** {@link UserDetailsService} user details service instance. */
    private UserDetailsService userDetailsService;
    /** {@link MessageService} message service instance. */
    private MessageService messageService;

    /**
     * Constructor of {@link OfferController} class.
     *
     * @param offerService for offer operations.
     * @param userDetailsService for user details operations.
     * @param messageService for messages operations.
     *
     */
    @Autowired
    public OfferController(final OfferService offerService,
                           final UserDetailsService userDetailsService,
                           final MessageService messageService) {
        this.offerService = offerService;
        this.userDetailsService = userDetailsService;
        this.messageService = messageService;
    }


    /**
     * This is "/offers" POST endpoint.
     * This method takes all {@link Offer}'s from DB.
     * This method can takes sorted offers.
     * If city and date parameter are empty this method
     * return all offers sorted by date ASC.
     *
     * @param city from search offers form.
     * @param date from search offers form.
     * @param modelAndView from request.
     * @param pageable with page object.
     * @return <code>ModelAndView</code> with model object and view name.
     */
    @RequestMapping(value = OFFERS_ENDPOINT,
            method = RequestMethod.POST)
    public ModelAndView showChosenAdverts(final @RequestParam(value = REQUEST_PARAM_CITY) String city,
                                          final @RequestParam(value = REQUEST_PARAM_DATE) String date,
                                          final ModelAndView modelAndView,
                                          final Pageable pageable) {

        Page<Offer> offersPageable = getOffersDependsOnParameters(city, date, pageable);
        PageWrapper<Offer> page = new PageWrapper<>(offersPageable, OFFERS_ENDPOINT);
        modelAndView.addObject(MODEL_OFFERS_ID, offersPageable.getContent());
        modelAndView.addObject(MODEL_PAGE_ID, page);
        modelAndView.setViewName(TEMPLATE_NAME_OFFER_OFFERS);
        return modelAndView;
    }

    /**
     * This method takes {@link Offer}'s depends on parameters.
     *
     * @param city from search offers form.
     * @param date from search offers form.
     * @param pageable with page object.
     * @return <code>Page</code> with all {@link Offer}'s.
     */
    private Page<Offer> getOffersDependsOnParameters(final String city,
                                                     final String date,
                                                     final Pageable pageable) {
        if (!areCityAndDateEmpty(city, date)) {
            return offerService.getAllOffersByCityAndDate(city, LocalDateTime.parse(date), pageable);
        } else if (!city.isEmpty()) {
            return offerService.getAllOffersByCity(city, pageable);
        } else if (!date.isEmpty()) {
            return offerService.getAllOffersByDateSortedByDate(LocalDateTime.parse(date), pageable);
        }
        return offerService.getAllOffersSortedByDate(pageable);
    }

    /**
     * This method checks if city and date are not empty.
     *
     * @param city from request.
     * @param date from request.
     * @return <code>true</code> if city and date are not empty, <code>false</code> otherwise.
     */
    private boolean areCityAndDateEmpty(final String city,
                                        final String date) {
        return city.isEmpty() && date.isEmpty();
    }

    /**
     * This is "/offers" GET endpoint.
     * This method takes all {@link Offer}'s from DB.
     *
     * @param modelAndView from request.
     * @param pageable with page object.
     * @return <code>ModelAndView</code> with model object and view name.
     */
    @RequestMapping(value = OFFERS_ENDPOINT,
            method = RequestMethod.GET)
    public ModelAndView showAllOffers(final ModelAndView modelAndView,
                                      final Pageable pageable) {
        Page<Offer> offersPageable = offerService.getAllOffers(pageable);
        PageWrapper<Offer> page = new PageWrapper<>(offersPageable, OFFERS_ENDPOINT);
        modelAndView.addObject(MODEL_OFFERS_ID, offersPageable.getContent());
        modelAndView.addObject(MODEL_PAGE_ID, page);
        modelAndView.setViewName(TEMPLATE_NAME_OFFER_OFFERS);
        return modelAndView;
    }

    /**
     * TODO
     * @param model
     * @return
     */
    @RequestMapping(value = "/offers/my",
            method = RequestMethod.GET)
    public String showMyOffers(final Model model) {
        model.addAttribute("offers", offerService.getAllMyOffers("giesem@o2.pl"));
        return "offer/offers";
    }

    /**
     *
     * @param offerID
     * @param messageForm
     * @param model
     * @return
     */
    @RequestMapping(value = "/offer/{offerID}",
            method = RequestMethod.GET)
    public String getOffer(final @PathVariable long offerID,
                           final Message messageForm,
                           final Model model) {
        Offer offer = offerService.getOfferById(offerID);
        if (offer == null) {
            model.addAttribute("message", "Empty or wrong offer ID");
            return TEMPLATE_NAME_MESSAGES;
        }
        model.addAttribute("offer", offerService.getOfferById(offerID));
        model.addAttribute("user", userDetailsService.getLoggedUser());
        return TEMPLATE_NAME_OFFER_OFFER;
    }


    /**
     * TODO
     * @param message
     * @param offerID
     * @return
     */
    @RequestMapping(value = "offers/{offerID}/message",
            method = RequestMethod.POST)
    public String sendMessageToOffer(final @Valid Message message,
                                     final BindingResult bindingResult,
                                     final @PathVariable("offerID") long offerID){
        message.isRead();
        message.getContent();
        log.debug("sendMessageToOffer");
        log.debug(bindingResult.toString());
        log.debug(message.getContent());
        return "index";
    }

}
