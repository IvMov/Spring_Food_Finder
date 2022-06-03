package iv.movchanets.foodfinder.repository;

import iv.movchanets.foodfinder.entity.Nutrients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NutrientsRepository extends JpaRepository<Nutrients, Long> {
}
