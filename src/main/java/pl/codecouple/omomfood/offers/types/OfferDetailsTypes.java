package pl.codecouple.omomfood.offers.types;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
public enum OfferDetailsTypes {
    ANIMAL("offer.details.type.animal"),
    ALCOHOL("offer.details.type.alcohol"),
    GLUTEN("offer.details.type.gluten.free"),
    PARKING("offer.details.type.free.parking"),
    WIFI("offer.details.type.wifi"),
    VEGETARIAN("offer.details.type.vegetarian"),
    VEGE("offer.details.type.vege");

    private String type;

    OfferDetailsTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
