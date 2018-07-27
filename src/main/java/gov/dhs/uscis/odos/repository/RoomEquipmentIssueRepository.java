package gov.dhs.uscis.odos.repository;

import gov.dhs.uscis.odos.domain.RoomEquipmentIssue;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RoomEquipmentIssue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RoomEquipmentIssueRepository extends JpaRepository<RoomEquipmentIssue, Long> {

}
