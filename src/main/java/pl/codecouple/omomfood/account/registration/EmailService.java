package pl.codecouple.omomfood.account.registration;

/**
 * Created by Krzysztof Chruściel.
 */
public interface EmailService {
    void sendEmail(String title, String to, String content);
    void sendConfirmationEmail(String to, String content);
    void sendWelcomeEmail(String to);
}
