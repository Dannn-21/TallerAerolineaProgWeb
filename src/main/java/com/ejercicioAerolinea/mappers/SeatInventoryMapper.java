package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.SeatInventoryDTO;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.SeatInventory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface SeatInventoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flight", expression = "java(flight)")
    @Mapping(target = "cabin", expression = "java(SeatInventory.Cabin.valueOf(dto.cabin()))")
    SeatInventory toEntity(SeatInventoryDTO.SeatInventoryCreateRequest dto, Flight flight);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "flight", expression = "java(flight)")
    @Mapping(target = "cabin", expression = "java(dto.cabin()==null? null : SeatInventory.Cabin.valueOf(dto.cabin()))")
    void updateEntity(SeatInventoryDTO.SeatInventoryUpdateRequest dto, @MappingTarget SeatInventory entity, Flight flight);

    @Mapping(target = "flightId", source = "flight.id")
    @Mapping(target = "flightNumber", source = "flight.number")
    @Mapping(target = "cabin", expression = "java(entity.getCabin().name())")
    SeatInventoryDTO.SeatInventoryResponse toResponse(SeatInventory entity);
}