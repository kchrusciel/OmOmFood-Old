package pl.codecouple.omomfood.offers;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.codecouple.omomfood.account.users.User;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @NotEmpty
    @Size(min=2,max=50)
    private String title;
    @NotEmpty
    private String description;
    @NotEmpty
    @Size(min=2,max=32)
    private String city;
    @NotEmpty
    private String phoneNumber;

    @Column(nullable= false, precision=5, scale=2)    // Creates the database field with this size.
    @Digits(integer=5, fraction=2)
    private BigDecimal price;

    @Min(1)
    private int quantity;

    private String fileName;

    private LocalDateTime createdDate;

    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime eventDate;

    @ManyToOne
    private User owner;

//    private List<User> interested = new ArrayList<>();

    public Offer() {
    }

    public Offer(String description, String city, String phoneNumber, BigDecimal price, String fileName, LocalDateTime createdDate, LocalDateTime eventDate, User owner) {
        this.description = description;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.fileName = fileName;
        this.createdDate = createdDate;
        this.eventDate = eventDate;
        this.owner = owner;
    }
}
