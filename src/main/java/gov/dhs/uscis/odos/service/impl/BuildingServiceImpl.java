package gov.dhs.uscis.odos.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.domain.Building;
import gov.dhs.uscis.odos.repository.BuildingRepository;
import gov.dhs.uscis.odos.service.BuildingService;
import gov.dhs.uscis.odos.service.dto.BuildingDTO;
import gov.dhs.uscis.odos.service.mapper.BuildingMapper;

/**
 * Service Implementation for managing Building.
 */
@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {

    private final Logger log = LoggerFactory.getLogger(BuildingServiceImpl.class);

    private final BuildingRepository buildingRepository;

    

    public BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
      
    }

    /**
     * Save a building.
     *
     * @param buildingDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public BuildingDTO save(BuildingDTO buildingDTO) {
        log.debug("Request to save Building : {}", buildingDTO);
        Building building  = BuildingMapper.INSTANCE.toEntity( buildingDTO );
        building = buildingRepository.save(building);
        return BuildingMapper.INSTANCE.toDto( building );
    }

    /**
     * Get all the buildings.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<BuildingDTO> findAll() {
        log.debug("Request to get all Buildings");
        return null;
//        return buildingRepository.findAll().stream()
//            .map(buildingMapper::toDto)
//            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one building by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public BuildingDTO findOne(Long id) {
        log.debug("Request to get Building : {}", id);
        return null;
//        Building building = buildingRepository.findOne(id);
//        return buildingMapper.toDto(building);
    }

    /**
     * Delete the building by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Building : {}", id);
        buildingRepository.delete(id);
    }
}
