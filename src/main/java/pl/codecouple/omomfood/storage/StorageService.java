package pl.codecouple.omomfood.storage;

import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * Created by Krzysztof Chruściel.
 */
public interface StorageService {

    void init();
    void store(MultipartFile file);
    void storeFileInPath(MultipartFile file, Path path);
    void deleteAll();


}
