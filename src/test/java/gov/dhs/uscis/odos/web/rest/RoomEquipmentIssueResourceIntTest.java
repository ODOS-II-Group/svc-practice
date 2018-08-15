package gov.dhs.uscis.odos.web.rest;

import static gov.dhs.uscis.odos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import gov.dhs.uscis.odos.base.test.BaseIntegrationTest;
import gov.dhs.uscis.odos.domain.RoomEquipmentIssue;
import gov.dhs.uscis.odos.domain.enumeration.EquipmentStatusEnum;
import gov.dhs.uscis.odos.repository.RoomEquipmentIssueRepository;
import gov.dhs.uscis.odos.service.RoomEquipmentIssueService;
import gov.dhs.uscis.odos.web.rest.errors.ExceptionTranslator;
/**
 * Test class for the RoomEquipmentIssueResource REST controller.
 *
 * @see RoomEquipmentIssueResource
 */
public class RoomEquipmentIssueResourceIntTest extends BaseIntegrationTest {

    private static final String DEFAULT_COMMENT = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT = "BBBBBBBBBB";

    private static final EquipmentStatusEnum DEFAULT_STATUS = EquipmentStatusEnum.REPORTED;
    private static final EquipmentStatusEnum UPDATED_STATUS = EquipmentStatusEnum.ACCEPTED;

    @Autowired
    private RoomEquipmentIssueRepository roomEquipmentIssueRepository;

    @Autowired
    private RoomEquipmentIssueService roomEquipmentIssueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRoomEquipmentIssueMockMvc;

    private RoomEquipmentIssue roomEquipmentIssue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoomEquipmentIssueResource roomEquipmentIssueResource = new RoomEquipmentIssueResource(roomEquipmentIssueService);
        this.restRoomEquipmentIssueMockMvc = MockMvcBuilders.standaloneSetup(roomEquipmentIssueResource)
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
    public static RoomEquipmentIssue createEntity(EntityManager em) {
        RoomEquipmentIssue roomEquipmentIssue = new RoomEquipmentIssue()
            .comment(DEFAULT_COMMENT)
            .status(DEFAULT_STATUS);
        return roomEquipmentIssue;
    }

    @Before
    public void initTest() {
        roomEquipmentIssue = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoomEquipmentIssue() throws Exception {
        int databaseSizeBeforeCreate = roomEquipmentIssueRepository.findAll().size();

        // Create the RoomEquipmentIssue
        restRoomEquipmentIssueMockMvc.perform(post("/api/room-equipment-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomEquipmentIssue)))
            .andExpect(status().isCreated());

        // Validate the RoomEquipmentIssue in the database
        List<RoomEquipmentIssue> roomEquipmentIssueList = roomEquipmentIssueRepository.findAll();
        assertThat(roomEquipmentIssueList).hasSize(databaseSizeBeforeCreate + 1);
        RoomEquipmentIssue testRoomEquipmentIssue = roomEquipmentIssueList.get(roomEquipmentIssueList.size() - 1);
        assertThat(testRoomEquipmentIssue.getComment()).isEqualTo(DEFAULT_COMMENT);
        assertThat(testRoomEquipmentIssue.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createRoomEquipmentIssueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomEquipmentIssueRepository.findAll().size();

        // Create the RoomEquipmentIssue with an existing ID
        roomEquipmentIssue.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomEquipmentIssueMockMvc.perform(post("/api/room-equipment-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomEquipmentIssue)))
            .andExpect(status().isBadRequest());

        // Validate the RoomEquipmentIssue in the database
        List<RoomEquipmentIssue> roomEquipmentIssueList = roomEquipmentIssueRepository.findAll();
        assertThat(roomEquipmentIssueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCommentIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomEquipmentIssueRepository.findAll().size();
        // set the field null
        roomEquipmentIssue.setComment(null);

        // Create the RoomEquipmentIssue, which fails.

        restRoomEquipmentIssueMockMvc.perform(post("/api/room-equipment-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomEquipmentIssue)))
            .andExpect(status().isBadRequest());

        List<RoomEquipmentIssue> roomEquipmentIssueList = roomEquipmentIssueRepository.findAll();
        assertThat(roomEquipmentIssueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoomEquipmentIssues() throws Exception {
        // Initialize the database
        roomEquipmentIssueRepository.saveAndFlush(roomEquipmentIssue);

        // Get all the roomEquipmentIssueList
        restRoomEquipmentIssueMockMvc.perform(get("/api/room-equipment-issues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomEquipmentIssue.getId().intValue())))
            .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getRoomEquipmentIssue() throws Exception {
        // Initialize the database
        roomEquipmentIssueRepository.saveAndFlush(roomEquipmentIssue);

        // Get the roomEquipmentIssue
        restRoomEquipmentIssueMockMvc.perform(get("/api/room-equipment-issues/{id}", roomEquipmentIssue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roomEquipmentIssue.getId().intValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoomEquipmentIssue() throws Exception {
        // Get the roomEquipmentIssue
        restRoomEquipmentIssueMockMvc.perform(get("/api/room-equipment-issues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoomEquipmentIssue() throws Exception {
        // Initialize the database
        roomEquipmentIssueService.save(roomEquipmentIssue);

        int databaseSizeBeforeUpdate = roomEquipmentIssueRepository.findAll().size();

        // Update the roomEquipmentIssue
        RoomEquipmentIssue updatedRoomEquipmentIssue = roomEquipmentIssueRepository.findOne(roomEquipmentIssue.getId());
        // Disconnect from session so that the updates on updatedRoomEquipmentIssue are not directly saved in db
        em.detach(updatedRoomEquipmentIssue);
        updatedRoomEquipmentIssue
            .comment(UPDATED_COMMENT)
            .status(UPDATED_STATUS);

        restRoomEquipmentIssueMockMvc.perform(put("/api/room-equipment-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRoomEquipmentIssue)))
            .andExpect(status().isOk());

        // Validate the RoomEquipmentIssue in the database
        List<RoomEquipmentIssue> roomEquipmentIssueList = roomEquipmentIssueRepository.findAll();
        assertThat(roomEquipmentIssueList).hasSize(databaseSizeBeforeUpdate);
        RoomEquipmentIssue testRoomEquipmentIssue = roomEquipmentIssueList.get(roomEquipmentIssueList.size() - 1);
        assertThat(testRoomEquipmentIssue.getComment()).isEqualTo(UPDATED_COMMENT);
        assertThat(testRoomEquipmentIssue.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingRoomEquipmentIssue() throws Exception {
        int databaseSizeBeforeUpdate = roomEquipmentIssueRepository.findAll().size();

        // Create the RoomEquipmentIssue

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoomEquipmentIssueMockMvc.perform(put("/api/room-equipment-issues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomEquipmentIssue)))
            .andExpect(status().isCreated());

        // Validate the RoomEquipmentIssue in the database
        List<RoomEquipmentIssue> roomEquipmentIssueList = roomEquipmentIssueRepository.findAll();
        assertThat(roomEquipmentIssueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRoomEquipmentIssue() throws Exception {
        // Initialize the database
        roomEquipmentIssueService.save(roomEquipmentIssue);

        int databaseSizeBeforeDelete = roomEquipmentIssueRepository.findAll().size();

        // Get the roomEquipmentIssue
        restRoomEquipmentIssueMockMvc.perform(delete("/api/room-equipment-issues/{id}", roomEquipmentIssue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RoomEquipmentIssue> roomEquipmentIssueList = roomEquipmentIssueRepository.findAll();
        assertThat(roomEquipmentIssueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomEquipmentIssue.class);
        RoomEquipmentIssue roomEquipmentIssue1 = new RoomEquipmentIssue();
        roomEquipmentIssue1.setId(1L);
        RoomEquipmentIssue roomEquipmentIssue2 = new RoomEquipmentIssue();
        roomEquipmentIssue2.setId(roomEquipmentIssue1.getId());
        assertThat(roomEquipmentIssue1).isEqualTo(roomEquipmentIssue2);
        roomEquipmentIssue2.setId(2L);
        assertThat(roomEquipmentIssue1).isNotEqualTo(roomEquipmentIssue2);
        roomEquipmentIssue1.setId(null);
        assertThat(roomEquipmentIssue1).isNotEqualTo(roomEquipmentIssue2);
    }
}
