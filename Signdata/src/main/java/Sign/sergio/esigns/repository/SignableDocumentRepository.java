package Sign.sergio.esigns.repository;

import Sign.sergio.esigns.entity.SignableDocumentData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SignableDocumentRepository extends JpaRepository<SignableDocumentData, Integer> {
    // Add custom queries if needed
}
