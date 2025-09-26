package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.BookingDTO;
import com.ejercicioAerolinea.api.dto.BookingItemDTO;
import com.ejercicioAerolinea.entities.Booking;
import com.ejercicioAerolinea.entities.BookingItem;
import com.ejercicioAerolinea.entities.Passenger;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookingMapper {

    public static Booking toEntity(BookingDTO.BookingCreateRequest dto,
                                   Passenger passenger,
                                   List<BookingItem> items) {
        Booking b = Booking.builder()
                .passenger(passenger)
                .createdAt(OffsetDateTime.now())
                .build();
        if (items != null) items.forEach(b::addItem);
        return b;
    }

    public static void updateEntity(Booking b,
                                    BookingDTO.BookingUpdateRequest dto,
                                    List<BookingItem> newItems) {
        if (dto.items() != null) {
            if (b.getItems() != null) {
                b.getItems().forEach(it -> it.setBooking(null));
                b.getItems().clear();
            }
            if (newItems != null) newItems.forEach(b::addItem);
        }
    }

    public static BookingDTO.BookingResponse toResponse(Booking b) {
        var items = b.getItems() == null ? List.<BookingItem>of() : b.getItems();

        List<BookingItemDTO.BookingItemResponse> itemDtos = items.stream()
                .sorted(Comparator.comparing(i -> i.getSegmentOrder() == null ? 0 : i.getSegmentOrder()))
                .map(BookingItemMapper::toResponse) // ya devuelve BookingItemDTO.BookingItemResponse
                .collect(Collectors.toList());

        return new BookingDTO.BookingResponse(
                b.getId(),
                b.getCreatedAt(),
                b.getPassenger().getEmail(),
                itemDtos
        );
    }

    public static BookingDTO.BookingListItem toListItem(Booking b) {
        int count = b.getItems() == null ? 0 : b.getItems().size();
        return new BookingDTO.BookingListItem(b.getId(), b.getCreatedAt(), b.getPassenger().getEmail(), count);
    }
}
