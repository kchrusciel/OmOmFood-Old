package pl.codecouple.omomfood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import pl.codecouple.omomfood.account.roles.Role;
import pl.codecouple.omomfood.account.roles.RoleEnum;
import pl.codecouple.omomfood.account.roles.RoleRepository;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.account.users.UserRepository;
import pl.codecouple.omomfood.account.users.references.Reference;
import pl.codecouple.omomfood.account.users.references.ReferenceRepository;
import pl.codecouple.omomfood.offers.Offer;
import pl.codecouple.omomfood.offers.OfferRepository;
import pl.codecouple.omomfood.offers.types.OfferTypesEnum;
import pl.codecouple.omomfood.storage.StorageProperties;
import pl.codecouple.omomfood.storage.StorageService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

//        offerRepository.deleteAll();
//
//        storageService.deleteAll();
//        storageService.init();
//
//        roleRepository.save(new Role(RoleEnum.ROLE_ADMIN));
//        roleRepository.save(new Role(RoleEnum.ROLE_USER));
//
//
//
//        List<OfferTypesEnum> offerTypeEna = new ArrayList<>(Arrays.asList(OfferTypesEnum.ALCOHOL, OfferTypesEnum.DOG, OfferTypesEnum.MUSIC));
//
//        offerRepository.save(new Offer("Fajny obiad",
//                                       "Opis",
//                                       "Sosnowiec",
//                                       "500-500-500",
//                                        new BigDecimal(50),
//                                        4,
//                                       "food-150x150.png",
//                                        LocalDateTime.now(),
//                                        LocalDateTime.now(),
//                userRepository.findOne(1L),
//                offerTypeEna));
//
//        for(int i=0; i<100; i++){
//            offerRepository.save(new Offer(i+ " :Test",
//                    "Test",
//                    "Katowice",
//                    "500-500-500",
//                    new BigDecimal(i),
//                    1,
//                    "food-150x150.png",
//                    LocalDateTime.now(),
//                    LocalDateTime.now().plusDays(i),
//                    userRepository.findOne(2L),
//                    offerTypeEna));
//        }



    }
}
