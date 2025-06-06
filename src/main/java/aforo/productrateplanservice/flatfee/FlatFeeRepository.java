package aforo.productrateplanservice.flatfee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatFeeRepository extends JpaRepository<FlatFee, Long> {
}
