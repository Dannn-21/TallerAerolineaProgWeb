package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.PassengerDTO;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.entities.PassengerProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.AfterMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper (componentModel = "spring")
public interface PassengerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    @Mapping(target = "profile", expression = "java(toProfile(dto.profile()))")
    Passenger toEntity(PassengerDTO.PassengerCreateRequest dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "bookings", ignore = true)
    void updateEntity(PassengerDTO.PassengerUpdateRequest dto, @MappingTarget Passenger entity);

    @Mapping(target = "profile", expression = "java(toProfileDto(entity.getProfile()))")
    PassengerDTO.PassengerResponse toResponse(Passenger entity);

    // helpers simples
    default PassengerProfile toProfile(PassengerDTO.PassengerProfileDto dto){
        if (dto == null) return null;
        return PassengerProfile.builder().phone(dto.phone()).countryCode(dto.countryCode()).build();
    }
    default PassengerDTO.PassengerProfileDto toProfileDto(PassengerProfile p){
        return p == null ? null : new PassengerDTO.PassengerProfileDto(p.getPhone(), p.getCountryCode());
    }

    @AfterMapping
    default void link(@MappingTarget Passenger p){
        if (p.getProfile()!=null) p.getProfile().setPassenger(p);
    }
}