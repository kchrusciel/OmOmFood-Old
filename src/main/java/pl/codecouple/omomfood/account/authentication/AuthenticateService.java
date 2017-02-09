package pl.codecouple.omomfood.account.authentication;

import org.springframework.social.connect.Connection;

/**
 * Created by Krzysztof Chruściel.
 */
public interface AuthenticateService {

    /**
     * This is method for social authentication.
     *
     * @param username for authentication details.
     */
    void authenticate(String username);

    boolean isUserExists(String providerUserId);

    boolean isUserEnabled(String providerUserId);

}
