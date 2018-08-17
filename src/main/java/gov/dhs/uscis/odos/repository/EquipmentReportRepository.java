package gov.dhs.uscis.odos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dhs.uscis.odos.domain.EquipmentReport;


/**
 * Spring Data JPA repository for the EquipmentReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentReportRepository extends JpaRepository<EquipmentReport, Long> {
	
	@Query("SELECT er FROM EquipmentReport er inner join er.conferenceRoomEquipment.conferenceRoom cf " +
			"where cf.conferenceRoomId = :conferenceRoomId")
	public List<EquipmentReport> findAllByConfRoomId(
			@Param("conferenceRoomId") Long conferenceRoomId);

}
