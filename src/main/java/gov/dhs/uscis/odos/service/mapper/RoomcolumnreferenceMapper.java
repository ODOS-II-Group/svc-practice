package gov.dhs.uscis.odos.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

import gov.dhs.uscis.odos.domain.Roomcolumnreference;
import gov.dhs.uscis.odos.service.dto.RoomcolumnreferenceDTO;

/**
 * Mapper for the entity Roomcolumnreference and its DTO RoomcolumnreferenceDTO.
 */
@Named
public class RoomcolumnreferenceMapper implements EntityMapper<RoomcolumnreferenceDTO, Roomcolumnreference> {

	@Inject
	private Mapper mapper;

	@Override
	public Roomcolumnreference toEntity(RoomcolumnreferenceDTO dto) {
		return mapper.map(dto, Roomcolumnreference.class);
	}

	@Override
	public RoomcolumnreferenceDTO toDto(Roomcolumnreference entity) {
		RoomcolumnreferenceDTO roomcolumnreferenceDTO = mapper.map(entity, RoomcolumnreferenceDTO.class);
		return roomcolumnreferenceDTO;
	}

	@Override
	public List<Roomcolumnreference> toEntity(List<RoomcolumnreferenceDTO> dtoList) {
		List<Roomcolumnreference> roomcolumnreferences = new ArrayList<>();
		for (RoomcolumnreferenceDTO roomcolumnreferenceDTO : dtoList) {
			roomcolumnreferences.add(mapper.map(roomcolumnreferenceDTO, Roomcolumnreference.class));
		}
		return roomcolumnreferences;
	}

	@Override
	public List<RoomcolumnreferenceDTO> toDto(List<Roomcolumnreference> entityList) {
		List<RoomcolumnreferenceDTO> roomcolumnreferenceDTO = new ArrayList<>();
		for (Roomcolumnreference roomcolumnreference : entityList) {
			roomcolumnreferenceDTO.add(mapper.map(roomcolumnreference, RoomcolumnreferenceDTO.class));
		}
		return roomcolumnreferenceDTO;
	}
	
	
}
