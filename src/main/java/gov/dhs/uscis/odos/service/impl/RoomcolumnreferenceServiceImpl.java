package gov.dhs.uscis.odos.service.impl;

import gov.dhs.uscis.odos.service.RoomcolumnreferenceService;
import gov.dhs.uscis.odos.domain.Roomcolumnreference;
import gov.dhs.uscis.odos.repository.RoomcolumnreferenceRepository;
import gov.dhs.uscis.odos.service.dto.RoomcolumnreferenceDTO;
import gov.dhs.uscis.odos.service.mapper.RoomcolumnreferenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Roomcolumnreference.
 */
@Service
@Transactional
public class RoomcolumnreferenceServiceImpl implements RoomcolumnreferenceService {

    private final Logger log = LoggerFactory.getLogger(RoomcolumnreferenceServiceImpl.class);

    private final RoomcolumnreferenceRepository roomcolumnreferenceRepository;

    private final RoomcolumnreferenceMapper roomcolumnreferenceMapper;

    public RoomcolumnreferenceServiceImpl(RoomcolumnreferenceRepository roomcolumnreferenceRepository, RoomcolumnreferenceMapper roomcolumnreferenceMapper) {
        this.roomcolumnreferenceRepository = roomcolumnreferenceRepository;
        this.roomcolumnreferenceMapper = roomcolumnreferenceMapper;
    }

    /**
     * Save a roomcolumnreference.
     *
     * @param roomcolumnreferenceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomcolumnreferenceDTO save(RoomcolumnreferenceDTO roomcolumnreferenceDTO) {
        log.debug("Request to save Roomcolumnreference : {}", roomcolumnreferenceDTO);
        Roomcolumnreference roomcolumnreference = roomcolumnreferenceMapper.toEntity(roomcolumnreferenceDTO);
        roomcolumnreference = roomcolumnreferenceRepository.save(roomcolumnreference);
        return roomcolumnreferenceMapper.toDto(roomcolumnreference);
    }

    /**
     * Get all the roomcolumnreferences.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomcolumnreferenceDTO> findAll() {
        log.debug("Request to get all Roomcolumnreferences");
        return roomcolumnreferenceRepository.findAll().stream()
            .map(roomcolumnreferenceMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one roomcolumnreference by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RoomcolumnreferenceDTO findOne(Long id) {
        log.debug("Request to get Roomcolumnreference : {}", id);
        Roomcolumnreference roomcolumnreference = roomcolumnreferenceRepository.findOne(id);
        return roomcolumnreferenceMapper.toDto(roomcolumnreference);
    }

    /**
     * Delete the roomcolumnreference by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Roomcolumnreference : {}", id);
        roomcolumnreferenceRepository.delete(id);
    }
}
