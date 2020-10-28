package seller.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seller.entities.Seller;

import java.util.UUID;

public interface SellerRepository extends JpaRepository<Seller, UUID> {
    @Query("SELECT s FROM Seller s WHERE s.firstName = :firstName and s.lastName = :lastName")
    Seller findSellerByName(@Param("firstName") String firstName, @Param("lastName") String lastName);
}
