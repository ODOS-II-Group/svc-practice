package gov.dhs.uscis.odos.service;

import gov.dhs.uscis.odos.service.dto.RoomcolumnreferenceDTO;
import java.util.List;

/**
 * Service Interface for managing Roomcolumnreference.
 */
public interface RoomcolumnreferenceService {

    /**
     * Save a roomcolumnreference.
     *
     * @param roomcolumnreferenceDTO the entity to save
     * @return the persisted entity
     */
    RoomcolumnreferenceDTO save(RoomcolumnreferenceDTO roomcolumnreferenceDTO);

    /**
     * Get all the roomcolumnreferences.
     *
     * @return the list of entities
     */
    List<RoomcolumnreferenceDTO> findAll();

    /**
     * Get the "id" roomcolumnreference.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RoomcolumnreferenceDTO findOne(Long id);

    /**
     * Delete the "id" roomcolumnreference.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
