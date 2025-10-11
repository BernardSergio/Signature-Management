package Sign.sergio.esigns.repository;

import Sign.sergio.esigns.entity.SignatureRequestData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignatureRequestRepository extends JpaRepository<SignatureRequestData, Integer> {
    // Add custom query methods here if needed
}
