package gov.dhs.uscis.odos.service.mapper;

import gov.dhs.uscis.odos.domain.*;
import gov.dhs.uscis.odos.service.dto.RoomcolumnreferenceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Roomcolumnreference and its DTO RoomcolumnreferenceDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoomcolumnreferenceMapper extends EntityMapper<RoomcolumnreferenceDTO, Roomcolumnreference> {



    default Roomcolumnreference fromId(Long id) {
        if (id == null) {
            return null;
        }
        Roomcolumnreference roomcolumnreference = new Roomcolumnreference();
        roomcolumnreference.setId(id);
        return roomcolumnreference;
    }
}
