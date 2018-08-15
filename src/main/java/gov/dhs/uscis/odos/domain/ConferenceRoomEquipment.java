package gov.dhs.uscis.odos.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * A ConferenceRoomEquipment.
 */
@Entity
@Table(name = "conf_rm_equip")
public class ConferenceRoomEquipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conf_rm_equip_id")
    private Long conferenceRoomEquipId;

    @ManyToOne(targetEntity = ConferenceRoom.class)
    @JoinColumn(name = "conf_rm_id")
    private ConferenceRoom conferenceRoom;

    @OneToOne(targetEntity = Equipment.class)
    @JoinColumn(name = "equip_id")
    private Equipment equipment;
    
    @OneToMany(mappedBy = "conferenceRoomEquipment", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EquipmentReport> equipmentReport = new ArrayList<>();
    
	public Long getConferenceRoomEquipId() {
		return conferenceRoomEquipId;
	}

	public void setConferenceRoomEquipId(Long conferenceRoomEquipId) {
		this.conferenceRoomEquipId = conferenceRoomEquipId;
	}

	public ConferenceRoom getConferenceRoom() {
		return conferenceRoom;
	}

	public void setConferenceRoom(ConferenceRoom conferenceRoom) {
		this.conferenceRoom = conferenceRoom;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public List<EquipmentReport> getEquipmentReport() {
		return equipmentReport;
	}

	public void setEquipmentReport(List<EquipmentReport> equipmentReport) {
		this.equipmentReport = equipmentReport;
	}
	
}
