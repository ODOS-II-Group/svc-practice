package gov.dhs.uscis.odos.web.rest;

import gov.dhs.uscis.odos.CrrsvcApp;

import gov.dhs.uscis.odos.domain.Roomcolumnvalue;
import gov.dhs.uscis.odos.repository.RoomcolumnvalueRepository;
import gov.dhs.uscis.odos.service.RoomcolumnvalueService;
import gov.dhs.uscis.odos.service.dto.RoomcolumnvalueDTO;
import gov.dhs.uscis.odos.service.mapper.RoomcolumnvalueMapper;
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
 * Test class for the RoomcolumnvalueResource REST controller.
 *
 * @see RoomcolumnvalueResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrrsvcApp.class)
public class RoomcolumnvalueResourceIntTest {

    private static final String DEFAULT_COLUMNVALUE = "AAAAAAAAAA";
    private static final String UPDATED_COLUMNVALUE = "BBBBBBBBBB";

    @Autowired
    private RoomcolumnvalueRepository roomcolumnvalueRepository;

    @Autowired
    private RoomcolumnvalueMapper roomcolumnvalueMapper;

    @Autowired
    private RoomcolumnvalueService roomcolumnvalueService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRoomcolumnvalueMockMvc;

    private Roomcolumnvalue roomcolumnvalue;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RoomcolumnvalueResource roomcolumnvalueResource = new RoomcolumnvalueResource(roomcolumnvalueService);
        this.restRoomcolumnvalueMockMvc = MockMvcBuilders.standaloneSetup(roomcolumnvalueResource)
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
    public static Roomcolumnvalue createEntity(EntityManager em) {
        Roomcolumnvalue roomcolumnvalue = new Roomcolumnvalue()
            .columnvalue(DEFAULT_COLUMNVALUE);
        return roomcolumnvalue;
    }

    @Before
    public void initTest() {
        roomcolumnvalue = createEntity(em);
    }

    @Test
    @Transactional
    public void createRoomcolumnvalue() throws Exception {
        int databaseSizeBeforeCreate = roomcolumnvalueRepository.findAll().size();

        // Create the Roomcolumnvalue
        RoomcolumnvalueDTO roomcolumnvalueDTO = roomcolumnvalueMapper.toDto(roomcolumnvalue);
        restRoomcolumnvalueMockMvc.perform(post("/api/roomcolumnvalues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnvalueDTO)))
            .andExpect(status().isCreated());

        // Validate the Roomcolumnvalue in the database
        List<Roomcolumnvalue> roomcolumnvalueList = roomcolumnvalueRepository.findAll();
        assertThat(roomcolumnvalueList).hasSize(databaseSizeBeforeCreate + 1);
        Roomcolumnvalue testRoomcolumnvalue = roomcolumnvalueList.get(roomcolumnvalueList.size() - 1);
        assertThat(testRoomcolumnvalue.getColumnvalue()).isEqualTo(DEFAULT_COLUMNVALUE);
    }

    @Test
    @Transactional
    public void createRoomcolumnvalueWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = roomcolumnvalueRepository.findAll().size();

        // Create the Roomcolumnvalue with an existing ID
        roomcolumnvalue.setId(1L);
        RoomcolumnvalueDTO roomcolumnvalueDTO = roomcolumnvalueMapper.toDto(roomcolumnvalue);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRoomcolumnvalueMockMvc.perform(post("/api/roomcolumnvalues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnvalueDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Roomcolumnvalue in the database
        List<Roomcolumnvalue> roomcolumnvalueList = roomcolumnvalueRepository.findAll();
        assertThat(roomcolumnvalueList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkColumnvalueIsRequired() throws Exception {
        int databaseSizeBeforeTest = roomcolumnvalueRepository.findAll().size();
        // set the field null
        roomcolumnvalue.setColumnvalue(null);

        // Create the Roomcolumnvalue, which fails.
        RoomcolumnvalueDTO roomcolumnvalueDTO = roomcolumnvalueMapper.toDto(roomcolumnvalue);

        restRoomcolumnvalueMockMvc.perform(post("/api/roomcolumnvalues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnvalueDTO)))
            .andExpect(status().isBadRequest());

        List<Roomcolumnvalue> roomcolumnvalueList = roomcolumnvalueRepository.findAll();
        assertThat(roomcolumnvalueList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRoomcolumnvalues() throws Exception {
        // Initialize the database
        roomcolumnvalueRepository.saveAndFlush(roomcolumnvalue);

        // Get all the roomcolumnvalueList
        restRoomcolumnvalueMockMvc.perform(get("/api/roomcolumnvalues?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(roomcolumnvalue.getId().intValue())))
            .andExpect(jsonPath("$.[*].columnvalue").value(hasItem(DEFAULT_COLUMNVALUE.toString())));
    }

    @Test
    @Transactional
    public void getRoomcolumnvalue() throws Exception {
        // Initialize the database
        roomcolumnvalueRepository.saveAndFlush(roomcolumnvalue);

        // Get the roomcolumnvalue
        restRoomcolumnvalueMockMvc.perform(get("/api/roomcolumnvalues/{id}", roomcolumnvalue.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(roomcolumnvalue.getId().intValue()))
            .andExpect(jsonPath("$.columnvalue").value(DEFAULT_COLUMNVALUE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRoomcolumnvalue() throws Exception {
        // Get the roomcolumnvalue
        restRoomcolumnvalueMockMvc.perform(get("/api/roomcolumnvalues/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRoomcolumnvalue() throws Exception {
        // Initialize the database
        roomcolumnvalueRepository.saveAndFlush(roomcolumnvalue);
        int databaseSizeBeforeUpdate = roomcolumnvalueRepository.findAll().size();

        // Update the roomcolumnvalue
        Roomcolumnvalue updatedRoomcolumnvalue = roomcolumnvalueRepository.findOne(roomcolumnvalue.getId());
        // Disconnect from session so that the updates on updatedRoomcolumnvalue are not directly saved in db
        em.detach(updatedRoomcolumnvalue);
        updatedRoomcolumnvalue
            .columnvalue(UPDATED_COLUMNVALUE);
        RoomcolumnvalueDTO roomcolumnvalueDTO = roomcolumnvalueMapper.toDto(updatedRoomcolumnvalue);

        restRoomcolumnvalueMockMvc.perform(put("/api/roomcolumnvalues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnvalueDTO)))
            .andExpect(status().isOk());

        // Validate the Roomcolumnvalue in the database
        List<Roomcolumnvalue> roomcolumnvalueList = roomcolumnvalueRepository.findAll();
        assertThat(roomcolumnvalueList).hasSize(databaseSizeBeforeUpdate);
        Roomcolumnvalue testRoomcolumnvalue = roomcolumnvalueList.get(roomcolumnvalueList.size() - 1);
        assertThat(testRoomcolumnvalue.getColumnvalue()).isEqualTo(UPDATED_COLUMNVALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingRoomcolumnvalue() throws Exception {
        int databaseSizeBeforeUpdate = roomcolumnvalueRepository.findAll().size();

        // Create the Roomcolumnvalue
        RoomcolumnvalueDTO roomcolumnvalueDTO = roomcolumnvalueMapper.toDto(roomcolumnvalue);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRoomcolumnvalueMockMvc.perform(put("/api/roomcolumnvalues")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(roomcolumnvalueDTO)))
            .andExpect(status().isCreated());

        // Validate the Roomcolumnvalue in the database
        List<Roomcolumnvalue> roomcolumnvalueList = roomcolumnvalueRepository.findAll();
        assertThat(roomcolumnvalueList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRoomcolumnvalue() throws Exception {
        // Initialize the database
        roomcolumnvalueRepository.saveAndFlush(roomcolumnvalue);
        int databaseSizeBeforeDelete = roomcolumnvalueRepository.findAll().size();

        // Get the roomcolumnvalue
        restRoomcolumnvalueMockMvc.perform(delete("/api/roomcolumnvalues/{id}", roomcolumnvalue.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Roomcolumnvalue> roomcolumnvalueList = roomcolumnvalueRepository.findAll();
        assertThat(roomcolumnvalueList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Roomcolumnvalue.class);
        Roomcolumnvalue roomcolumnvalue1 = new Roomcolumnvalue();
        roomcolumnvalue1.setId(1L);
        Roomcolumnvalue roomcolumnvalue2 = new Roomcolumnvalue();
        roomcolumnvalue2.setId(roomcolumnvalue1.getId());
        assertThat(roomcolumnvalue1).isEqualTo(roomcolumnvalue2);
        roomcolumnvalue2.setId(2L);
        assertThat(roomcolumnvalue1).isNotEqualTo(roomcolumnvalue2);
        roomcolumnvalue1.setId(null);
        assertThat(roomcolumnvalue1).isNotEqualTo(roomcolumnvalue2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RoomcolumnvalueDTO.class);
        RoomcolumnvalueDTO roomcolumnvalueDTO1 = new RoomcolumnvalueDTO();
        roomcolumnvalueDTO1.setId(1L);
        RoomcolumnvalueDTO roomcolumnvalueDTO2 = new RoomcolumnvalueDTO();
        assertThat(roomcolumnvalueDTO1).isNotEqualTo(roomcolumnvalueDTO2);
        roomcolumnvalueDTO2.setId(roomcolumnvalueDTO1.getId());
        assertThat(roomcolumnvalueDTO1).isEqualTo(roomcolumnvalueDTO2);
        roomcolumnvalueDTO2.setId(2L);
        assertThat(roomcolumnvalueDTO1).isNotEqualTo(roomcolumnvalueDTO2);
        roomcolumnvalueDTO1.setId(null);
        assertThat(roomcolumnvalueDTO1).isNotEqualTo(roomcolumnvalueDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(roomcolumnvalueMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(roomcolumnvalueMapper.fromId(null)).isNull();
    }
}
