package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.BookingItemDTO.*;
import com.ejercicioAerolinea.entities.BookingItem;
import com.ejercicioAerolinea.mappers.BookingItemMapper;
import com.ejercicioAerolinea.repositories.BookingItemRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingItemService {

    private final BookingItemRepository repo;
    private final BookingItemMapper mapper;

    public BookingItemResponse create(BookingItemCreateRequest req) {
        BookingItem saved = repo.save(mapper.toEntity(req));
        return mapper.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public BookingItemResponse get(Long id) {
        return repo.findById(id).map(mapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("BookingItem %d not found".formatted(id)));
    }

    @Transactional(readOnly = true)
    public Page<BookingItemResponse> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toResponse);
    }

    public BookingItemResponse update(Long id, BookingItemUpdateRequest req) {
        BookingItem bi = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("BookingItem %d not found".formatted(id)));
        mapper.updateEntity(req, bi);
        return mapper.toResponse(bi);
    }

    public void delete(Long id) { repo.deleteById(id); }
}
