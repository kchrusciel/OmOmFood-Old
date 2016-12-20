package pl.codecouple.omomfood.offers.types;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
public enum OfferTypesEnum {
    DOG("Dog", true),
    ALCOHOL("Alcohol", true),
    MUSIC("Music", false);

    private String type;
    private boolean available;

    OfferTypesEnum(String type, boolean available) {
        this.type = type;
        this.available = available;
    }

    public String getType() {
        return type;
    }

    public boolean isAvailable() {
        return available;
    }
}
