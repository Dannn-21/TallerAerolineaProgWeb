package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.BookingItemDTO;
import com.ejercicioAerolinea.entities.Booking;
import com.ejercicioAerolinea.entities.BookingItem;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.SeatInventory;

public class BookingItemMapper {

    public static BookingItem toEntity(BookingItemDTO.BookingItemCreateRequest dto, Booking booking, Flight flight) {
        BookingItem item = BookingItem.builder()
                .flight(flight)
                .cabin(SeatInventory.Cabin.valueOf(dto.cabin()))
                .price(dto.price())
                .segmentOrder(dto.segmentOrder())
                .build();

        booking.addItem(item);
        return item;
    }

    public static void updateEntity(BookingItem item, BookingItemDTO.BookingItemUpdateRequest dto, Flight flight) {
        if (flight != null) item.setFlight(flight);
        if (dto.cabin() != null) item.setCabin(SeatInventory.Cabin.valueOf(dto.cabin()));
        if (dto.price() != null) item.setPrice(dto.price());
        if (dto.segmentOrder() != null) item.setSegmentOrder(dto.segmentOrder());
    }

    public static BookingItemDTO.BookingItemResponse toResponse(BookingItem item) {
        Long bookingId = item.getBooking() != null ? item.getBooking().getId() : null;
        String flightNumber = item.getFlight() != null ? item.getFlight().getNumber() : null;

        return new BookingItemDTO.BookingItemResponse(
                item.getId(),
                bookingId,
                flightNumber,
                item.getCabin() != null ? item.getCabin().name() : null,
                item.getPrice(),
                item.getSegmentOrder()
        );
    }
}
