package pl.codecouple.omomfood.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Krzysztof Chruściel.
 */
public interface StorageService {

    void init();
    void store(MultipartFile file);
    void deleteAll();

}
