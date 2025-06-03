package aforo.productrateplanservice.discount;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DiscountRepository extends JpaRepository<Discount, Long> {
    List<Discount> findByIsDeletedFalse();
    Optional<Discount> findByIdAndIsDeletedFalse(Long id);
}
