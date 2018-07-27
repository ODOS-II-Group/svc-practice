package gov.dhs.uscis.odos.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
    @Column(name = "comment", nullable = false)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EquipmentStatus status;
    
    @Column(name = "conf_rm_id")
    private Long conferenceRoomId;
    
    @Column(name = "requestor_name")
    private String requestorName;
    
    @Column(name = "report_date")
    private Date reportDate;
    
    

    public RoomEquipmentIssue(String comment, EquipmentStatus status, Long conferenceRoomId) {
		super();
		this.comment = comment;
		this.status = status;
		this.conferenceRoomId = conferenceRoomId;
	}
    public RoomEquipmentIssue(){}

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


	public String getRequestorName() {
		return requestorName;
	}
	public void setRequestorName(String requestorName) {
		this.requestorName = requestorName;
	}
	public Date getReportDate() {
		return reportDate;
	}
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
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

 
	public Long getConferenceRoomId() {
		return conferenceRoomId;
	}
	public void setConferenceRoomId(Long conferenceRoomId) {
		this.conferenceRoomId = conferenceRoomId;
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
