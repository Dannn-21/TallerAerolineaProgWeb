package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.AirportDTO;
import com.ejercicioAerolinea.entities.Airport;

public class AirportMapper {

    public static Airport toEntity(AirportDTO.AirportCreateRequest dto) {
        return Airport.builder()
                .code(dto.code())
                .name(dto.name())
                .city(dto.city())
                .build();
    }

    public static void updateEntity(Airport ap, AirportDTO.AirportUpdateRequest dto) {
        if (dto.code() != null) ap.setCode(dto.code());
        if (dto.name() != null) ap.setName(dto.name());
        if (dto.city() != null) ap.setCity(dto.city());
    }

    public static AirportDTO.AirportResponse toResponse(Airport ap) {
        return new AirportDTO.AirportResponse(ap.getId(), ap.getCode(), ap.getName(), ap.getCity());
    }
}
