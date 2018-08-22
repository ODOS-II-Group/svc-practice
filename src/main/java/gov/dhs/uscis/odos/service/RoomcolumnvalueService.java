package gov.dhs.uscis.odos.service;

import gov.dhs.uscis.odos.service.dto.RoomcolumnvalueDTO;
import java.util.List;

/**
 * Service Interface for managing Roomcolumnvalue.
 */
public interface RoomcolumnvalueService {

    /**
     * Save a roomcolumnvalue.
     *
     * @param roomcolumnvalueDTO the entity to save
     * @return the persisted entity
     */
    RoomcolumnvalueDTO save(RoomcolumnvalueDTO roomcolumnvalueDTO);

    /**
     * Get all the roomcolumnvalues.
     *
     * @return the list of entities
     */
    List<RoomcolumnvalueDTO> findAll();

    /**
     * Get the "id" roomcolumnvalue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RoomcolumnvalueDTO findOne(Long id);

    /**
     * Delete the "id" roomcolumnvalue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
