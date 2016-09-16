package pl.codecouple.omomfood.account.registration;

/**
 * Created by Krzysztof Chruściel.
 */
public interface EmailService {
    void sendEmail(String title, String to, String content);
}
