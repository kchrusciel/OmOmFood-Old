package pl.codecouple.omomfood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.codecouple.omomfood.account.roles.Role;
import pl.codecouple.omomfood.account.roles.RoleRepository;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.account.users.UserRepository;
import pl.codecouple.omomfood.account.users.references.Reference;
import pl.codecouple.omomfood.account.users.references.ReferenceRepository;
import pl.codecouple.omomfood.offers.Offer;
import pl.codecouple.omomfood.offers.OfferRepository;
import pl.codecouple.omomfood.offers.types.OfferTypes;
import pl.codecouple.omomfood.storage.StorageProperties;
import pl.codecouple.omomfood.storage.StorageService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class OmOmFood implements CommandLineRunner{

    @Autowired
    OfferRepository offerRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReferenceRepository referenceRepository;

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


        User user = new User("Jan", "Nowak", "jan@nowak", "password", "password");
        User jan = new User("Kamil", "Kowalski", "jan123@nowak", "password", "password");
        user.setConfirmationStatus(true);


        userRepository.save(user);
        userRepository.save(jan);

        referenceRepository.save(new Reference("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.1", jan, user));
        referenceRepository.save(new Reference("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.2", jan, user));
        referenceRepository.save(new Reference("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer posuere erat a ante.3", jan, user));


        List<OfferTypes> offerTypes = new ArrayList<>(Arrays.asList(OfferTypes.ALCOHOL, OfferTypes.DOG, OfferTypes.MUSIC));

        offerRepository.save(new Offer("Fajny obiad",
                                       "Opis",
                                       "Sosnowiec",
                                       "500-500-500",
                                        new BigDecimal(50),
                                        4,
                                       "food-150x150.png",
                                        LocalDateTime.now(),
                                        LocalDateTime.now(),
                                        user,
                                        offerTypes));

        for(int i=0; i<100; i++){
            offerRepository.save(new Offer("Test",
                    "Test",
                    "Katowice",
                    "500-500-500",
                    new BigDecimal(50),
                    1,
                    "food-150x150.png",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    jan,
                    offerTypes));
        }




    }
}
