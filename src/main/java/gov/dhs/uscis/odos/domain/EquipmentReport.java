package gov.dhs.uscis.odos.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import gov.dhs.uscis.odos.domain.enumeration.EquipmentStatusEnum;

/**
 * A EquipmentReport.
 */
@Entity
@Table(name = "equipment_report")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class EquipmentReport implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "resolution", nullable = false)
    private String resolution;

    @Enumerated(EnumType.STRING)
    @Column(name = "flag")
    private EquipmentStatusEnum flag;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public EquipmentReport description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResolution() {
        return resolution;
    }

    public EquipmentReport resolution(String resolution) {
        this.resolution = resolution;
        return this;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public EquipmentStatusEnum getFlag() {
        return flag;
    }

    public EquipmentReport flag(EquipmentStatusEnum flag) {
        this.flag = flag;
        return this;
    }

    public void setFlag(EquipmentStatusEnum flag) {
        this.flag = flag;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EquipmentReport equipmentReport = (EquipmentReport) o;
        if (equipmentReport.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), equipmentReport.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EquipmentReport{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", resolution='" + getResolution() + "'" +
            ", flag='" + getFlag() + "'" +
            "}";
    }
}
