package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.BookingDTO.*;
import com.ejercicioAerolinea.entities.Booking;
import com.ejercicioAerolinea.mappers.BookingMapper;
import com.ejercicioAerolinea.repositories.BookingRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository repo;
    private final BookingMapper mapper;

    public BookingResponse create(BookingCreateRequest req) {
        Booking saved = repo.save(mapper.toEntity(req));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public BookingResponse get(Long id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Booking %d not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    public Page<BookingResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toResponse);
    }

    public BookingResponse update(Long id, BookingUpdateRequest req) {
        Booking b = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Booking %d not found".formatted(id)));
        mapper.updateEntity(req, b);
        return mapper.toResponse(b);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
