package iv.movchanets.foodfinder.repository;

import iv.movchanets.foodfinder.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
