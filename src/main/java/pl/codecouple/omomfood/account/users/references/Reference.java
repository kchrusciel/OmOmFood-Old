package pl.codecouple.omomfood.account.users.references;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.codecouple.omomfood.account.users.User;

import javax.persistence.*;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@NoArgsConstructor
@Data
@Entity
public class Reference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String referenceContent;


    @ManyToOne
    private User author;

    @ManyToOne
    private User about;

    public Reference(String referenceContent, User author, User about) {
        this.referenceContent = referenceContent;
        this.author = author;
        this.about = about;
    }
}
