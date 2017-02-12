package pl.codecouple.omomfood.storage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;

    private final StorageProperties properties;

    @Autowired
    public StorageServiceImpl(final StorageProperties properties) {
        this.properties = properties;
        this.rootLocation = Paths.get(properties.getRootLocation());
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(Paths.get(properties.getOffersLocation()));
            Files.createDirectory(Paths.get(properties.getUsersLocation()));
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public void store(final MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public void storeFileInPath(final MultipartFile file, final Path path) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(path.resolve(file.getOriginalFilename())));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(Paths.get(properties.getOffersLocation()).toFile());
        FileSystemUtils.deleteRecursively(Paths.get(properties.getUsersLocation()).toFile());
    }
}
