package pl.codecouple.omomfood.offers;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.offers.types.OfferDetailsTypes;
import pl.codecouple.omomfood.offers.types.OfferTypes;
import pl.codecouple.omomfood.utils.validators.Future;

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

    /** Offer title. */
    @NotEmpty
    @Size(min=2,max=50)
    private String title;

    /** Offer description. */
    @NotEmpty
    @Column(length = 500)
    private String description;

    /** Offer city. */
    @NotEmpty
    @Size(min=2,max=32)
    private String city;

    /** Phone number. */
    @NotEmpty
    private String phoneNumber;

    /** Offer price with specific precision. */
    @Column(nullable= false, precision=5, scale=2)    // Creates the database field with this size.
    @Digits(integer=5, fraction=2)
    private BigDecimal price;

    /** Quantity of {@link Offer}. */
    @Min(1)
    private int quantity;
    /** {@link Boolean} flag which store information about quantity. */
    private boolean unlimited;
    /** {@Link String } value with icon file name. */
    private String iconFileName;
    /** {@Link LocalDateTime} with {@link Offer} creation date. */
    private LocalDateTime createdDate;
    /** {@Link LocalDateTime} with event date. */
    @Future
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private LocalDateTime eventDate;

    @ManyToOne
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "offer_user", joinColumns = @JoinColumn(name = "offer_is"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> assignedUser = new ArrayList<>();

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = OfferDetailsTypes.class)
    private List<OfferDetailsTypes> offerDetailsTypes;

    @Column
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = OfferTypes.class)
    private List<OfferDetailsTypes> offerTypes;

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
     * @param iconFileName
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
                 String iconFileName,
                 LocalDateTime createdDate,
                 LocalDateTime eventDate,
                 User owner) {

        this(title, description, city, phoneNumber, price, quantity, iconFileName, createdDate, eventDate, owner, new ArrayList<>());
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
     * @param iconFileName
     * @param createdDate
     * @param eventDate
     * @param owner
     * @param offerTypeEna
     */
    public Offer(String title,
                 String description,
                 String city,
                 String phoneNumber,
                 BigDecimal price,
                 int quantity,
                 String iconFileName,
                 LocalDateTime createdDate,
                 LocalDateTime eventDate,
                 User owner,
                 List<OfferDetailsTypes> offerDetailsTypes) {

        this.title = title;
        this.description = description;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.quantity = quantity;
        this.iconFileName = iconFileName;
        this.createdDate = createdDate;
        this.eventDate = eventDate;
        this.owner = owner;
        this.offerDetailsTypes = offerDetailsTypes;
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
