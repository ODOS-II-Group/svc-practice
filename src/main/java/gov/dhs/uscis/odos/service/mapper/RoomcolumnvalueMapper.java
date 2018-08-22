package gov.dhs.uscis.odos.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

import gov.dhs.uscis.odos.domain.Roomcolumnvalue;
import gov.dhs.uscis.odos.service.dto.RoomcolumnvalueDTO;

/**
 * Mapper for the entity Roomcolumnvalue and its DTO RoomcolumnvalueDTO.
 */
@Named
public class RoomcolumnvalueMapper implements EntityMapper<RoomcolumnvalueDTO, Roomcolumnvalue> {

	@Inject
	private Mapper mapper;

	@Override
	public Roomcolumnvalue toEntity(RoomcolumnvalueDTO dto) {
		return mapper.map(dto, Roomcolumnvalue.class);
	}

	@Override
	public RoomcolumnvalueDTO toDto(Roomcolumnvalue entity) {
		RoomcolumnvalueDTO roomcolumnvalueDTO = mapper.map(entity, RoomcolumnvalueDTO.class);
		return roomcolumnvalueDTO;
	}

	@Override
	public List<Roomcolumnvalue> toEntity(List<RoomcolumnvalueDTO> dtoList) {
		List<Roomcolumnvalue> roomcolumnvalues = new ArrayList<>();
		for (RoomcolumnvalueDTO roomcolumnvalueDTO : dtoList) {
			roomcolumnvalues.add(mapper.map(roomcolumnvalueDTO, Roomcolumnvalue.class));
		}
		return roomcolumnvalues;
	}

	@Override
	public List<RoomcolumnvalueDTO> toDto(List<Roomcolumnvalue> entityList) {
		List<RoomcolumnvalueDTO> roomcolumnvalueDTO = new ArrayList<>();
		for (Roomcolumnvalue roomcolumnvalue : entityList) {
			roomcolumnvalueDTO.add(mapper.map(roomcolumnvalue, RoomcolumnvalueDTO.class));
		}
		return roomcolumnvalueDTO;
	}
}
