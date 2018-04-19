package gov.dhs.uscis.odos.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.service.ConferenceRoomScheduleService;
import gov.dhs.uscis.odos.web.rest.errors.BadRequestAlertException;
import gov.dhs.uscis.odos.web.rest.util.HeaderUtil;
import gov.dhs.uscis.odos.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ConferenceRoomSchedule.
 */
@RestController
@RequestMapping("/api")
public class ConferenceRoomScheduleResource {

    private final Logger log = LoggerFactory.getLogger(ConferenceRoomScheduleResource.class);

    private static final String ENTITY_NAME = "conferenceRoomSchedule";

    private final ConferenceRoomScheduleService conferenceRoomScheduleService;

    public ConferenceRoomScheduleResource(ConferenceRoomScheduleService conferenceRoomScheduleService) {
        this.conferenceRoomScheduleService = conferenceRoomScheduleService;
    }

    /**
     * POST  /conference-room-schedule : Create a new conferenceRoomSchedule.
     *
     * @param conferenceRoomSchedule the conferenceRoomSchedule to create
     * @return the ResponseEntity with status 201 (Created) and with body the new conferenceRoomSchedule, or with status 400 (Bad Request) if the conferenceRoomSchedule has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/conference-room-schedule")
    @Timed
    public ResponseEntity<ConferenceRoomSchedule> createConferenceRoomSchedule(@RequestBody ConferenceRoomSchedule conferenceRoomSchedule) throws URISyntaxException {
        log.debug("REST request to save ConferenceRoomSchedule : {}", conferenceRoomSchedule);
        if (conferenceRoomSchedule.getId() != null) {
            throw new BadRequestAlertException("A new conferenceRoomSchedule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConferenceRoomSchedule result = conferenceRoomScheduleService.save(conferenceRoomSchedule);
        return ResponseEntity.created(new URI("/api/conference-room-schedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /conference-room-schedule : Updates an existing conferenceRoomSchedule.
     *
     * @param conferenceRoomSchedule the conferenceRoomSchedule to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated conferenceRoomSchedule,
     * or with status 400 (Bad Request) if the conferenceRoomSchedule is not valid,
     * or with status 500 (Internal Server Error) if the conferenceRoomSchedule couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/conference-room-schedule")
    @Timed
    public ResponseEntity<ConferenceRoomSchedule> updateConferenceRoomSchedule(@RequestBody ConferenceRoomSchedule conferenceRoomSchedule) throws URISyntaxException {
        log.debug("REST request to update ConferenceRoomSchedule : {}", conferenceRoomSchedule);
        if (conferenceRoomSchedule.getId() == null) {
            return createConferenceRoomSchedule(conferenceRoomSchedule);
        }
        ConferenceRoomSchedule result = conferenceRoomScheduleService.save(conferenceRoomSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, conferenceRoomSchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /conference-room-schedule : get all the conferenceRoomSchedules.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of conferenceRoomSchedules in body
     */
    @GetMapping("/conference-room-schedule")
    @Timed
    public ResponseEntity<List<ConferenceRoomSchedule>> getAllConferenceRoomSchedules(Pageable pageable) {
        log.debug("REST request to get a page of ConferenceRoomSchedules");
        Page<ConferenceRoomSchedule> page = conferenceRoomScheduleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/conference-room-schedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /conference-room-schedule/:id : get the "id" conferenceRoomSchedule.
     *
     * @param id the id of the conferenceRoomSchedule to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the conferenceRoomSchedule, or with status 404 (Not Found)
     */
    @GetMapping("/conference-room-schedule/{id}")
    @Timed
    public ResponseEntity<ConferenceRoomSchedule> getConferenceRoomSchedule(@PathVariable Long id) {
        log.debug("REST request to get ConferenceRoomSchedule : {}", id);
        ConferenceRoomSchedule conferenceRoomSchedule = conferenceRoomScheduleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(conferenceRoomSchedule));
    }

    /**
     * DELETE  /conference-room-schedule/:id : delete the "id" conferenceRoomSchedule.
     *
     * @param id the id of the conferenceRoomSchedule to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/conference-room-schedule/{id}")
    @Timed
    public ResponseEntity<Void> deleteConferenceRoomSchedule(@PathVariable Long id) {
        log.debug("REST request to delete ConferenceRoomSchedule : {}", id);
        conferenceRoomScheduleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
