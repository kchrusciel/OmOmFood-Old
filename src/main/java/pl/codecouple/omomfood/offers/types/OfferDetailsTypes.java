package pl.codecouple.omomfood.offers.types;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
public enum OfferDetailsTypes {
    ANIMAL("Animal"),
    ALCOHOL("Alcohol"),
    GLUTEN("Gluten free"),
    PARKING("Free Parking"),
    WIFI("Internet"),
    VEGETARIAN("Vegetarian"),
    VEGE("Vegan");

    private String type;

    OfferDetailsTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
