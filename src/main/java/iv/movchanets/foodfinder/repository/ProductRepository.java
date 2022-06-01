package iv.movchanets.foodfinder.repository;

import iv.movchanets.foodfinder.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
