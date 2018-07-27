package gov.dhs.uscis.odos.service;

import gov.dhs.uscis.odos.domain.RoomEquipmentIssue;
import java.util.List;

/**
 * Service Interface for managing RoomEquipmentIssue.
 */
public interface RoomEquipmentIssueService {

    /**
     * Save a roomEquipmentIssue.
     *
     * @param roomEquipmentIssue the entity to save
     * @return the persisted entity
     */
    RoomEquipmentIssue save(RoomEquipmentIssue roomEquipmentIssue);

    /**
     * Get all the roomEquipmentIssues.
     *
     * @return the list of entities
     */
    List<RoomEquipmentIssue> findAll();

    /**
     * Get the "id" roomEquipmentIssue.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RoomEquipmentIssue findOne(Long id);

    /**
     * Delete the "id" roomEquipmentIssue.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
