package Sign.sergio.esigns.repository;

import Sign.sergio.esigns.entity.DocumentStorageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentStorageRepository extends JpaRepository<DocumentStorageData, Integer> {
    // Add custom queries here if needed
}
