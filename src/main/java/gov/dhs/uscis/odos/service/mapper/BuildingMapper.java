package gov.dhs.uscis.odos.service.mapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.dozer.Mapper;

import gov.dhs.uscis.odos.domain.Building;
import gov.dhs.uscis.odos.service.ConferenceRoomService;
import gov.dhs.uscis.odos.service.dto.BuildingDTO;

/**
 * Mapper for the entity Building and its DTO BuildingDTO.
 */
@Named
public class BuildingMapper implements EntityMapper<BuildingDTO, Building> {

	@Inject
	private Mapper mapper;
	
	@Inject
	private ConferenceRoomService conferenceRoomService;

	@Override
	public Building toEntity(BuildingDTO dto) {
		return mapper.map(dto, Building.class);
	}

	@Override
	public BuildingDTO toDto(Building entity) {
		if (entity == null) return null;
		BuildingDTO buildingDTO = mapper.map(entity, BuildingDTO.class);
		buildingDTO.setConferenceRooms(conferenceRoomService.findAll());
		return buildingDTO;
	}

	@Override
	public List<Building> toEntity(List<BuildingDTO> dtoList) {
		List<Building> buildingLists = new ArrayList<>();
		for (BuildingDTO buildingDTO : dtoList) {
			buildingLists.add(mapper.map(buildingDTO, Building.class));
		}
		return buildingLists;
	}

	@Override
	public List<BuildingDTO> toDto(List<Building> entityList) {
		List<BuildingDTO> buildingDTOList = new ArrayList<>();
		for (Building buildingEnty : entityList) {
			buildingDTOList.add(mapper.map(buildingEnty, BuildingDTO.class));
		}
		return buildingDTOList;
	}

}
