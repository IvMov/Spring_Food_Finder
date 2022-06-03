package iv.movchanets.foodfinder.repository;

import iv.movchanets.foodfinder.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {
}
