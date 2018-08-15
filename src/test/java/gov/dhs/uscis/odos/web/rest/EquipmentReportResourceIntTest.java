package gov.dhs.uscis.odos.web.rest;

import gov.dhs.uscis.odos.CrrsvcApp;

import gov.dhs.uscis.odos.domain.EquipmentReport;
import gov.dhs.uscis.odos.repository.EquipmentReportRepository;
import gov.dhs.uscis.odos.service.EquipmentReportService;
import gov.dhs.uscis.odos.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static gov.dhs.uscis.odos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import gov.dhs.uscis.odos.domain.enumeration.EquipmentStatusEnum;
/**
 * Test class for the EquipmentReportResource REST controller.
 *
 * @see EquipmentReportResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrrsvcApp.class)
public class EquipmentReportResourceIntTest {

    private static final String DEFAULT_DISCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DISCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_RESOLUTION = "AAAAAAAAAA";
    private static final String UPDATED_RESOLUTION = "BBBBBBBBBB";

    private static final EquipmentStatusEnum DEFAULT_FLAG = EquipmentStatusEnum.ACCEPTED;
    private static final EquipmentStatusEnum UPDATED_FLAG = EquipmentStatusEnum.REPORTED;

    @Autowired
    private EquipmentReportRepository equipmentReportRepository;

    @Autowired
    private EquipmentReportService equipmentReportService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEquipmentReportMockMvc;

    private EquipmentReport equipmentReport;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EquipmentReportResource equipmentReportResource = new EquipmentReportResource(equipmentReportService);
        this.restEquipmentReportMockMvc = MockMvcBuilders.standaloneSetup(equipmentReportResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EquipmentReport createEntity(EntityManager em) {
        EquipmentReport equipmentReport = new EquipmentReport()
            .description(DEFAULT_DISCRIPTION)
            .resolution(DEFAULT_RESOLUTION)
            .flag(DEFAULT_FLAG);
        return equipmentReport;
    }

    @Before
    public void initTest() {
        equipmentReport = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipmentReport() throws Exception {
        int databaseSizeBeforeCreate = equipmentReportRepository.findAll().size();

        // Create the EquipmentReport
        restEquipmentReportMockMvc.perform(post("/api/equipment-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentReport)))
            .andExpect(status().isCreated());

        // Validate the EquipmentReport in the database
        List<EquipmentReport> equipmentReportList = equipmentReportRepository.findAll();
        assertThat(equipmentReportList).hasSize(databaseSizeBeforeCreate + 1);
        EquipmentReport testEquipmentReport = equipmentReportList.get(equipmentReportList.size() - 1);
        assertThat(testEquipmentReport.getDescription()).isEqualTo(DEFAULT_DISCRIPTION);
        assertThat(testEquipmentReport.getResolution()).isEqualTo(DEFAULT_RESOLUTION);
        assertThat(testEquipmentReport.getFlag()).isEqualTo(DEFAULT_FLAG);
    }

    @Test
    @Transactional
    public void createEquipmentReportWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipmentReportRepository.findAll().size();

        // Create the EquipmentReport with an existing ID
        equipmentReport.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipmentReportMockMvc.perform(post("/api/equipment-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentReport)))
            .andExpect(status().isBadRequest());

        // Validate the EquipmentReport in the database
        List<EquipmentReport> equipmentReportList = equipmentReportRepository.findAll();
        assertThat(equipmentReportList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkdescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentReportRepository.findAll().size();
        // set the field null
        equipmentReport.setDescription(null);

        // Create the EquipmentReport, which fails.

        restEquipmentReportMockMvc.perform(post("/api/equipment-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentReport)))
            .andExpect(status().isBadRequest());

        List<EquipmentReport> equipmentReportList = equipmentReportRepository.findAll();
        assertThat(equipmentReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkResolutionIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipmentReportRepository.findAll().size();
        // set the field null
        equipmentReport.setResolution(null);

        // Create the EquipmentReport, which fails.

        restEquipmentReportMockMvc.perform(post("/api/equipment-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentReport)))
            .andExpect(status().isBadRequest());

        List<EquipmentReport> equipmentReportList = equipmentReportRepository.findAll();
        assertThat(equipmentReportList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipmentReports() throws Exception {
        // Initialize the database
        equipmentReportRepository.saveAndFlush(equipmentReport);

        // Get all the equipmentReportList
        restEquipmentReportMockMvc.perform(get("/api/equipment-reports?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipmentReport.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DISCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].resolution").value(hasItem(DEFAULT_RESOLUTION.toString())))
            .andExpect(jsonPath("$.[*].flag").value(hasItem(DEFAULT_FLAG.toString())));
    }

    @Test
    @Transactional
    public void getEquipmentReport() throws Exception {
        // Initialize the database
        equipmentReportRepository.saveAndFlush(equipmentReport);

        // Get the equipmentReport
        restEquipmentReportMockMvc.perform(get("/api/equipment-reports/{id}", equipmentReport.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(equipmentReport.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DISCRIPTION.toString()))
            .andExpect(jsonPath("$.resolution").value(DEFAULT_RESOLUTION.toString()))
            .andExpect(jsonPath("$.flag").value(DEFAULT_FLAG.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEquipmentReport() throws Exception {
        // Get the equipmentReport
        restEquipmentReportMockMvc.perform(get("/api/equipment-reports/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipmentReport() throws Exception {
        // Initialize the database
        equipmentReportService.save(equipmentReport);

        int databaseSizeBeforeUpdate = equipmentReportRepository.findAll().size();

        // Update the equipmentReport
        EquipmentReport updatedEquipmentReport = equipmentReportRepository.findOne(equipmentReport.getId());
        // Disconnect from session so that the updates on updatedEquipmentReport are not directly saved in db
        em.detach(updatedEquipmentReport);
        updatedEquipmentReport
            .description(UPDATED_DISCRIPTION)
            .resolution(UPDATED_RESOLUTION)
            .flag(UPDATED_FLAG);

        restEquipmentReportMockMvc.perform(put("/api/equipment-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipmentReport)))
            .andExpect(status().isOk());

        // Validate the EquipmentReport in the database
        List<EquipmentReport> equipmentReportList = equipmentReportRepository.findAll();
        assertThat(equipmentReportList).hasSize(databaseSizeBeforeUpdate);
        EquipmentReport testEquipmentReport = equipmentReportList.get(equipmentReportList.size() - 1);
        assertThat(testEquipmentReport.getDescription()).isEqualTo(UPDATED_DISCRIPTION);
        assertThat(testEquipmentReport.getResolution()).isEqualTo(UPDATED_RESOLUTION);
        assertThat(testEquipmentReport.getFlag()).isEqualTo(UPDATED_FLAG);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipmentReport() throws Exception {
        int databaseSizeBeforeUpdate = equipmentReportRepository.findAll().size();

        // Create the EquipmentReport

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEquipmentReportMockMvc.perform(put("/api/equipment-reports")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(equipmentReport)))
            .andExpect(status().isCreated());

        // Validate the EquipmentReport in the database
        List<EquipmentReport> equipmentReportList = equipmentReportRepository.findAll();
        assertThat(equipmentReportList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEquipmentReport() throws Exception {
        // Initialize the database
        equipmentReportService.save(equipmentReport);

        int databaseSizeBeforeDelete = equipmentReportRepository.findAll().size();

        // Get the equipmentReport
        restEquipmentReportMockMvc.perform(delete("/api/equipment-reports/{id}", equipmentReport.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<EquipmentReport> equipmentReportList = equipmentReportRepository.findAll();
        assertThat(equipmentReportList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EquipmentReport.class);
        EquipmentReport equipmentReport1 = new EquipmentReport();
        equipmentReport1.setId(1L);
        EquipmentReport equipmentReport2 = new EquipmentReport();
        equipmentReport2.setId(equipmentReport1.getId());
        assertThat(equipmentReport1).isEqualTo(equipmentReport2);
        equipmentReport2.setId(2L);
        assertThat(equipmentReport1).isNotEqualTo(equipmentReport2);
        equipmentReport1.setId(null);
        assertThat(equipmentReport1).isNotEqualTo(equipmentReport2);
    }
}
