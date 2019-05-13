package com.epam.test.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.epam.test.dto.ProgramEventDto;
import com.epam.test.entity.ProgramEvent;

@Mapper(componentModel = "spring")
public interface ProgramEventConverter extends Converter<ProgramEvent, ProgramEventDto> {

    @Mappings({
            @Mapping(target = "programEventId", source = "programEventDto", qualifiedByName = "toId"),
    })
    @Override
    ProgramEvent toEntity(ProgramEventDto programEventDto);

    @Mappings({
            @Mapping(target = "personId", source = "entity.programEventId.userId"),
            @Mapping(target = "programId", source = "entity.programEventId.programId"),
//            @Mapping(target = "programEvent", source = "entity.programEvent"),
    })
    @Override
    ProgramEventDto toDto(ProgramEvent entity);

    @Mappings({
            @Mapping(target = "userId", source = "programEventDto.personId"),
    })
    ProgramEvent.ProgramEventId toId(ProgramEventDto programEventDto);
}
