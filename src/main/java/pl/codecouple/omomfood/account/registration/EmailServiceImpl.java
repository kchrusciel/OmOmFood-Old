package pl.codecouple.omomfood.account.registration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(String title, String to, String content) {

        MimeMessage mail = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);

            helper.setTo(to);
            helper.setReplyTo("test@omom.pl");
            helper.setFrom("test@omom.pl");
            helper.setSubject(title);
            helper.setText(content);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        javaMailSender.send(mail);

    }

}
