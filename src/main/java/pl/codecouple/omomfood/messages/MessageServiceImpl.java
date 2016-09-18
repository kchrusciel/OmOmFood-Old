package pl.codecouple.omomfood.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.codecouple.omomfood.account.users.User;

import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    MessageRepository messageRepository;

    @Override
    public void sendMessage(Message message) {
        messageRepository.save(message);
    }

    @Override
    public void readMessage() {

    }

    @Override
    public List<Message> getAllMessages(User user) {
        return messageRepository.findByOwner(user);
    }

    @Override
    public Message getMessage(User user, Long id) {
        return messageRepository.findByOwnerAndId(user, id);
    }
}
