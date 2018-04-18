package gov.dhs.uscis.odos.web.rest;

import gov.dhs.uscis.odos.CrrsvcApp;

import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;
import gov.dhs.uscis.odos.repository.ConferenceRoomScheduleRepository;
import gov.dhs.uscis.odos.service.ConferenceRoomScheduleService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static gov.dhs.uscis.odos.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ConferenceRoomScheduleResource REST controller.
 *
 * @see ConferenceRoomScheduleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CrrsvcApp.class)
public class ConferenceRoomScheduleResourceIntTest {

	private static final Long DEFAULT_CONFERENCE_ROOM_SCHEDULE_ID = 1L;

	private static final Long DEFAULT_CONFERENCE_ROOM_ID = 1L;
	private static final Long UPDATED_CONFERENCE_ROOM_ID = 2L;

	private static final Long DEFAULT_REQUESTOR_ID = 1L;
	private static final Long UPDATED_REQUESTOR_ID = 2L;

	private static final LocalDate DEFAULT_ROOM_SCHEDULE_START_TIME = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_ROOM_SCHEDULE_START_TIME = LocalDate.now(ZoneId.systemDefault());

	private static final LocalDate DEFAULT_ROOM_SCHEDULE_END_TIME = LocalDate.ofEpochDay(0L);
	private static final LocalDate UPDATED_ROOM_SCHEDULE_END_TIME = LocalDate.now(ZoneId.systemDefault());

	@Autowired
	private ConferenceRoomScheduleRepository conferenceRoomScheduleRepository;

	@Autowired
	private ConferenceRoomScheduleService conferenceRoomScheduleService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	@Autowired
	private EntityManager em;

	private MockMvc restConferenceRoomScheduleMockMvc;

	private ConferenceRoomSchedule conferenceRoomSchedule;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		final ConferenceRoomScheduleResource conferenceRoomScheduleResource = new ConferenceRoomScheduleResource(
				conferenceRoomScheduleService);
		this.restConferenceRoomScheduleMockMvc = MockMvcBuilders.standaloneSetup(conferenceRoomScheduleResource)
				.setCustomArgumentResolvers(pageableArgumentResolver).setControllerAdvice(exceptionTranslator)
				.setConversionService(createFormattingConversionService()).setMessageConverters(jacksonMessageConverter)
				.build();
	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it, if
	 * they test an entity which requires the current entity.
	 */
	public static ConferenceRoomSchedule createEntity(EntityManager em) {
		ConferenceRoomSchedule conferenceRoomSchedule = new ConferenceRoomSchedule();
		conferenceRoomSchedule.setConferenceRoomId(DEFAULT_CONFERENCE_ROOM_ID);
		conferenceRoomSchedule.setRequestorId(DEFAULT_REQUESTOR_ID);
		conferenceRoomSchedule.setRoomScheduleStartTime(DEFAULT_ROOM_SCHEDULE_START_TIME);
		conferenceRoomSchedule.setRoomScheduleEndTime(DEFAULT_ROOM_SCHEDULE_END_TIME);
		return conferenceRoomSchedule;
	}

	@Before
	public void initTest() {
		conferenceRoomSchedule = createEntity(em);
	}

	@Test
	@Transactional
	public void createConferenceRoomSchedule() throws Exception {
		int databaseSizeBeforeCreate = conferenceRoomScheduleRepository.findAll().size();

		// Create the ConferenceRoomSchedule
		restConferenceRoomScheduleMockMvc
				.perform(post("/api/conference-room-schedules").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(conferenceRoomSchedule)))
				.andExpect(status().isCreated());

		// Validate the ConferenceRoomSchedule in the database
		List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
		assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeCreate + 1);
		ConferenceRoomSchedule testConferenceRoomSchedule = conferenceRoomScheduleList
				.get(conferenceRoomScheduleList.size() - 1);
		assertThat(testConferenceRoomSchedule.getConferenceRoomId()).isEqualTo(DEFAULT_CONFERENCE_ROOM_ID);
		assertThat(testConferenceRoomSchedule.getRequestorId()).isEqualTo(DEFAULT_REQUESTOR_ID);
		assertThat(testConferenceRoomSchedule.getRoomScheduleStartTime()).isEqualTo(DEFAULT_ROOM_SCHEDULE_START_TIME);
		assertThat(testConferenceRoomSchedule.getRoomScheduleEndTime()).isEqualTo(DEFAULT_ROOM_SCHEDULE_END_TIME);
	}

	@Test
	@Transactional
	public void createConferenceRoomScheduleWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = conferenceRoomScheduleRepository.findAll().size();

		// Create the ConferenceRoomSchedule with an existing ID
		conferenceRoomSchedule.setId(1L);

		// An entity with an existing ID cannot be created, so this API call must fail
		restConferenceRoomScheduleMockMvc
				.perform(post("/api/conference-room-schedules").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(conferenceRoomSchedule)))
				.andExpect(status().isBadRequest());

		// Validate the ConferenceRoomSchedule in the database
		List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
		assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	@Transactional
	public void getAllConferenceRoomSchedules() throws Exception {
		// Initialize the database
		conferenceRoomScheduleRepository.saveAndFlush(conferenceRoomSchedule);

		// Get all the conferenceRoomScheduleList
		restConferenceRoomScheduleMockMvc.perform(get("/api/conference-room-schedules?sort=id,desc"))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.[*].id").value(hasItem(conferenceRoomSchedule.getId().intValue())))
				.andExpect(jsonPath("$.[*].conferenceRoomScheduleId")
						.value(hasItem(DEFAULT_CONFERENCE_ROOM_SCHEDULE_ID.intValue())))
				.andExpect(jsonPath("$.[*].conferenceRoomId").value(hasItem(DEFAULT_CONFERENCE_ROOM_ID.intValue())))
				.andExpect(jsonPath("$.[*].requestorId").value(hasItem(DEFAULT_REQUESTOR_ID.intValue())))
				.andExpect(jsonPath("$.[*].roomScheduleStartTime")
						.value(hasItem(DEFAULT_ROOM_SCHEDULE_START_TIME.toString())))
				.andExpect(jsonPath("$.[*].roomScheduleEndTime")
						.value(hasItem(DEFAULT_ROOM_SCHEDULE_END_TIME.toString())));
	}

	@Test
	@Transactional
	public void getConferenceRoomSchedule() throws Exception {
		// Initialize the database
		conferenceRoomScheduleRepository.saveAndFlush(conferenceRoomSchedule);

		// Get the conferenceRoomSchedule
		restConferenceRoomScheduleMockMvc
				.perform(get("/api/conference-room-schedules/{id}", conferenceRoomSchedule.getId()))
				.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andExpect(jsonPath("$.id").value(conferenceRoomSchedule.getId().intValue()))
				.andExpect(jsonPath("$.conferenceRoomScheduleId").value(DEFAULT_CONFERENCE_ROOM_SCHEDULE_ID.intValue()))
				.andExpect(jsonPath("$.conferenceRoomId").value(DEFAULT_CONFERENCE_ROOM_ID.intValue()))
				.andExpect(jsonPath("$.requestorId").value(DEFAULT_REQUESTOR_ID.intValue()))
				.andExpect(jsonPath("$.roomScheduleStartTime").value(DEFAULT_ROOM_SCHEDULE_START_TIME.toString()))
				.andExpect(jsonPath("$.roomScheduleEndTime").value(DEFAULT_ROOM_SCHEDULE_END_TIME.toString()));
	}

	@Test
	@Transactional
	public void getNonExistingConferenceRoomSchedule() throws Exception {
		// Get the conferenceRoomSchedule
		restConferenceRoomScheduleMockMvc.perform(get("/api/conference-room-schedules/{id}", Long.MAX_VALUE))
				.andExpect(status().isNotFound());
	}

	@Test
	@Transactional
	public void updateConferenceRoomSchedule() throws Exception {
		// Initialize the database
		conferenceRoomScheduleService.save(conferenceRoomSchedule);

		int databaseSizeBeforeUpdate = conferenceRoomScheduleRepository.findAll().size();

		// Update the conferenceRoomSchedule
		ConferenceRoomSchedule updatedConferenceRoomSchedule = conferenceRoomScheduleRepository
				.findOne(conferenceRoomSchedule.getId());
		// Disconnect from session so that the updates on updatedConferenceRoomSchedule
		// are not directly saved in db
		em.detach(updatedConferenceRoomSchedule);
		updatedConferenceRoomSchedule.conferenceRoomId(UPDATED_CONFERENCE_ROOM_ID).requestorId(UPDATED_REQUESTOR_ID)
				.roomScheduleStartTime(UPDATED_ROOM_SCHEDULE_START_TIME)
				.roomScheduleEndTime(UPDATED_ROOM_SCHEDULE_END_TIME);

		restConferenceRoomScheduleMockMvc
				.perform(put("/api/conference-room-schedules").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(updatedConferenceRoomSchedule)))
				.andExpect(status().isOk());

		// Validate the ConferenceRoomSchedule in the database
		List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
		assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeUpdate);
		ConferenceRoomSchedule testConferenceRoomSchedule = conferenceRoomScheduleList
				.get(conferenceRoomScheduleList.size() - 1);
		assertThat(testConferenceRoomSchedule.getConferenceRoomId()).isEqualTo(UPDATED_CONFERENCE_ROOM_ID);
		assertThat(testConferenceRoomSchedule.getRequestorId()).isEqualTo(UPDATED_REQUESTOR_ID);
		assertThat(testConferenceRoomSchedule.getRoomScheduleStartTime()).isEqualTo(UPDATED_ROOM_SCHEDULE_START_TIME);
		assertThat(testConferenceRoomSchedule.getRoomScheduleEndTime()).isEqualTo(UPDATED_ROOM_SCHEDULE_END_TIME);
	}

	@Test
	@Transactional
	public void updateNonExistingConferenceRoomSchedule() throws Exception {
		int databaseSizeBeforeUpdate = conferenceRoomScheduleRepository.findAll().size();

		// Create the ConferenceRoomSchedule

		// If the entity doesn't have an ID, it will be created instead of just being
		// updated
		restConferenceRoomScheduleMockMvc
				.perform(put("/api/conference-room-schedules").contentType(TestUtil.APPLICATION_JSON_UTF8)
						.content(TestUtil.convertObjectToJsonBytes(conferenceRoomSchedule)))
				.andExpect(status().isCreated());

		// Validate the ConferenceRoomSchedule in the database
		List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
		assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	@Transactional
	public void deleteConferenceRoomSchedule() throws Exception {
		// Initialize the database
		conferenceRoomScheduleService.save(conferenceRoomSchedule);

		int databaseSizeBeforeDelete = conferenceRoomScheduleRepository.findAll().size();

		// Get the conferenceRoomSchedule
		restConferenceRoomScheduleMockMvc
				.perform(delete("/api/conference-room-schedules/{id}", conferenceRoomSchedule.getId())
						.accept(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(status().isOk());

		// Validate the database is empty
		List<ConferenceRoomSchedule> conferenceRoomScheduleList = conferenceRoomScheduleRepository.findAll();
		assertThat(conferenceRoomScheduleList).hasSize(databaseSizeBeforeDelete - 1);
	}

	@Test
	@Transactional
	public void equalsVerifier() throws Exception {
		TestUtil.equalsVerifier(ConferenceRoomSchedule.class);
		ConferenceRoomSchedule conferenceRoomSchedule1 = new ConferenceRoomSchedule();
		conferenceRoomSchedule1.setId(1L);
		ConferenceRoomSchedule conferenceRoomSchedule2 = new ConferenceRoomSchedule();
		conferenceRoomSchedule2.setId(conferenceRoomSchedule1.getId());
		assertThat(conferenceRoomSchedule1).isEqualTo(conferenceRoomSchedule2);
		conferenceRoomSchedule2.setId(2L);
		assertThat(conferenceRoomSchedule1).isNotEqualTo(conferenceRoomSchedule2);
		conferenceRoomSchedule1.setId(null);
		assertThat(conferenceRoomSchedule1).isNotEqualTo(conferenceRoomSchedule2);
	}
}
