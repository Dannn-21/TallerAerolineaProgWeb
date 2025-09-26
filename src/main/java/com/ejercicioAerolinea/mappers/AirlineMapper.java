package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.AirlineDTO;
import com.ejercicioAerolinea.entities.Airline;

public class AirlineMapper {

    public static Airline toEntity(AirlineDTO.AirlineCreateRequest dto) {
        return Airline.builder()
                .code(dto.code())
                .name(dto.name())
                .build();
    }

    public static void updateEntity(Airline a, AirlineDTO.AirlineUpdateRequest dto) {
        if (dto.code() != null) a.setCode(dto.code());
        if (dto.name() != null) a.setName(dto.name());
    }

    public static AirlineDTO.AirlineResponse toResponse(Airline a) {
        return new AirlineDTO.AirlineResponse(a.getId(), a.getCode(), a.getName());
    }
}
