package Sign.sergio.esigns.repository;

import Sign.sergio.esigns.entity.DocumentCategoryData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentCategoryRepository extends JpaRepository<DocumentCategoryData, Integer> {
    // You can add custom query methods here if needed
}
