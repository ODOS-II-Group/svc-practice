package gov.dhs.uscis.odos.repository;

import gov.dhs.uscis.odos.domain.Roomcolumnreference;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Roomcolumnreference entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomcolumnreferenceRepository extends JpaRepository<Roomcolumnreference, Long> {

}
