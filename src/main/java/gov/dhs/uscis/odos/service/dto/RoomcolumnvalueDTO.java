package gov.dhs.uscis.odos.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Roomcolumnvalue entity.
 */
public class RoomcolumnvalueDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8583132390072375297L;

	private Long id;

    @NotNull
    private String columnvalue;
    
    private String columnName;
    
    private Long confRoomId;

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

    public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Long getConfRoomId() {
		return confRoomId;
	}

	public void setConfRoomId(Long confRoomId) {
		this.confRoomId = confRoomId;
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
		return "RoomcolumnvalueDTO [id=" + id + ", columnvalue=" + columnvalue + ", columnName=" + columnName
				+ ", confRoomId=" + confRoomId + "]";
	}
}
