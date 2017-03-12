package pl.codecouple.omomfood.account.settings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.utils.UserDetailsService;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Controller
public class SettingsController {

    /** Account settings template name.*/
    public static final String ACCOUNT_SETTINGS = "account/settings";
    /** Settings endpoint. */
    public static final String SETTINGS = "/settings";

    /** {@link UserDetailsService} user details service instance. */
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * This is "/settings" GET endpoint.
     * This method shows account settings page.
     *
     * @param settingsForm for form validation.
     * @return <code>String</code> with template name.
     */
    @GetMapping(SETTINGS)
    public String showSettingsPage(final SettingsForm settingsForm){
        fillSettingsForm(settingsForm);
        return ACCOUNT_SETTINGS;
    }

    /**
     * This is method for filling attributes in {@link SettingsForm}
     *
     * @param settingsForm for filling attributes
     */
    private void fillSettingsForm(final SettingsForm settingsForm) {
        User loggedUser = userDetailsService.getLoggedUser();
        settingsForm.setEmail(loggedUser.getEmail());
        settingsForm.setFirstName(loggedUser.getFirstName());
        settingsForm.setLastName(loggedUser.getLastName());
        settingsForm.setAvatarFileName(loggedUser.getAvatarFileName());
    }


}
