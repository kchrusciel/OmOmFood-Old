package pl.codecouple.omomfood.offers.types;

/**
 * Created by Krzysztof Chruściel.
 */
public enum OfferType {
    DINNER("offer.type.dinner"),
    TAKEOUT("offer.type.takeout"),
    ONTHESPOT("offer.type.onthespot");

    private String offerType;

    OfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getOfferType() {
        return offerType;
    }
}
