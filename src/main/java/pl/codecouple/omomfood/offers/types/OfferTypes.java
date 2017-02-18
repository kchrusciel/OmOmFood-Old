package pl.codecouple.omomfood.offers.types;

/**
 * Created by Krzysztof Chruściel.
 */
public enum OfferTypes {
    DINNER("Dinner"),
    TAKEOUT("Takeout"),
    ONTHESPOT("On the spot");

    private String offerType;

    OfferTypes(String offerType) {
        this.offerType = offerType;
    }
}
