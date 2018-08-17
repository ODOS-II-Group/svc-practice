package gov.dhs.uscis.odos.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.domain.EquipmentReport;
import gov.dhs.uscis.odos.repository.EquipmentReportRepository;
import gov.dhs.uscis.odos.service.EquipmentReportService;
import gov.dhs.uscis.odos.service.dto.EquipmentReportDTO;
import gov.dhs.uscis.odos.service.mapper.EquipmentReportMapper;

/**
 * Service Implementation for managing EquipmentReport.
 */
@Service
@Transactional
public class EquipmentReportServiceImpl implements EquipmentReportService {

    private final Logger log = LoggerFactory.getLogger(EquipmentReportServiceImpl.class);

    private final EquipmentReportRepository equipmentReportRepository;
    
    @Inject
    private EquipmentReportMapper equipmentReportMapper;

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
    public EquipmentReportDTO save(EquipmentReportDTO equipmentReportDTO) {
        log.debug("Request to save EquipmentReport : {}", equipmentReportDTO);
        EquipmentReport equipmentReport = equipmentReportRepository.save(equipmentReportMapper.toEntity(equipmentReportDTO));
        return equipmentReportMapper.toDto(equipmentReport);
    }

    /**
     * Get all the equipmentReports.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EquipmentReportDTO> findAllById(Long id) {
        log.debug("Request to get all EquipmentReports");
        return equipmentReportRepository.findAllByConfRoomId(id).stream()
        		.map(equipmentReportMapper::toDto)
        		.collect(Collectors.toCollection(LinkedList::new));
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
