package gov.dhs.uscis.odos.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.dhs.uscis.odos.domain.EquipmentReport;
import gov.dhs.uscis.odos.service.EquipmentReportService;
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
 * REST controller for managing EquipmentReport.
 */
@RestController
@RequestMapping("/api")
public class EquipmentReportResource {

    private final Logger log = LoggerFactory.getLogger(EquipmentReportResource.class);

    private static final String ENTITY_NAME = "equipmentReport";

    private final EquipmentReportService equipmentReportService;

    public EquipmentReportResource(EquipmentReportService equipmentReportService) {
        this.equipmentReportService = equipmentReportService;
    }

    /**
     * POST  /equipment-reports : Create a new equipmentReport.
     *
     * @param equipmentReport the equipmentReport to create
     * @return the ResponseEntity with status 201 (Created) and with body the new equipmentReport, or with status 400 (Bad Request) if the equipmentReport has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/equipment-reports")
    @Timed
    public ResponseEntity<EquipmentReport> createEquipmentReport(@Valid @RequestBody EquipmentReport equipmentReport) throws URISyntaxException {
        log.debug("REST request to save EquipmentReport : {}", equipmentReport);
        if (equipmentReport.getId() != null) {
            throw new BadRequestAlertException("A new equipmentReport cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EquipmentReport result = equipmentReportService.save(equipmentReport);
        return ResponseEntity.created(new URI("/api/equipment-reports/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /equipment-reports : Updates an existing equipmentReport.
     *
     * @param equipmentReport the equipmentReport to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated equipmentReport,
     * or with status 400 (Bad Request) if the equipmentReport is not valid,
     * or with status 500 (Internal Server Error) if the equipmentReport couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/equipment-reports")
    @Timed
    public ResponseEntity<EquipmentReport> updateEquipmentReport(@Valid @RequestBody EquipmentReport equipmentReport) throws URISyntaxException {
        log.debug("REST request to update EquipmentReport : {}", equipmentReport);
        if (equipmentReport.getId() == null) {
            return createEquipmentReport(equipmentReport);
        }
        EquipmentReport result = equipmentReportService.save(equipmentReport);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, equipmentReport.getId().toString()))
            .body(result);
    }

    /**
     * GET  /equipment-reports : get all the equipmentReports.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of equipmentReports in body
     */
    @GetMapping("/equipment-reports")
    @Timed
    public List<EquipmentReport> getAllEquipmentReports() {
        log.debug("REST request to get all EquipmentReports");
        return equipmentReportService.findAll();
        }

    /**
     * GET  /equipment-reports/:id : get the "id" equipmentReport.
     *
     * @param id the id of the equipmentReport to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the equipmentReport, or with status 404 (Not Found)
     */
    @GetMapping("/equipment-reports/{id}")
    @Timed
    public ResponseEntity<EquipmentReport> getEquipmentReport(@PathVariable Long id) {
        log.debug("REST request to get EquipmentReport : {}", id);
        EquipmentReport equipmentReport = equipmentReportService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(equipmentReport));
    }

    /**
     * DELETE  /equipment-reports/:id : delete the "id" equipmentReport.
     *
     * @param id the id of the equipmentReport to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/equipment-reports/{id}")
    @Timed
    public ResponseEntity<Void> deleteEquipmentReport(@PathVariable Long id) {
        log.debug("REST request to delete EquipmentReport : {}", id);
        equipmentReportService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
