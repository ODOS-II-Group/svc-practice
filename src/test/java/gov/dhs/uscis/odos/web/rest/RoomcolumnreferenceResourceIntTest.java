package gov.dhs.uscis.odos.web.rest;

import gov.dhs.uscis.odos.CrrsvcApp;

import gov.dhs.uscis.odos.domain.Roomcolumnreference;
import gov.dhs.uscis.odos.repository.RoomcolumnreferenceRepository;
import gov.dhs.uscis.odos.service.RoomcolumnreferenceService;
import gov.dhs.uscis.odos.service.dto.RoomcolumnreferenceDTO;
import gov.dhs.uscis.odos.service.mapper.RoomcolumnreferenceMapper;
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

/**
 * Test class for the RoomcolumnreferenceResource REST controller.
 *
 * @see RoomcolumnreferenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrrsvcApp.class)
public class RoomcolumnreferenceResourceIntTest {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private RoomcolumnreferenceRepository roomcolumnreferenceRepository;

    @Autowired
    private RoomcolumnreferenceMapper roomcolumnreferenceMapper;

    @Autowired
    private RoomcolumnreferenceService roomcolumnreferenceService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRoomcolumnreferenceMockMvc;

    private Roomcolumnreference roomcolumnreference;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoomcolumnreferenceResource roomcolumnreferenceResource = new RoomcolumnreferenceResource(roomcolumnreferenceService);
        this.restRoomcolumnreferenceMockMvc = MockMvcBuilders.standaloneSetup(roomcolumnreferenceResource)
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
    public static Roomcolumnreference createEntity(EntityManager em) {
        Roomcolumnreference roomcolumnreference = new Roomcolumnreference()
            .value(DEFAULT_VALUE);
        return roomcolumnreference;
    }

    @Before
    public void initTest() {
        roomcolumnreference = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoomcolumnreference() throws Exception {
        int databaseSizeBeforeCreate = roomcolumnreferenceRepository.findAll().size();

        // Create the Roomcolumnreference
        RoomcolumnreferenceDTO roomcolumnreferenceDTO = roomcolumnreferenceMapper.toDto(roomcolumnreference);
        restRoomcolumnreferenceMockMvc.perform(post("/api/roomcolumnreferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnreferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Roomcolumnreference in the database
        List<Roomcolumnreference> roomcolumnreferenceList = roomcolumnreferenceRepository.findAll();
        assertThat(roomcolumnreferenceList).hasSize(databaseSizeBeforeCreate + 1);
        Roomcolumnreference testRoomcolumnreference = roomcolumnreferenceList.get(roomcolumnreferenceList.size() - 1);
        assertThat(testRoomcolumnreference.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createRoomcolumnreferenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomcolumnreferenceRepository.findAll().size();

        // Create the Roomcolumnreference with an existing ID
        roomcolumnreference.setId(1L);
        RoomcolumnreferenceDTO roomcolumnreferenceDTO = roomcolumnreferenceMapper.toDto(roomcolumnreference);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomcolumnreferenceMockMvc.perform(post("/api/roomcolumnreferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnreferenceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Roomcolumnreference in the database
        List<Roomcolumnreference> roomcolumnreferenceList = roomcolumnreferenceRepository.findAll();
        assertThat(roomcolumnreferenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomcolumnreferenceRepository.findAll().size();
        // set the field null
        roomcolumnreference.setValue(null);

        // Create the Roomcolumnreference, which fails.
        RoomcolumnreferenceDTO roomcolumnreferenceDTO = roomcolumnreferenceMapper.toDto(roomcolumnreference);

        restRoomcolumnreferenceMockMvc.perform(post("/api/roomcolumnreferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnreferenceDTO)))
            .andExpect(status().isBadRequest());

        List<Roomcolumnreference> roomcolumnreferenceList = roomcolumnreferenceRepository.findAll();
        assertThat(roomcolumnreferenceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoomcolumnreferences() throws Exception {
        // Initialize the database
        roomcolumnreferenceRepository.saveAndFlush(roomcolumnreference);

        // Get all the roomcolumnreferenceList
        restRoomcolumnreferenceMockMvc.perform(get("/api/roomcolumnreferences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomcolumnreference.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.toString())));
    }

    @Test
    @Transactional
    public void getRoomcolumnreference() throws Exception {
        // Initialize the database
        roomcolumnreferenceRepository.saveAndFlush(roomcolumnreference);

        // Get the roomcolumnreference
        restRoomcolumnreferenceMockMvc.perform(get("/api/roomcolumnreferences/{id}", roomcolumnreference.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roomcolumnreference.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoomcolumnreference() throws Exception {
        // Get the roomcolumnreference
        restRoomcolumnreferenceMockMvc.perform(get("/api/roomcolumnreferences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoomcolumnreference() throws Exception {
        // Initialize the database
        roomcolumnreferenceRepository.saveAndFlush(roomcolumnreference);
        int databaseSizeBeforeUpdate = roomcolumnreferenceRepository.findAll().size();

        // Update the roomcolumnreference
        Roomcolumnreference updatedRoomcolumnreference = roomcolumnreferenceRepository.findOne(roomcolumnreference.getId());
        // Disconnect from session so that the updates on updatedRoomcolumnreference are not directly saved in db
        em.detach(updatedRoomcolumnreference);
        updatedRoomcolumnreference
            .value(UPDATED_VALUE);
        RoomcolumnreferenceDTO roomcolumnreferenceDTO = roomcolumnreferenceMapper.toDto(updatedRoomcolumnreference);

        restRoomcolumnreferenceMockMvc.perform(put("/api/roomcolumnreferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnreferenceDTO)))
            .andExpect(status().isOk());

        // Validate the Roomcolumnreference in the database
        List<Roomcolumnreference> roomcolumnreferenceList = roomcolumnreferenceRepository.findAll();
        assertThat(roomcolumnreferenceList).hasSize(databaseSizeBeforeUpdate);
        Roomcolumnreference testRoomcolumnreference = roomcolumnreferenceList.get(roomcolumnreferenceList.size() - 1);
        assertThat(testRoomcolumnreference.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingRoomcolumnreference() throws Exception {
        int databaseSizeBeforeUpdate = roomcolumnreferenceRepository.findAll().size();

        // Create the Roomcolumnreference
        RoomcolumnreferenceDTO roomcolumnreferenceDTO = roomcolumnreferenceMapper.toDto(roomcolumnreference);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoomcolumnreferenceMockMvc.perform(put("/api/roomcolumnreferences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnreferenceDTO)))
            .andExpect(status().isCreated());

        // Validate the Roomcolumnreference in the database
        List<Roomcolumnreference> roomcolumnreferenceList = roomcolumnreferenceRepository.findAll();
        assertThat(roomcolumnreferenceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRoomcolumnreference() throws Exception {
        // Initialize the database
        roomcolumnreferenceRepository.saveAndFlush(roomcolumnreference);
        int databaseSizeBeforeDelete = roomcolumnreferenceRepository.findAll().size();

        // Get the roomcolumnreference
        restRoomcolumnreferenceMockMvc.perform(delete("/api/roomcolumnreferences/{id}", roomcolumnreference.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Roomcolumnreference> roomcolumnreferenceList = roomcolumnreferenceRepository.findAll();
        assertThat(roomcolumnreferenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Roomcolumnreference.class);
        Roomcolumnreference roomcolumnreference1 = new Roomcolumnreference();
        roomcolumnreference1.setId(1L);
        Roomcolumnreference roomcolumnreference2 = new Roomcolumnreference();
        roomcolumnreference2.setId(roomcolumnreference1.getId());
        assertThat(roomcolumnreference1).isEqualTo(roomcolumnreference2);
        roomcolumnreference2.setId(2L);
        assertThat(roomcolumnreference1).isNotEqualTo(roomcolumnreference2);
        roomcolumnreference1.setId(null);
        assertThat(roomcolumnreference1).isNotEqualTo(roomcolumnreference2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomcolumnreferenceDTO.class);
        RoomcolumnreferenceDTO roomcolumnreferenceDTO1 = new RoomcolumnreferenceDTO();
        roomcolumnreferenceDTO1.setId(1L);
        RoomcolumnreferenceDTO roomcolumnreferenceDTO2 = new RoomcolumnreferenceDTO();
        assertThat(roomcolumnreferenceDTO1).isNotEqualTo(roomcolumnreferenceDTO2);
        roomcolumnreferenceDTO2.setId(roomcolumnreferenceDTO1.getId());
        assertThat(roomcolumnreferenceDTO1).isEqualTo(roomcolumnreferenceDTO2);
        roomcolumnreferenceDTO2.setId(2L);
        assertThat(roomcolumnreferenceDTO1).isNotEqualTo(roomcolumnreferenceDTO2);
        roomcolumnreferenceDTO1.setId(null);
        assertThat(roomcolumnreferenceDTO1).isNotEqualTo(roomcolumnreferenceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roomcolumnreferenceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roomcolumnreferenceMapper.fromId(null)).isNull();
    }
}
