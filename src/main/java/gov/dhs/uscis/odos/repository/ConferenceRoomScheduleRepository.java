package gov.dhs.uscis.odos.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gov.dhs.uscis.odos.domain.ConferenceRoomSchedule;

/**
 * Spring Data JPA repository for the ConferenceRoomSchedule entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ConferenceRoomScheduleRepository extends JpaRepository<ConferenceRoomSchedule, Long> {
	public List<ConferenceRoomSchedule> findByRequestorId(String requestorId);

	@Query("SELECT cs FROM ConferenceRoomSchedule cs inner join cs.conferenceRoom cf where cf.conferenceRoomId = :conferenceRoomId and "
			+ " cs.roomScheduleEndTime >= :startDate AND cs.roomScheduleEndTime <= :endDate")
	public List<ConferenceRoomSchedule> findAllByConferenceRoomIdAndDate(
			@Param("conferenceRoomId") Long conferenceRoomId, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);
}
