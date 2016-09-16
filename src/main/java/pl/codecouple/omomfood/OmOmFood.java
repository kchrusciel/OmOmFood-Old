package pl.codecouple.omomfood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.codecouple.omomfood.account.roles.Role;
import pl.codecouple.omomfood.account.roles.RoleRepository;
import pl.codecouple.omomfood.offers.Offer;
import pl.codecouple.omomfood.offers.OfferRepository;

import java.time.LocalDateTime;

@SpringBootApplication
public class OmOmFood implements CommandLineRunner{

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(OmOmFood.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_USER"));

        offerRepository.save(new Offer("Fajny obiad",
                                       "Opis",
                                       "Sosnowiec",
                                       "food-150x150.png",
                                        LocalDateTime.now(),
                                        LocalDateTime.now()));
    }
}
