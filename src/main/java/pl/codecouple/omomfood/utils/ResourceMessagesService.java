package pl.codecouple.omomfood.utils;

/**
 * Created by Krzysztof Chruściel.
 */

public interface ResourceMessagesService {
    String getMessage(String id);
    String getParametrizedMessages(String id, Object[] objects);
}
