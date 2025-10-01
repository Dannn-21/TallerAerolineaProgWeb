package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.PassengerProfileDTO;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.entities.PassengerProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PassengerProfileMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passenger", expression = "java(passenger)")
    PassengerProfile toEntity(PassengerProfileDTO.PassengerProfileCreateRequest dto, Passenger passenger);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "passenger", ignore = true)
    void updateEntity(PassengerProfileDTO.PassengerProfileUpdateRequest dto, @MappingTarget PassengerProfile entity);

    @Mapping(target = "passengerId", source = "passenger.id")
    PassengerProfileDTO.PassengerProfileResponse toResponse(PassengerProfile entity);
}

