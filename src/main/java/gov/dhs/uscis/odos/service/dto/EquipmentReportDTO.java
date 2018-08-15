package gov.dhs.uscis.odos.service.dto;


import java.io.Serializable;

/**
 * A DTO for the Equipment_Report entity.
 */
public class EquipmentReportDTO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 6613433690413368185L;

	private Long id;

    private String description;

    private String resolution;
    
    private String flag;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Override
	public String toString() {
		return "EquipmentReportDTO [id=" + id + ", description=" + description + ", resolution=" + resolution
				+ ", flag=" + flag + "]";
	}
    
    
}
