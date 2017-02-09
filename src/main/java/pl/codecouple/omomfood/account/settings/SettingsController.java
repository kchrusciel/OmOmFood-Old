package pl.codecouple.omomfood.account.settings;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Controller
public class SettingsController {


    public static final String ACCOUNT_SETTINGS = "account/settings";
    public static final String SETTINGS = "/settings";

    /**
     * This is "/settings" GET endpoint.
     * This method shows account settings page.
     *
     * @return <code>String</code> with template name.
     */
    @GetMapping(SETTINGS)
    public String showSettingsPage(){
        return ACCOUNT_SETTINGS;
    }

}
