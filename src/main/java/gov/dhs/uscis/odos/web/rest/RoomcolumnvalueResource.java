package gov.dhs.uscis.odos.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.dhs.uscis.odos.service.RoomcolumnvalueService;
import gov.dhs.uscis.odos.web.rest.errors.BadRequestAlertException;
import gov.dhs.uscis.odos.web.rest.util.HeaderUtil;
import gov.dhs.uscis.odos.service.dto.RoomcolumnvalueDTO;
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
 * REST controller for managing Roomcolumnvalue.
 */
@RestController
@RequestMapping("/api")
public class RoomcolumnvalueResource {

    private final Logger log = LoggerFactory.getLogger(RoomcolumnvalueResource.class);

    private static final String ENTITY_NAME = "roomcolumnvalue";

    private final RoomcolumnvalueService roomcolumnvalueService;

    public RoomcolumnvalueResource(RoomcolumnvalueService roomcolumnvalueService) {
        this.roomcolumnvalueService = roomcolumnvalueService;
    }

    /**
     * POST  /roomcolumnvalues : Create a new roomcolumnvalue.
     *
     * @param roomcolumnvalueDTO the roomcolumnvalueDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomcolumnvalueDTO, or with status 400 (Bad Request) if the roomcolumnvalue has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/roomcolumnvalues")
    @Timed
    public ResponseEntity<RoomcolumnvalueDTO> createRoomcolumnvalue(@Valid @RequestBody RoomcolumnvalueDTO roomcolumnvalueDTO) throws URISyntaxException {
        log.debug("REST request to save Roomcolumnvalue : {}", roomcolumnvalueDTO);
        if (roomcolumnvalueDTO.getId() != null) {
            throw new BadRequestAlertException("A new roomcolumnvalue cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomcolumnvalueDTO result = roomcolumnvalueService.save(roomcolumnvalueDTO);
        return ResponseEntity.created(new URI("/api/roomcolumnvalues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /roomcolumnvalues : Updates an existing roomcolumnvalue.
     *
     * @param roomcolumnvalueDTO the roomcolumnvalueDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomcolumnvalueDTO,
     * or with status 400 (Bad Request) if the roomcolumnvalueDTO is not valid,
     * or with status 500 (Internal Server Error) if the roomcolumnvalueDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/roomcolumnvalues")
    @Timed
    public ResponseEntity<RoomcolumnvalueDTO> updateRoomcolumnvalue(@Valid @RequestBody RoomcolumnvalueDTO roomcolumnvalueDTO) throws URISyntaxException {
        log.debug("REST request to update Roomcolumnvalue : {}", roomcolumnvalueDTO);
        if (roomcolumnvalueDTO.getId() == null) {
            return createRoomcolumnvalue(roomcolumnvalueDTO);
        }
        RoomcolumnvalueDTO result = roomcolumnvalueService.save(roomcolumnvalueDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomcolumnvalueDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /roomcolumnvalues : get all the roomcolumnvalues.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of roomcolumnvalues in body
     */
    @GetMapping("/roomcolumnvalues")
    @Timed
    public List<RoomcolumnvalueDTO> getAllRoomcolumnvalues() {
        log.debug("REST request to get all Roomcolumnvalues");
        return roomcolumnvalueService.findAll();
        }

    /**
     * GET  /roomcolumnvalues/:id : get the "id" roomcolumnvalue.
     *
     * @param id the id of the roomcolumnvalueDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomcolumnvalueDTO, or with status 404 (Not Found)
     */
    @GetMapping("/roomcolumnvalues/{id}")
    @Timed
    public ResponseEntity<RoomcolumnvalueDTO> getRoomcolumnvalue(@PathVariable Long id) {
        log.debug("REST request to get Roomcolumnvalue : {}", id);
        RoomcolumnvalueDTO roomcolumnvalueDTO = roomcolumnvalueService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(roomcolumnvalueDTO));
    }

    /**
     * DELETE  /roomcolumnvalues/:id : delete the "id" roomcolumnvalue.
     *
     * @param id the id of the roomcolumnvalueDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/roomcolumnvalues/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoomcolumnvalue(@PathVariable Long id) {
        log.debug("REST request to delete Roomcolumnvalue : {}", id);
        roomcolumnvalueService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
