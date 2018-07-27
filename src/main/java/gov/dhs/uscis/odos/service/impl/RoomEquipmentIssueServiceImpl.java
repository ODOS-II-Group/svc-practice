package gov.dhs.uscis.odos.service.impl;

import gov.dhs.uscis.odos.service.RoomEquipmentIssueService;
import gov.dhs.uscis.odos.domain.ConferenceRoom;
import gov.dhs.uscis.odos.domain.RoomEquipmentIssue;
import gov.dhs.uscis.odos.repository.RoomEquipmentIssueRepository;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Service Implementation for managing RoomEquipmentIssue.
 */
@Service
@Transactional
public class RoomEquipmentIssueServiceImpl implements RoomEquipmentIssueService {

	
	private static final String DATE_FORMAT  = "yyyy-MM-dd HH:mm";
    private final Logger log = LoggerFactory.getLogger(RoomEquipmentIssueServiceImpl.class);

    private final RoomEquipmentIssueRepository roomEquipmentIssueRepository;

    public RoomEquipmentIssueServiceImpl(RoomEquipmentIssueRepository roomEquipmentIssueRepository) {
        this.roomEquipmentIssueRepository = roomEquipmentIssueRepository;
    }

    /**
     * Save a roomEquipmentIssue.
     *
     * @param roomEquipmentIssue the entity to save
     * @return the persisted entity
     */
    @Override
    @Transactional
    public RoomEquipmentIssue save(RoomEquipmentIssue roomEquipmentIssue) {
        log.debug("Request to save RoomEquipmentIssue : {}", roomEquipmentIssue);
   //    Date date =  this.convertDateString(roomEquipmentIssue.getReportDate().toString(), DATE_FORMAT);
     //  roomEquipmentIssue.setReportDate(date); 
       return roomEquipmentIssueRepository.save(roomEquipmentIssue);
    }
    private Date convertDateString(String dateStr, String format) {
		Date dateValue = null;
		try {
			dateValue = DateUtils.parseDate(dateStr, format);
		}
		catch(ParseException e) {
			log.error("Error parsing date value " + dateStr, e);
			throw new RuntimeException(e);
		}
		return dateValue;
	}

    /**
     * Get all the roomEquipmentIssues.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomEquipmentIssue> findAll() {
        log.debug("Request to get all RoomEquipmentIssues");
        return roomEquipmentIssueRepository.findAll();
    }

    /**
     * Get one roomEquipmentIssue by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RoomEquipmentIssue findOne(Long id) {
        log.debug("Request to get RoomEquipmentIssue : {}", id);
        return roomEquipmentIssueRepository.findOne(id);
    }

    /**
     * Delete the roomEquipmentIssue by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomEquipmentIssue : {}", id);
        roomEquipmentIssueRepository.delete(id);
    }
}
