package iv.movchanets.foodfinder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long newId;

    @Column
    private Long id;

    @Column
    private String imageType;

    @Column
    private String title;

    @Column
    private Integer readyInMinutes;

    @Column
    private Double servings;

    @Column
    private String sourceUrl;

    @ManyToMany(mappedBy = "meals")
    private Set<Menu> eventsPaid = new HashSet<>();

}
