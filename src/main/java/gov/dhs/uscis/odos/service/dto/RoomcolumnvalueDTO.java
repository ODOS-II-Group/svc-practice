package gov.dhs.uscis.odos.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Roomcolumnvalue entity.
 */
public class RoomcolumnvalueDTO implements Serializable {

    private Long id;

    @NotNull
    private String columnvalue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColumnvalue() {
        return columnvalue;
    }

    public void setColumnvalue(String columnvalue) {
        this.columnvalue = columnvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RoomcolumnvalueDTO roomcolumnvalueDTO = (RoomcolumnvalueDTO) o;
        if(roomcolumnvalueDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), roomcolumnvalueDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RoomcolumnvalueDTO{" +
            "id=" + getId() +
            ", columnvalue='" + getColumnvalue() + "'" +
            "}";
    }
}
