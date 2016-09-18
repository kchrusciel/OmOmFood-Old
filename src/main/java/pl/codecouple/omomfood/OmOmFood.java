package pl.codecouple.omomfood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.codecouple.omomfood.account.roles.Role;
import pl.codecouple.omomfood.account.roles.RoleRepository;
import pl.codecouple.omomfood.offers.OfferRepository;
import pl.codecouple.omomfood.storage.StorageProperties;
import pl.codecouple.omomfood.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class OmOmFood implements CommandLineRunner{

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    StorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(OmOmFood.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {

        offerRepository.deleteAll();

        storageService.deleteAll();
        storageService.init();

        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_USER"));


//        offerRepository.save(new Offer("Fajny obiad",
//                                       "Opis",
//                                       "Sosnowiec",
//                                       "food-150x150.png",
//                                        LocalDateTime.now(),
//                                        LocalDateTime.now()));
    }
}
