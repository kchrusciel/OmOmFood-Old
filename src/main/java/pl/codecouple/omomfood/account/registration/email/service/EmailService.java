package pl.codecouple.omomfood.account.registration.email.service;

/**
 * This is {@link EmailService} interface for email purposes.
 * This interface have three methods. First method is used for
 * general sending. Last two are for send specific email after
 * registration and confirmation process.
 *
 * @author Krzysztof Chruściel
 */
public interface EmailService {
    /**
     * This method send email to specific address, with specific title and content.
     * @param title of the email.
     * @param to where the email will be send.
     * @param content of the email.
     */
    void sendEmail(String title, String to, String content);
    /**
     * This method send confirmation email after registration process to specific address.
     * @param to where the email will be send.
     * @param content of the email.
     */
    void sendConfirmationEmail(String to, String content);

    /**
     * This method send welcome email after confirmation process to specific address.
     * @param to where email will be send.
     */
    void sendWelcomeEmail(String to);
}
