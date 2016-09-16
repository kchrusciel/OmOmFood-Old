package pl.codecouple.omomfood.offers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class OfferController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
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

    @RequestMapping(value = "/offers/{offerID}",
            method = RequestMethod.GET)
    public String getOffer(@PathVariable long offerID,
                           Model model) {
        Offer offer = offerService.getOfferById(offerID);
        if (offer == null) {
            model.addAttribute("message", "Empty");
            return "messages";
        }
        model.addAttribute("offer", offerService.getOfferById(offerID));
        return "offer/offer";
    }

}
