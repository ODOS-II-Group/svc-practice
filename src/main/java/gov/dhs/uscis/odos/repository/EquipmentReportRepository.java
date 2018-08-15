package gov.dhs.uscis.odos.repository;

import gov.dhs.uscis.odos.domain.EquipmentReport;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EquipmentReport entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EquipmentReportRepository extends JpaRepository<EquipmentReport, Long> {

}
