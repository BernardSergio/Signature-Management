package Sign.sergio.esigns.repository;

import Sign.sergio.esigns.entity.SignatureStatusData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureStatusRepository extends JpaRepository<SignatureStatusData, Integer> {
    // Add custom query methods if needed
}
