package gov.dhs.uscis.odos.service.impl;

import gov.dhs.uscis.odos.service.RoomcolumnvalueService;
import gov.dhs.uscis.odos.domain.Roomcolumnvalue;
import gov.dhs.uscis.odos.repository.RoomcolumnvalueRepository;
import gov.dhs.uscis.odos.service.dto.RoomcolumnvalueDTO;
import gov.dhs.uscis.odos.service.mapper.RoomcolumnvalueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Roomcolumnvalue.
 */
@Service
@Transactional
public class RoomcolumnvalueServiceImpl implements RoomcolumnvalueService {

    private final Logger log = LoggerFactory.getLogger(RoomcolumnvalueServiceImpl.class);

    private final RoomcolumnvalueRepository roomcolumnvalueRepository;

    private final RoomcolumnvalueMapper roomcolumnvalueMapper;

    public RoomcolumnvalueServiceImpl(RoomcolumnvalueRepository roomcolumnvalueRepository, RoomcolumnvalueMapper roomcolumnvalueMapper) {
        this.roomcolumnvalueRepository = roomcolumnvalueRepository;
        this.roomcolumnvalueMapper = roomcolumnvalueMapper;
    }

    /**
     * Save a roomcolumnvalue.
     *
     * @param roomcolumnvalueDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomcolumnvalueDTO save(RoomcolumnvalueDTO roomcolumnvalueDTO) {
        log.debug("Request to save Roomcolumnvalue : {}", roomcolumnvalueDTO);
        Roomcolumnvalue roomcolumnvalue = roomcolumnvalueMapper.toEntity(roomcolumnvalueDTO);
        roomcolumnvalue = roomcolumnvalueRepository.save(roomcolumnvalue);
        return roomcolumnvalueMapper.toDto(roomcolumnvalue);
    }

    /**
     * Get all the roomcolumnvalues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomcolumnvalueDTO> findAll() {
        log.debug("Request to get all Roomcolumnvalues");
        return roomcolumnvalueRepository.findAll().stream()
            .map(roomcolumnvalueMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one roomcolumnvalue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RoomcolumnvalueDTO findOne(Long id) {
        log.debug("Request to get Roomcolumnvalue : {}", id);
        Roomcolumnvalue roomcolumnvalue = roomcolumnvalueRepository.findOne(id);
        return roomcolumnvalueMapper.toDto(roomcolumnvalue);
    }

    /**
     * Delete the roomcolumnvalue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Roomcolumnvalue : {}", id);
        roomcolumnvalueRepository.delete(id);
    }
}
