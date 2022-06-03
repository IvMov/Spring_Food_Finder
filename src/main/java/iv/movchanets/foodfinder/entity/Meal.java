package iv.movchanets.foodfinder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "meals")
public class Meal {

    @Id
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

    @ManyToOne
    @JoinColumn(name = "Menu",
            referencedColumnName = "id")
    private Menu menu;

}
