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
import java.util.List;

@Slf4j
@Controller
public class OfferController {

    /** {@link OfferService} account service instance. */
    private OfferService offerService;
    /** {@link UserDetailsService} user details service instance. */
    private UserDetailsService userDetailsService;
    /** {@link MessageService} message service instance. */
    private MessageService messageService;

    @Autowired
    public OfferController(final OfferService offerService,
                           final UserDetailsService userDetailsService,
                           final MessageService messageService) {
        this.offerService = offerService;
        this.userDetailsService = userDetailsService;
        this.messageService = messageService;
    }


    /**
     * Todo
     *
     * @param city from search offers form.
     * @param date from search offers form.
     * @param modelAndView from request.
     * @return
     */
    @RequestMapping(value = "/offers",
            method = RequestMethod.POST)
    public ModelAndView showChosenAdverts(final @RequestParam(value = "city") String city,
                                          final @RequestParam(value = "date") String date,
                                          final ModelAndView modelAndView,
                                          final Pageable pageable) {

        if (!areCityAndDateEmpty(city, date)) {
            modelAndView.addObject("offers", offerService.getAllOffersByCityAndDate(city, LocalDateTime.parse(date)));
        } else if (!city.isEmpty()) {
            modelAndView.addObject("offers", offerService.getAllOffersByCity(city));
        } else if (!date.isEmpty()) {
//            offerService.getAllOffersByDateSortedByDate(LocalDateTime.parse(date));
            modelAndView.addObject("offers", offerService.getAllOffers(pageable));
        } else {
//            modelAndView.addObject("offers", offerService.getAllOffersSortedByDate());
            modelAndView.addObject("offers", offerService.getAllOffers(pageable));
        }

        Page<Offer> offersPageable = offerService.getAllOffers(pageable);
        PageWrapper<Offer> page = new PageWrapper<>(offersPageable, "/offers");
        modelAndView.addObject("offers", offersPageable.getContent());
        modelAndView.addObject("page", page);
        modelAndView.setViewName("offer/offers");
        return modelAndView;
    }

    /**
     * This method checks if city and date are not empty.
     *
     * @param city from request.
     * @param date from request.
     * @return <code>true</code> if city and date are not empty, <code>false</code> otherwise.
     */
    private boolean areCityAndDateEmpty(final @RequestParam(value = "city") String city,
                                        final @RequestParam(value = "date") String date) {
        return city.isEmpty() && date.isEmpty();
    }

    @RequestMapping(value = "/offers",
            method = RequestMethod.GET)
    public ModelAndView showAllOffers(final ModelAndView modelAndView,
                                      final Pageable pageable) {
        Page<Offer> offersPageable = offerService.getAllOffers(pageable);
        PageWrapper<Offer> page = new PageWrapper<>(offersPageable, "/offers");
        modelAndView.addObject("offers", offersPageable.getContent());
        modelAndView.addObject("page", page);
        modelAndView.setViewName("offer/offers");
        return modelAndView;
    }

    @RequestMapping(value = "/offers/my",
            method = RequestMethod.GET)
    public String showMyOffers(Model model) {
        model.addAttribute("offers", offerService.getAllMyOffers("giesem@o2.pl"));
        return "offer/offers";
    }

    @RequestMapping(value = "/offer/{offerID}",
            method = RequestMethod.GET)
    public String getOffer(final @PathVariable long offerID,
                           final Message messageForm,
                           final Model model) {
        Offer offer = offerService.getOfferById(offerID);
        if (offer == null) {
            model.addAttribute("message", "Empty or wrong offer ID");
            return "messages";
        }
        model.addAttribute("offer", offerService.getOfferById(offerID));
        model.addAttribute("user", userDetailsService.getLoggedUser());
        return "offer/offer";
    }



    @RequestMapping(value = "offers/{offerID}/message",
            method = RequestMethod.POST)
    public String sendMessageToOffer(@Valid Message message,
                                     @PathVariable("offerID") long offerID){
        message.isRead();
        message.getContent();
        log.debug("sendMessageToOffer");
        log.debug(message.getContent());
        return "index";
    }

}
