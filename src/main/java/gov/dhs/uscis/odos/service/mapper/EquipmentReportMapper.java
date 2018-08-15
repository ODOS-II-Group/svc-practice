package gov.dhs.uscis.odos.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

import gov.dhs.uscis.odos.domain.EquipmentReport;
import gov.dhs.uscis.odos.service.dto.EquipmentReportDTO;

/**
 * Mapper for the entity ConferenceRoom and its DTO ConferenceRoomDTO.
 */
@Named
public class EquipmentReportMapper implements EntityMapper<EquipmentReportDTO, EquipmentReport> {

	@Inject
	private Mapper mapper;

	@Override
	public EquipmentReport toEntity(EquipmentReportDTO dto) {	
		return mapper.map(dto, EquipmentReport.class);
	}

	@Override
	public EquipmentReportDTO toDto(EquipmentReport entity) {		
		return mapper.map(entity, EquipmentReportDTO.class);
	}

	@Override
	public List<EquipmentReport> toEntity(List<EquipmentReportDTO> dtoList) {
		List<EquipmentReport> equipmentReportList = new ArrayList<>();
		for (EquipmentReportDTO equipmentReportDTO : dtoList) {
			equipmentReportList.add(mapper.map(equipmentReportDTO, EquipmentReport.class));
		}
		return equipmentReportList;
	}

	@Override
	public List<EquipmentReportDTO> toDto(List<EquipmentReport> entityList) {
		List<EquipmentReportDTO> equipmentReportDTOList = new ArrayList<>();
		for (EquipmentReport equipmentReport : entityList) {
			equipmentReportDTOList.add(mapper.map(equipmentReport, EquipmentReportDTO.class));
		}
		return equipmentReportDTOList;
	}

}
