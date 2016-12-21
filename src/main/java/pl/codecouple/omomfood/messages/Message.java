package pl.codecouple.omomfood.messages;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import pl.codecouple.omomfood.account.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
@Slf4j
@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @NotEmpty
    private String content;

    private Long offerID;

    private LocalDateTime creationDate;

    private boolean isRead;

    private User toUser;

    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name="owner")
    private User owner;

    public Message() {
    }

    public Message(final String content,
                   final Long offerID,
                   final LocalDateTime creationDate,
                   final User owner,
                   final User toUser) {
        log.debug("Create message");
        this.content = content;
        this.offerID = offerID;
        this.creationDate = creationDate;
        this.isRead = false;
        this.owner = owner;
        this.toUser = toUser;
    }
}
