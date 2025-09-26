package com.ejercicioAerolinea.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class BookingDTO {

    public record BookingCreateRequest(
            String passengerEmail,
            List<BookingItemDTO.BookingItemCreateRequest> items
    ) implements Serializable {}

    public record BookingUpdateRequest(
            List<BookingItemDTO.BookingItemCreateRequest> items
    ) implements Serializable {}

    public record BookingResponse(
            Long id,
            OffsetDateTime createdAt,
            String passengerEmail,
            List<BookingItemDTO.BookingItemResponse> items
    ) implements Serializable {}

    public record BookingListItem(
            Long id,
            OffsetDateTime createdAt,
            String passengerEmail,
            Integer itemsCount
    ) implements Serializable {}
}
