package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.AirportDTO;
import com.ejercicioAerolinea.entities.Airport;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AirportMapper {

    // CREATE
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "originFlights", ignore = true)
    @Mapping(target = "destinationFlights", ignore = true)
    Airport toEntity(AirportDTO.AirportCreateRequest dto);

    // UPDATE parcial
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "originFlights", ignore = true)
    @Mapping(target = "destinationFlights", ignore = true)
    void updateEntity(AirportDTO.AirportUpdateRequest dto, @MappingTarget Airport entity);

    // Response
    AirportDTO.AirportResponse toResponse(Airport entity);
}