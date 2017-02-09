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

    /** Message ID. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /** Message title. */
    private String title;

    /** Message content. */
    @NotEmpty
    private String content;

    /** Message {@link java.time.LocalDateTime} creation date. */
    private LocalDateTime creationDate;

    /** Value {@link Boolean} which store information about read message. */
    private boolean isRead;

    /** Recipient {@link User} for which message is addressed. */
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name="recipient")
    private User recipient;

    /** Author {@link User} of message. */
    @ManyToOne(fetch=FetchType.LAZY, optional = false)
    @JoinColumn(name="author")
    private User author;

    /**
     * Empty constructor for {@link Message}
     */
    public Message() {
    }

    /**
     * Constructor for {@link Message}
     *
     * @param title of the message.
     * @param content of the message.
     * @param creationDate of the message.
     * @param author of the message.
     * @param recipient of the message.
     */
    public Message(final String title,
                   final String content,
                   final LocalDateTime creationDate,
                   final User author,
                   final User recipient) {
        this.title = title;
        this.content = content;
        this.creationDate = creationDate;
        this.isRead = false;
        this.author = author;
        this.recipient = recipient;
    }
}
