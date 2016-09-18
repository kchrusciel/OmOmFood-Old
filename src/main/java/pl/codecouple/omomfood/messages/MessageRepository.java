package pl.codecouple.omomfood.messages;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.codecouple.omomfood.account.users.User;

import java.util.List;

/**
 * Created by Krzysztof Chruściel.
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByOwner(User user);
    Message findByOwnerAndId(User user, Long id);
}
