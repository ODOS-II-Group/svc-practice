package gov.dhs.uscis.odos.service.impl;

import gov.dhs.uscis.odos.service.EquipmentReportService;
import gov.dhs.uscis.odos.domain.EquipmentReport;
import gov.dhs.uscis.odos.repository.EquipmentReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing EquipmentReport.
 */
@Service
@Transactional
public class EquipmentReportServiceImpl implements EquipmentReportService {

    private final Logger log = LoggerFactory.getLogger(EquipmentReportServiceImpl.class);

    private final EquipmentReportRepository equipmentReportRepository;

    public EquipmentReportServiceImpl(EquipmentReportRepository equipmentReportRepository) {
        this.equipmentReportRepository = equipmentReportRepository;
    }

    /**
     * Save a equipmentReport.
     *
     * @param equipmentReport the entity to save
     * @return the persisted entity
     */
    @Override
    public EquipmentReport save(EquipmentReport equipmentReport) {
        log.debug("Request to save EquipmentReport : {}", equipmentReport);
        return equipmentReportRepository.save(equipmentReport);
    }

    /**
     * Get all the equipmentReports.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EquipmentReport> findAll() {
        log.debug("Request to get all EquipmentReports");
        return equipmentReportRepository.findAll();
    }

    /**
     * Get one equipmentReport by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EquipmentReport findOne(Long id) {
        log.debug("Request to get EquipmentReport : {}", id);
        return equipmentReportRepository.findOne(id);
    }

    /**
     * Delete the equipmentReport by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EquipmentReport : {}", id);
        equipmentReportRepository.delete(id);
    }
}
