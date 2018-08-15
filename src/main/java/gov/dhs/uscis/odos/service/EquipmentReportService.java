package gov.dhs.uscis.odos.service;

import java.util.List;

import gov.dhs.uscis.odos.service.dto.EquipmentReportDTO;

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
    EquipmentReportDTO save(EquipmentReportDTO equipmentReportDTO);

    /**
     * Get all the equipmentReports.
     *
     * @return the list of entities
     */
    List<EquipmentReportDTO> findAll();

    /**
     * Get the "id" equipmentReport.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EquipmentReportDTO findOne(Long id);

    /**
     * Delete the "id" equipmentReport.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
