package gov.dhs.uscis.odos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import gov.dhs.uscis.odos.domain.ConferenceRoom;


/**
 * Spring Data JPA repository for the ConferenceRoom entity.
 */
@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {
	
	@Modifying
	@Query("update ConferenceRoom conf set conf.foodSpace = ?1 where conf.conferenceRoomId = ?2")
	void updateFoodSpace(@Param("foodSpace") String foodSpace, @Param(value = "conferenceRoomId") Long conferenceRoomId);

}
