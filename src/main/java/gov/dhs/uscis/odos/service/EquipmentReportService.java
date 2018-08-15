package gov.dhs.uscis.odos.service;

import gov.dhs.uscis.odos.domain.EquipmentReport;
import java.util.List;

/**
 * Service Interface for managing EquipmentReport.
 */
public interface EquipmentReportService {

    /**
     * Save a equipmentReport.
     *
     * @param equipmentReport the entity to save
     * @return the persisted entity
     */
    EquipmentReport save(EquipmentReport equipmentReport);

    /**
     * Get all the equipmentReports.
     *
     * @return the list of entities
     */
    List<EquipmentReport> findAll();

    /**
     * Get the "id" equipmentReport.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EquipmentReport findOne(Long id);

    /**
     * Delete the "id" equipmentReport.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
