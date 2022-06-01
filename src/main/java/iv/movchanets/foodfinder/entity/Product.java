package iv.movchanets.foodfinder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer calories;

    @Column
    private Double proteins;

    @Column
    private Double carbohydrates;

    @Column
    private Double fats;

    @ElementCollection
    @JoinTable(name = "product_seasons",
            joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "season")
    @Enumerated(EnumType.STRING)
    Set<ProductSeason> seasons = new HashSet<>();


}
