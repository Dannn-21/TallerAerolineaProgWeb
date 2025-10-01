package com.ejercicioAerolinea.api.dto;

import java.io.Serializable;

public class SeatInventoryDTO {

    public record SeatInventoryCreateRequest(
            Long flightId,
            String cabin,           // "ECONOMY", "PREMIUM", "BUSINESS"
            Integer totalSeats,
            Integer availableSeats
    ) implements Serializable {}

    public record SeatInventoryUpdateRequest(
            String cabin,
            Integer totalSeats,
            Integer availableSeats
    ) implements Serializable {}

    public record SeatInventoryResponse(
            Long id,
            Long flightId,
            String flightNumber,
            String cabin,
            Integer totalSeats,
            Integer availableSeats
    ) implements Serializable {}
}
