package gov.dhs.uscis.odos.repository;

import gov.dhs.uscis.odos.domain.Roomcolumnvalue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Roomcolumnvalue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomcolumnvalueRepository extends JpaRepository<Roomcolumnvalue, Long> {

}
