package iv.movchanets.foodfinder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "menus")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "nutrients_id",
            referencedColumnName = "id")
    private Nutrients nutrients;

    @OneToMany(mappedBy = "menu",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    private Set<Meal> meals;
}
