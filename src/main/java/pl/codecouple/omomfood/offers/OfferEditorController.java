package pl.codecouple.omomfood.offers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Controller
public class OfferEditorController {

    @RequestMapping(value = "offers/{offerID}/edit",
            method = RequestMethod.GET)
    public String showEditOfferPage(final Offer offerForm,
                                    final @PathVariable("offerID") long offerID) {
        log.debug("Show edit offer page");
        return "offer/edit";
    }

    @RequestMapping(value = "offers/{offerID}/edit",
            method = RequestMethod.POST)
    public String editOfferPage(final @Valid Offer offer,
                                final @PathVariable("offerID") long offerID) {
        log.debug("Show edit offer page");
        return "offer/edit";
    }
}
