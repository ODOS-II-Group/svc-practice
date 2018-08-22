package gov.dhs.uscis.odos.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.dhs.uscis.odos.service.RoomcolumnreferenceService;
import gov.dhs.uscis.odos.web.rest.errors.BadRequestAlertException;
import gov.dhs.uscis.odos.web.rest.util.HeaderUtil;
import gov.dhs.uscis.odos.service.dto.RoomcolumnreferenceDTO;
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
 * REST controller for managing Roomcolumnreference.
 */
@RestController
@RequestMapping("/api")
public class RoomcolumnreferenceResource {

    private final Logger log = LoggerFactory.getLogger(RoomcolumnreferenceResource.class);

    private static final String ENTITY_NAME = "roomcolumnreference";

    private final RoomcolumnreferenceService roomcolumnreferenceService;

    public RoomcolumnreferenceResource(RoomcolumnreferenceService roomcolumnreferenceService) {
        this.roomcolumnreferenceService = roomcolumnreferenceService;
    }

    /**
     * POST  /roomcolumnreferences : Create a new roomcolumnreference.
     *
     * @param roomcolumnreferenceDTO the roomcolumnreferenceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomcolumnreferenceDTO, or with status 400 (Bad Request) if the roomcolumnreference has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/roomcolumnreferences")
    @Timed
    public ResponseEntity<RoomcolumnreferenceDTO> createRoomcolumnreference(@Valid @RequestBody RoomcolumnreferenceDTO roomcolumnreferenceDTO) throws URISyntaxException {
        log.debug("REST request to save Roomcolumnreference : {}", roomcolumnreferenceDTO);
        if (roomcolumnreferenceDTO.getId() != null) {
            throw new BadRequestAlertException("A new roomcolumnreference cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomcolumnreferenceDTO result = roomcolumnreferenceService.save(roomcolumnreferenceDTO);
        return ResponseEntity.created(new URI("/api/roomcolumnreferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /roomcolumnreferences : Updates an existing roomcolumnreference.
     *
     * @param roomcolumnreferenceDTO the roomcolumnreferenceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomcolumnreferenceDTO,
     * or with status 400 (Bad Request) if the roomcolumnreferenceDTO is not valid,
     * or with status 500 (Internal Server Error) if the roomcolumnreferenceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/roomcolumnreferences")
    @Timed
    public ResponseEntity<RoomcolumnreferenceDTO> updateRoomcolumnreference(@Valid @RequestBody RoomcolumnreferenceDTO roomcolumnreferenceDTO) throws URISyntaxException {
        log.debug("REST request to update Roomcolumnreference : {}", roomcolumnreferenceDTO);
        if (roomcolumnreferenceDTO.getId() == null) {
            return createRoomcolumnreference(roomcolumnreferenceDTO);
        }
        RoomcolumnreferenceDTO result = roomcolumnreferenceService.save(roomcolumnreferenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomcolumnreferenceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /roomcolumnreferences : get all the roomcolumnreferences.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of roomcolumnreferences in body
     */
    @GetMapping("/roomcolumnreferences")
    @Timed
    public List<RoomcolumnreferenceDTO> getAllRoomcolumnreferences() {
        log.debug("REST request to get all Roomcolumnreferences");
        return roomcolumnreferenceService.findAll();
        }

    /**
     * GET  /roomcolumnreferences/:id : get the "id" roomcolumnreference.
     *
     * @param id the id of the roomcolumnreferenceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomcolumnreferenceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/roomcolumnreferences/{id}")
    @Timed
    public ResponseEntity<RoomcolumnreferenceDTO> getRoomcolumnreference(@PathVariable Long id) {
        log.debug("REST request to get Roomcolumnreference : {}", id);
        RoomcolumnreferenceDTO roomcolumnreferenceDTO = roomcolumnreferenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(roomcolumnreferenceDTO));
    }

    /**
     * DELETE  /roomcolumnreferences/:id : delete the "id" roomcolumnreference.
     *
     * @param id the id of the roomcolumnreferenceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/roomcolumnreferences/{id}")
    @Timed
    public ResponseEntity<Void> deleteRoomcolumnreference(@PathVariable Long id) {
        log.debug("REST request to delete Roomcolumnreference : {}", id);
        roomcolumnreferenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
