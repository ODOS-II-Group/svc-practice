package gov.dhs.uscis.odos.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Roomcolumnreference entity.
 */
public class RoomcolumnreferenceDTO implements Serializable {

    private Long id;

    @NotNull
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoomcolumnreferenceDTO roomcolumnreferenceDTO = (RoomcolumnreferenceDTO) o;
        if(roomcolumnreferenceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomcolumnreferenceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomcolumnreferenceDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}
