package pl.codecouple.omomfood.offers;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.codecouple.omomfood.account.users.User;
import pl.codecouple.omomfood.offers.price.Price;
import pl.codecouple.omomfood.offers.types.OfferDetailsTypes;
import pl.codecouple.omomfood.offers.types.OfferTypes;
import pl.codecouple.omomfood.utils.validators.Future;

import javax.persistence.*;
import javax.validation.constraints.Size;
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

    /** Offer price. */
    @Embedded
    private Price price;

    /** Quantity of {@link Offer}. */
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
     * This method create string for reservation details view.
     *
     * @return reservation quantity details "assignedUser/quantity"
     */
    public String getReservationQuantityDetails(){
        if(unlimited){
            return "∞";
        }
        return assignedUser.size() + "/" + quantity;
    }

    public Offer(String title, String description, String city, String phoneNumber, Price price, int quantity, boolean unlimited, String iconFileName, LocalDateTime createdDate, LocalDateTime eventDate, User owner, List<User> assignedUser, List<OfferDetailsTypes> offerDetailsTypes, List<OfferDetailsTypes> offerTypes) {
        this.title = title;
        this.description = description;
        this.city = city;
        this.phoneNumber = phoneNumber;
        this.price = price;
        this.quantity = quantity;
        this.unlimited = unlimited;
        this.iconFileName = iconFileName;
        this.createdDate = createdDate;
        this.eventDate = eventDate;
        this.owner = owner;
        this.assignedUser = assignedUser;
        this.offerDetailsTypes = offerDetailsTypes;
        this.offerTypes = offerTypes;
    }
}
