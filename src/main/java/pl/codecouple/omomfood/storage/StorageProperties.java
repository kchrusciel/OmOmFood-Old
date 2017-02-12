package pl.codecouple.omomfood.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Root folder location for storing files
     */
    private String rootLocation = "src/main/resources/static/images";

    /**
     * Offers folder location for storing files
     */
    private String offersLocation = "src/main/resources/static/images/offers";

    /**
     * Users folder location for storing files
     */
    private String usersLocation = "src/main/resources/static/images/users";

}
