package pl.codecouple.omomfood.offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.account.validator.OfferValidator;
import pl.codecouple.omomfood.storage.StorageFolders;
import pl.codecouple.omomfood.storage.StorageService;
import pl.codecouple.omomfood.utils.UserDetailsService;

import javax.validation.Valid;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Controller
public class OfferCreatorController {

    /** Template name which is returned on GET method.*/
    public static final String TEMPLATE_NAME_OFFER_NEW = "offer/new";
    /** Template name which is returned after new offer adding.*/
    public static final String TEMPLATE_NAME_SINGLE_OFFER = "offer/offer";

    /** {@link OfferValidator} offer validator instance. */
    private OfferValidator offerValidator;
    /** {@link UserDetailsService} user details service instance. */
    private UserDetailsService userDetailsService;
    /** {@link OfferService} account service instance. */
    private OfferService offerService;
    /** {@link StorageService} storage service instance. */
    private StorageService storageService;

    @Autowired
    public OfferCreatorController(final OfferValidator offerValidator,
                                  final UserDetailsService userDetailsService,
                                  final OfferService offerService,
                                  final StorageService storageService) {
        this.offerValidator = offerValidator;
        this.userDetailsService = userDetailsService;
        this.offerService = offerService;
        this.storageService = storageService;
    }

    @RequestMapping(value = "offers/new",
            method = RequestMethod.GET)
    public String showNewOfferPage(final Offer offerForm) {
        log.info("Show new offer page");
        return TEMPLATE_NAME_OFFER_NEW;
    }


    @RequestMapping(value = "offers/new",
            method = RequestMethod.POST)
    public String addNewOffer(final @Valid Offer offer,
                              final BindingResult bindingResult,
                              final Model model,
                              final @RequestParam("offerIcon") MultipartFile file) {

        log.info("Add new offer");
        log.debug("Offer: "  + offer);
        log.debug("BindingResult: " + bindingResult);

        log.debug("Validate offer");
        offerValidator.validate(offer, bindingResult);

        User user = userDetailsService.getLoggedUser();
        log.debug("USER:"+user);

        if (bindingResult.hasErrors()) {
            log.debug("Error during creating new offer");
            return TEMPLATE_NAME_OFFER_NEW;
        }

//        User user = userDetailsService.getLoggedUser();

        offer.setOwner(user);
        offer.setFileName(file.getOriginalFilename());
        offer.setCreatedDate(LocalDateTime.now());

        log.debug("Store offer in DB");
        offerService.addOffer(offer);

        log.debug("Store file");
        storageService.storeFileInPath(file, getPathForSaveOfferImage(user));

        model.addAttribute("offer", offer);

        return TEMPLATE_NAME_SINGLE_OFFER;
    }

    private Path getPathForSaveOfferImage(final User user) {
        return Paths.get(user.getUsername() + StorageFolders.OFFERS.getFolderName());
    }

}
