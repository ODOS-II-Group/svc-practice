package gov.dhs.uscis.odos.service.mapper;

import gov.dhs.uscis.odos.domain.*;
import gov.dhs.uscis.odos.service.dto.RoomcolumnvalueDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Roomcolumnvalue and its DTO RoomcolumnvalueDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RoomcolumnvalueMapper extends EntityMapper<RoomcolumnvalueDTO, Roomcolumnvalue> {



    default Roomcolumnvalue fromId(Long id) {
        if (id == null) {
            return null;
        }
        Roomcolumnvalue roomcolumnvalue = new Roomcolumnvalue();
        roomcolumnvalue.setId(id);
        return roomcolumnvalue;
    }
}
