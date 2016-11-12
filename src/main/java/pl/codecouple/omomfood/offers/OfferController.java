package pl.codecouple.omomfood.offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import pl.codecouple.omomfood.storage.StorageService;
import pl.codecouple.omomfood.utils.UserDetailsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class OfferController {

    private OfferService offerService;

    private OfferValidator offerValidator;

    private StorageService storageService;

    private UserDetailsService userDetailsService;

    @Autowired
    public OfferController(OfferService offerService, OfferValidator offerValidator, StorageService storageService, UserDetailsService userDetailsService) {
        this.offerService = offerService;
        this.offerValidator = offerValidator;
        this.storageService = storageService;
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = "/offers",
            method = RequestMethod.POST)
    public ModelAndView showChosenAdverts(@RequestParam(value = "city") String city,
                                          @RequestParam(value = "date") String date,
                                          ModelAndView modelAndView) {

        modelAndView.addObject("offers", getOffers(city));
        modelAndView.setViewName("offer/offers");
        return modelAndView;
    }

    private List<Offer> getOffers(String city) {
        if (city.isEmpty())
            return offerService.getAllOffersSortedByDate();
        return offerService.getAllOffersByCity(city);
    }


    @RequestMapping(value = "/offers",
            method = RequestMethod.GET)
    public ModelAndView showAllOffers(ModelAndView modelAndView) {
        modelAndView.setViewName("offer/offers");
        modelAndView.addObject("offers", offerService.getAllOffers());
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
    public String getOffer(@PathVariable long offerID,
                           Message messageForm,
                           Model model) {
        Offer offer = offerService.getOfferById(offerID);
        if (offer == null) {
            model.addAttribute("message", "Empty or wrong offer ID");
            return "messages";
        }
        model.addAttribute("offer", offerService.getOfferById(offerID));
        model.addAttribute("user", userDetailsService.getLoggedUser());
        return "offer/offer";
    }

    //Add new offer

    @RequestMapping(value = "offers/new",
                    method = RequestMethod.GET)
    public String showNewOfferPage(Offer offerForm) {
        log.debug("Show new offer page");
        return "offer/new";
    }


    @RequestMapping(value = "offers/new",
                    method = RequestMethod.POST)
    public String addNewOffer(@Valid Offer offer,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam("offerIcon") MultipartFile file) {

        log.debug("Add new offer");
        log.debug("Offer: "  + offer);
        log.debug("BindingResult: " + bindingResult);

        log.debug("Validate offer");
        offerValidator.validate(offer, bindingResult);

        if (bindingResult.hasErrors()) {
            log.debug("Error during register");
            return "offer/new";
        }

        User user = userDetailsService.getLoggedUser();

        offer.setOwner(user);
        offer.setFileName(file.getOriginalFilename());
        offer.setCreatedDate(LocalDateTime.now());

        log.debug("Store offer in DB");
        offerService.addOffer(offer);

        log.debug("Store file");
        storageService.store(file);

        model.addAttribute("offer", offer);

        return "offer/offer";
    }

    //End

    //Edit offer

    @RequestMapping(value = "offers/{offerID}/edit",
            method = RequestMethod.GET)
    public String showEditOfferPage(Offer offerForm,
                                    @PathVariable("offerID") long offerID) {
        log.debug("Show edit offer page");
        return "offer/edit";
    }

    @RequestMapping(value = "offers/{offerID}/edit",
            method = RequestMethod.POST)
    public String editOfferPage(@Valid Offer offer,
                                @PathVariable("offerID") long offerID) {
        log.debug("Show edit offer page");
        return "offer/edit";
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
