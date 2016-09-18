package pl.codecouple.omomfood.messages;

import pl.codecouple.omomfood.account.users.User;

import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
public interface MessageService {
    List<Message> getAllMessages(User user);
    Message getMessage(User user, Long id);
    void sendMessage(Message message);
    void readMessage();
}
