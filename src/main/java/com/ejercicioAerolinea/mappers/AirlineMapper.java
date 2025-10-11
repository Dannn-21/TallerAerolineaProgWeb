package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.AirlineDTO;
import com.ejercicioAerolinea.entities.Airline;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AirlineMapper {

    // CREATE
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true)
    Airline toEntity(AirlineDTO.AirlineCreateRequest dto);

    // UPDATE parcial
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "flights", ignore = true)
    void updateEntity(AirlineDTO.AirlineUpdateRequest dto, @MappingTarget Airline entity);

    //Response
    AirlineDTO.AirlineResponse toResponse(Airline entity);
}