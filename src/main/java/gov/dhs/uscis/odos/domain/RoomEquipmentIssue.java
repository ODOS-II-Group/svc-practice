package gov.dhs.uscis.odos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gov.dhs.uscis.odos.domain.enumeration.EquipmentStatus;

/**
 * A RoomEquipmentIssue.
 */
@Entity
@Table(name = "rm_equip_issue")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RoomEquipmentIssue implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_comment", nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EquipmentStatus status;
    
    @OneToMany(mappedBy = "roomEquipmentIssue", fetch = FetchType.LAZY ,cascade = CascadeType.ALL, orphanRemoval = true)
   	private List<ConferenceRoom> ConferenceRoom = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public RoomEquipmentIssue comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public EquipmentStatus getStatus() {
        return status;
    }

    public RoomEquipmentIssue status(EquipmentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(EquipmentStatus status) {
        this.status = status;
    }
    
    
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public List<ConferenceRoom> getConferenceRoom() {
		return ConferenceRoom;
	}

	public void setConferenceRoom(List<ConferenceRoom> conferenceRoom) {
		ConferenceRoom = conferenceRoom;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoomEquipmentIssue roomEquipmentIssue = (RoomEquipmentIssue) o;
        if (roomEquipmentIssue.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomEquipmentIssue.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomEquipmentIssue{" +
            "id=" + getId() +
            ", comment='" + getComment() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
