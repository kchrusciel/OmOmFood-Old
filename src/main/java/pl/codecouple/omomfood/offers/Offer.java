package pl.codecouple.omomfood.offers;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.offers.types.OfferTypes;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    /** This is unique offer id*/
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "offer_user", joinColumns = @JoinColumn(name = "offer_is"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> assignedUser = new ArrayList<>();

    @Column
    @Enumerated
    @ElementCollection(targetClass = OfferTypes.class)
    private List<OfferTypes> offerTypes;

    /**
     * This is default empty constructor for {@link Offer}.
     */
    public Offer() {
    }

    /**
     * This is constructor for {@link Offer} class.
     *
     * @param title
     * @param description
     * @param city
     * @param phoneNumber
     * @param price
     * @param quantity
     * @param fileName
     * @param createdDate
     * @param eventDate
     * @param owner
     */
    public Offer(String title,
                 String description,
                 String city,
                 String phoneNumber,
                 BigDecimal price,
                 int quantity,
                 String fileName,
                 LocalDateTime createdDate,
                 LocalDateTime eventDate,
                 User owner) {

        this(title, description, city, phoneNumber, price, quantity, fileName, createdDate, eventDate, owner, new ArrayList<>());
    }

    /**
     * This is constructor for {@link Offer} class.
     *
     * @param title
     * @param description
     * @param city
     * @param phoneNumber
     * @param price
     * @param quantity
     * @param fileName
     * @param createdDate
     * @param eventDate
     * @param owner
     * @param offerTypes
     */
    public Offer(String title,
                 String description,
                 String city,
                 String phoneNumber,
                 BigDecimal price,
                 int quantity,
                 String fileName,
                 LocalDateTime createdDate,
                 LocalDateTime eventDate,
                 User owner,
                 List<OfferTypes> offerTypes) {

        this.title = title;
        this.description = description;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.quantity = quantity;
        this.fileName = fileName;
        this.createdDate = createdDate;
        this.eventDate = eventDate;
        this.owner = owner;
        this.offerTypes = offerTypes;
    }


    /**
     * This method create string for reservation details view.
     *
     * @return reservation quantity details "assignedUser/quantity"
     */
    public String getReservationQuantityDetails(){
        return assignedUser.size() + "/" + quantity;
    }
}
