package pl.codecouple.omomfood.storage;

import lombok.Data;

/**
 * Created by Krzysztof Chruściel.
 */
public enum StorageFolders {
    OFFERS("/offers");

    private String folderName;

    StorageFolders(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
