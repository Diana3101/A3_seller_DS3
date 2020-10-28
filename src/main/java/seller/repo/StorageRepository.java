package seller.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import seller.entities.Storage;
import seller.entities.Thing;

import java.util.UUID;

public interface StorageRepository extends JpaRepository<Storage, UUID> {
    @Query("SELECT s FROM Storage s WHERE s.thing = :thing")
    Storage findByThing(@Param("thing") Thing thing);
}
