package gov.dhs.uscis.odos.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import gov.dhs.uscis.odos.domain.enums.ActiveIndicatorEnum;

/**
 * A Building.
 */
@Entity
@Table(name = "conf_rm")
public class ConferenceRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conf_rm_id")
    private Long conferenceRoomId;

    @Column(name = "rm_num")
    private String roomNum;

    @Column(name = "rm_name")
    private String roomName;
    
    @Column(name = "rm_capcity")
    private Integer roomCapacity;
    
    @Column(name = "rm_active_ind")
    @Enumerated(EnumType.STRING)
    private ActiveIndicatorEnum activeIndicator;
    
    @ManyToOne(targetEntity = Building.class)
    @JoinColumn(name = "bldg_id")
    private Building building;
    
    public Long getConferenceRoomId() {
		return conferenceRoomId;
	}

	public void setConferenceRoomId(Long conferenceRoomId) {
		this.conferenceRoomId = conferenceRoomId;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Integer getRoomCapacity() {
		return roomCapacity;
	}

	public void setRoomCapacity(Integer roomCapacity) {
		this.roomCapacity = roomCapacity;
	}

	public ActiveIndicatorEnum getActiveIndicator() {
		return activeIndicator;
	}

	public void setActiveIndicator(ActiveIndicatorEnum activeIndicator) {
		this.activeIndicator = activeIndicator;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ConferenceRoom conferenceRoom = (ConferenceRoom) o;
        if (conferenceRoom.getConferenceRoomId() == null || getConferenceRoomId() == null) {
            return false;
        }
        return Objects.equals(getConferenceRoomId(), conferenceRoom.getConferenceRoomId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getConferenceRoomId());
    }
}
