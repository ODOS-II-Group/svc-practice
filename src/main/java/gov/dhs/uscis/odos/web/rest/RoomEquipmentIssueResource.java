package gov.dhs.uscis.odos.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.dhs.uscis.odos.domain.RoomEquipmentIssue;
import gov.dhs.uscis.odos.service.RoomEquipmentIssueService;
import gov.dhs.uscis.odos.web.rest.errors.BadRequestAlertException;
import gov.dhs.uscis.odos.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RoomEquipmentIssue.
 */
@RestController
@RequestMapping("/api")
public class RoomEquipmentIssueResource {

    private final Logger log = LoggerFactory.getLogger(RoomEquipmentIssueResource.class);

    private static final String ENTITY_NAME = "roomEquipmentIssue";

    private final RoomEquipmentIssueService roomEquipmentIssueService;

    public RoomEquipmentIssueResource(RoomEquipmentIssueService roomEquipmentIssueService) {
        this.roomEquipmentIssueService = roomEquipmentIssueService;
    }

    /**
     * POST  /room-equipment-issues : Create a new roomEquipmentIssue.
     *
     * @param roomEquipmentIssue the roomEquipmentIssue to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomEquipmentIssue, or with status 400 (Bad Request) if the roomEquipmentIssue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/room-equipment-issues")
    @Timed
    public ResponseEntity<RoomEquipmentIssue> createRoomEquipmentIssue(@Valid @RequestBody RoomEquipmentIssue roomEquipmentIssue) throws URISyntaxException {
        log.debug("REST request to save RoomEquipmentIssue : {}", roomEquipmentIssue);
        if (roomEquipmentIssue.getId() != null) {
            throw new BadRequestAlertException("A new roomEquipmentIssue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomEquipmentIssue result = roomEquipmentIssueService.save(roomEquipmentIssue);
        return ResponseEntity.created(new URI("/api/room-equipment-issues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /room-equipment-issues : Updates an existing roomEquipmentIssue.
     *
     * @param roomEquipmentIssue the roomEquipmentIssue to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomEquipmentIssue,
     * or with status 400 (Bad Request) if the roomEquipmentIssue is not valid,
     * or with status 500 (Internal Server Error) if the roomEquipmentIssue couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/room-equipment-issues")
    @Timed
    public ResponseEntity<RoomEquipmentIssue> updateRoomEquipmentIssue(@Valid @RequestBody RoomEquipmentIssue roomEquipmentIssue) throws URISyntaxException {
        log.debug("REST request to update RoomEquipmentIssue : {}", roomEquipmentIssue);
        if (roomEquipmentIssue.getId() == null) {
            return createRoomEquipmentIssue(roomEquipmentIssue);
        }
        RoomEquipmentIssue result = roomEquipmentIssueService.save(roomEquipmentIssue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomEquipmentIssue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /room-equipment-issues : get all the roomEquipmentIssues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of roomEquipmentIssues in body
     */
    @GetMapping("/room-equipment-issues")
    @Timed
    public List<RoomEquipmentIssue> getAllRoomEquipmentIssues() {
        log.debug("REST request to get all RoomEquipmentIssues");
        return roomEquipmentIssueService.findAll();
        }

    /**
     * GET  /room-equipment-issues/:id : get the "id" roomEquipmentIssue.
     *
     * @param id the id of the roomEquipmentIssue to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomEquipmentIssue, or with status 404 (Not Found)
     */
    @GetMapping("/room-equipment-issues/{id}")
    @Timed
    public ResponseEntity<RoomEquipmentIssue> getRoomEquipmentIssue(@PathVariable Long id) {
        log.debug("REST request to get RoomEquipmentIssue : {}", id);
        RoomEquipmentIssue roomEquipmentIssue = roomEquipmentIssueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(roomEquipmentIssue));
    }

    /**
     * DELETE  /room-equipment-issues/:id : delete the "id" roomEquipmentIssue.
     *
     * @param id the id of the roomEquipmentIssue to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/room-equipment-issues/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoomEquipmentIssue(@PathVariable Long id) {
        log.debug("REST request to delete RoomEquipmentIssue : {}", id);
        roomEquipmentIssueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
