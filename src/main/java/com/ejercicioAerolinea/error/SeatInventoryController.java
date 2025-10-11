package com.ejercicioAerolinea.error;

import com.ejercicioAerolinea.api.dto.SeatInventoryDTO.*;
import com.ejercicioAerolinea.servicios.SeatInventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/seat-inventories")
public class SeatInventoryController {

    private final SeatInventoryService service;

    @PostMapping
    public ResponseEntity<SeatInventoryResponse> create(@Valid @RequestBody SeatInventoryCreateRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatInventoryResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<SeatInventoryResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatInventoryResponse> update(@PathVariable Long id, @Valid @RequestBody SeatInventoryUpdateRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

