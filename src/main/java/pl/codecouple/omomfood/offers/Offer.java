package pl.codecouple.omomfood.offers;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import pl.codecouple.omomfood.account.users.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by krzysztof.chrusciel on 2016-09-01.
 */
@Slf4j
@Data
@ToString
@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String description;
    private String city;

    private String fileName;

    private LocalDateTime createdDate;
    private LocalDateTime eventDate;

    @ManyToOne
    private User owner;

//    private List<User> interested = new ArrayList<>();

    public Offer() {
    }


    public Offer(String title, String description, String city, String fileName, LocalDateTime createdDate, LocalDateTime eventDate) {
        this.title = title;
        this.description = description;
        this.city = city;
        this.fileName = fileName;
        this.createdDate = createdDate;
        this.eventDate = eventDate;
//        this.owner = owner;
//        this.interested = interested;
    }
}
