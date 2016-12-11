package pl.codecouple.omomfood.offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
public class OfferController {

    /** {@link OfferService} account service instance. */
    private OfferService offerService;
    /** {@link OfferValidator} offer validator instance. */
    private OfferValidator offerValidator;
    /** {@link StorageService} storage service instance. */
    private StorageService storageService;
    /** {@link UserDetailsService} user details service instance. */
    private UserDetailsService userDetailsService;
    /** {@link MessageService} message service instance. */
    private MessageService messageService;

    @Autowired
    public OfferController(OfferService offerService,
                           OfferValidator offerValidator,
                           StorageService storageService,
                           UserDetailsService userDetailsService,
                           MessageService messageService) {
        this.offerService = offerService;
        this.offerValidator = offerValidator;
        this.storageService = storageService;
        this.userDetailsService = userDetailsService;
        this.messageService = messageService;
    }

    @RequestMapping(value = "/offers",
            method = RequestMethod.POST)
    public String showChosenAdverts(HttpServletRequest request) {
        request.getSession().setAttribute("productList", null);
        return "redirect:/offers/page/1";
    }

//    @RequestMapping(value = "/offers",
//            method = RequestMethod.POST)
//    public ModelAndView showChosenAdverts(@RequestParam(value = "city") String city,
//                                          @RequestParam(value = "date") String date,
//                                          ModelAndView modelAndView) {
//
//        modelAndView.addObject("offers", getOffers(city));
//        modelAndView.setViewName("offer/offers");
//        return modelAndView;
//    }

    private List<Offer> getOffers(String city) {
        if (city.isEmpty())
            return offerService.getAllOffersSortedByDate();
        return offerService.getAllOffersByCity(city);
    }


//    @RequestMapping(value = "/offers",
//            method = RequestMethod.GET)
//    public ModelAndView showAllOffers(ModelAndView modelAndView) {
//        modelAndView.setViewName("offer/offers");
//        modelAndView.addObject("offers", offerService.getAllOffers());
//        return modelAndView;
//    }

    @RequestMapping(value = "/offers",
            method = RequestMethod.GET)
    public String showAllOffers(HttpServletRequest request) {
        request.getSession().setAttribute("productList", null);
        return "redirect:/offers/page/1";
    }

    @RequestMapping(value = "/offers/page/{pageNumber}",
                method = RequestMethod.GET)
    public ModelAndView showOffersPage(HttpServletRequest request,
                                 @PathVariable int pageNumber,
                                 ModelAndView modelAndView){

        PagedListHolder<?> pagedListHolder = (PagedListHolder<?>) request.getSession().getAttribute("productList");

        if(pagedListHolder == null){
            pagedListHolder = new PagedListHolder<>(offerService.getAllOffers());
            pagedListHolder.setPageSize(20);
        } else {
            final int goToPage = pageNumber - 1;
            if(goToPage <= pagedListHolder.getPageCount() && goToPage >= 0){
                pagedListHolder.setPage(goToPage);
            }
        }


        int currentIndex = pagedListHolder.getPage() + 1;
        int beginIndex = Math.max(1, currentIndex - 20);
        int endIndex = Math.min(beginIndex + 5, pagedListHolder.getPageCount());


        modelAndView.addObject("currentIndex", currentIndex);
        modelAndView.addObject("beginIndex", beginIndex);
        modelAndView.addObject("endIndex", endIndex);
        modelAndView.addObject("allPagesCount", pagedListHolder.getPageCount());
        modelAndView.addObject("offers", pagedListHolder);
        modelAndView.addObject("baseURL", "/offers/page/");

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
