package pl.codecouple.omomfood;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import pl.codecouple.omomfood.storage.StorageProperties;
import pl.codecouple.omomfood.storage.StorageService;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableAsync
@EnableCaching
public class OmOmFood implements CommandLineRunner{


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
