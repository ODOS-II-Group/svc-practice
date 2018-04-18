package gov.dhs.uscis.odos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A ConferenceRoomSchedule.
 */
@Entity
@Table(name = "conf_sched")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ConferenceRoomSchedule implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "conf_sch_id")
	private Long id;

	@Column(name = "conf_rm_id")
	private Long conferenceRoomId;

	@Column(name = "req_id")
	private Long requestorId;

	@Column(name = "start_dttm")
	private LocalDate roomScheduleStartTime;

	@Column(name = "end_dttm")
	private LocalDate roomScheduleEndTime;

	@Column(name = "room_schedule_end_time")
	private String conferenceTitle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conf_rm_id")
	private ConferenceRoom conferenceRoom;

	// jhipster-needle-entity-add-getters-setters - JHipster will add getters and
	// setters here, do not remove

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getConferenceRoomId() {
		return conferenceRoomId;
	}

	public void setConferenceRoomId(Long conferenceRoomId) {
		this.conferenceRoomId = conferenceRoomId;
	}

	public ConferenceRoomSchedule conferenceRoomId(Long conferenceRoomId) {
		this.conferenceRoomId = conferenceRoomId;
		return this;
	}

	public Long getRequestorId() {
		return requestorId;
	}

	public void setRequestorId(Long requestorId) {
		this.requestorId = requestorId;
	}

	public ConferenceRoomSchedule requestorId(Long requestorId) {
		this.requestorId = requestorId;
		return this;
	}

	public LocalDate getRoomScheduleStartTime() {
		return roomScheduleStartTime;
	}

	public ConferenceRoomSchedule roomScheduleStartTime(LocalDate roomScheduleStartTime) {
		this.roomScheduleStartTime = roomScheduleStartTime;
		return this;
	}

	public void setRoomScheduleStartTime(LocalDate roomScheduleStartTime) {
		this.roomScheduleStartTime = roomScheduleStartTime;
	}

	public LocalDate getRoomScheduleEndTime() {
		return roomScheduleEndTime;
	}

	public void setRoomScheduleEndTime(LocalDate roomScheduleEndTime) {
		this.roomScheduleEndTime = roomScheduleEndTime;
	}

	public ConferenceRoomSchedule roomScheduleEndTime(LocalDate roomScheduleEndTime) {
		this.roomScheduleEndTime = roomScheduleEndTime;
		return this;
	}

	public String getConferenceTitle() {
		return conferenceTitle;
	}

	public void setConferenceTitle(String conferenceTitle) {
		this.conferenceTitle = conferenceTitle;
	}

	public ConferenceRoom getConferenceRoom() {
		return conferenceRoom;
	}

	public void setConferenceRoom(ConferenceRoom conferenceRoom) {
		this.conferenceRoom = conferenceRoom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		ConferenceRoomSchedule conferenceRoomSchedule = (ConferenceRoomSchedule) o;
		if (conferenceRoomSchedule.getId() == null || getId() == null) {
			return false;
		}
		return Objects.equals(getId(), conferenceRoomSchedule.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getId());
	}

	@Override
	public String toString() {
		return "ConferenceRoomSchedule [id=" + id + ", conferenceRoomId=" + conferenceRoomId + ", requestorId="
				+ requestorId + ", roomScheduleStartTime=" + roomScheduleStartTime + ", roomScheduleEndTime="
				+ roomScheduleEndTime + ", conferenceTitle=" + conferenceTitle + ", conferenceRoom=" + conferenceRoom
				+ "]";
	}

}
