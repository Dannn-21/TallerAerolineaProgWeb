package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.BookingItemDTO;
import com.ejercicioAerolinea.entities.Booking;
import com.ejercicioAerolinea.entities.BookingItem;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.mappers.BookingItemMapper;
import com.ejercicioAerolinea.repositories.BookingItemRepository;
import com.ejercicioAerolinea.repositories.BookingRepository;
import com.ejercicioAerolinea.repositories.FlightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingItemService {

    private final BookingItemRepository bookingItemRepository;
    private final BookingRepository bookingRepository;
    private final FlightRepository flightRepository;

    @Transactional
    public BookingItemDTO.BookingItemResponse create(BookingItemDTO.BookingItemCreateRequest dto) {
        Booking booking = bookingRepository.findById(dto.bookingId())
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        Flight flight = flightRepository.findById(dto.flightId())
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));
        BookingItem item = BookingItemMapper.toEntity(dto, booking, flight);
        bookingItemRepository.save(item);
        return BookingItemMapper.toResponse(item);
    }

    @Transactional(readOnly = true)
    public BookingItemDTO.BookingItemResponse getById(Long id) {
        BookingItem item = bookingItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookingItem not found"));
        return BookingItemMapper.toResponse(item);
    }

    @Transactional
    public BookingItemDTO.BookingItemResponse update(Long id, BookingItemDTO.BookingItemUpdateRequest dto) {
        BookingItem item = bookingItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookingItem not found"));
        Flight flight = (dto.flightId() == null) ? null :
                flightRepository.findById(dto.flightId())
                        .orElseThrow(() -> new EntityNotFoundException("Flight not found"));
        BookingItemMapper.updateEntity(item, dto, flight);
        return BookingItemMapper.toResponse(item);
    }
}
