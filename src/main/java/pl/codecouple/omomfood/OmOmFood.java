package pl.codecouple.omomfood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.codecouple.omomfood.account.roles.RoleRepository;
import pl.codecouple.omomfood.account.users.UserRepository;
import pl.codecouple.omomfood.account.users.references.ReferenceRepository;
import pl.codecouple.omomfood.offers.OfferRepository;
import pl.codecouple.omomfood.storage.StorageProperties;
import pl.codecouple.omomfood.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableAsync
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
        storageService.deleteAll();
        storageService.init();

    }
}
