package pl.codecouple.omomfood.offers.types;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
public enum OfferTypes {
    DOG("Dog"),
    ALCOHOL("Alcohol"),
    MUSIC("Music");

    private String type;

    OfferTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
