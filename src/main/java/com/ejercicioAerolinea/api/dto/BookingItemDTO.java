package com.ejercicioAerolinea.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BookingItemDTO {

    public record BookingItemCreateRequest(
            Long bookingId,
            Long flightId,
            String cabin,          // "ECONOMY", "PREMIUM", "BUSINESS"
            BigDecimal price,
            Integer segmentOrder
    ) implements Serializable {}

    public record BookingItemUpdateRequest(
            Long flightId,         // opcional: si viene, se cambia
            String cabin,          // opcional
            BigDecimal price,      // opcional
            Integer segmentOrder   // opcional
    ) implements Serializable {}

    public record BookingItemResponse(
            Long id,
            Long bookingId,
            String flightNumber,
            String cabin,
            BigDecimal price,
            Integer segmentOrder
    ) implements Serializable {}
}
