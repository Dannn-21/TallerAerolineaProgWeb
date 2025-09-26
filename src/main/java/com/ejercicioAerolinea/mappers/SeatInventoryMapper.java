package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.SeatInventoryDTO;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.SeatInventory;

public class SeatInventoryMapper {

    public static SeatInventory toEntity(SeatInventoryDTO.SeatInventoryCreateRequest dto, Flight flight) {
        return SeatInventory.builder()
                .flight(flight)
                .cabin(SeatInventory.Cabin.valueOf(dto.cabin()))
                .totalSeats(dto.totalSeats())
                .availableSeats(dto.availableSeats())
                .build();
    }

    public static void updateEntity(SeatInventory si, SeatInventoryDTO.SeatInventoryUpdateRequest dto, Flight flight) {
        if (flight != null) si.setFlight(flight);
        if (dto.cabin() != null) si.setCabin(SeatInventory.Cabin.valueOf(dto.cabin()));
        if (dto.totalSeats() != null) si.setTotalSeats(dto.totalSeats());
        if (dto.availableSeats() != null) si.setAvailableSeats(dto.availableSeats());
    }

    public static SeatInventoryDTO.SeatInventoryResponse toResponse(SeatInventory si) {
        return new SeatInventoryDTO.SeatInventoryResponse(
                si.getId(),
                si.getFlight().getId(),
                si.getFlight().getNumber(),
                si.getCabin().name(),
                si.getTotalSeats(),
                si.getAvailableSeats()
        );
    }
}
