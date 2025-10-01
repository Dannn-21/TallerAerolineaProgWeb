package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.BookingDTO;
import com.ejercicioAerolinea.entities.Booking;
import com.ejercicioAerolinea.entities.BookingItem;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.mappers.BookingItemMapper;
import com.ejercicioAerolinea.mappers.BookingMapper;
import com.ejercicioAerolinea.repositories.BookingItemRepository;
import com.ejercicioAerolinea.repositories.BookingRepository;
import com.ejercicioAerolinea.repositories.FlightRepository;
import com.ejercicioAerolinea.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BookingItemRepository bookingItemRepository;
    private final PassengerRepository passengerRepository;
    private final FlightRepository flightRepository;

    @Transactional
    public BookingDTO.BookingResponse create(BookingDTO.BookingCreateRequest dto) {
        Passenger passenger = passengerRepository.findByEmailIgnoreCase(dto.passengerEmail())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));

        List<BookingItem> items = dto.items() == null ? List.of() :
                dto.items().stream().map(i -> {
                    Flight f = flightRepository.findById(i.flightId())
                            .orElseThrow(() -> new EntityNotFoundException("Flight not found: " + i.flightId()));
                    return BookingItemMapper.toEntity(i, null /*booking se setea luego*/, f);
                }).toList();

        Booking booking = BookingMapper.toEntity(dto, passenger, items);
        booking = bookingRepository.save(booking);
        return BookingMapper.toResponse(booking);
    }

    @Transactional(readOnly = true)
    public BookingDTO.BookingResponse getById(Long id) {
        Booking b = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        return BookingMapper.toResponse(b);
    }

    @Transactional
    public BookingDTO.BookingResponse update(Long id, BookingDTO.BookingUpdateRequest dto) {
        Booking b = bookingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        List<BookingItem> newItems = null;
        if (dto.items() != null) {
            newItems = dto.items().stream().map(i -> {
                Flight f = flightRepository.findById(i.flightId())
                        .orElseThrow(() -> new EntityNotFoundException("Flight not found: " + i.flightId()));
                return BookingItemMapper.toEntity(i, b, f);
            }).toList();
        }

        BookingMapper.updateEntity(b, dto, newItems);
        return BookingMapper.toResponse(b);
    }
}
