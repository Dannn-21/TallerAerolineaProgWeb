package com.ejercicioAerolinea.error;

import com.ejercicioAerolinea.api.dto.PassengerProfileDTO.*;
import com.ejercicioAerolinea.servicios.PassengerProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/passenger-profiles")
public class PassengerProfileController {

    private final PassengerProfileService service;

    @PostMapping
    public ResponseEntity<PassengerProfileResponse> create(@Valid @RequestBody PassengerProfileCreateRequest req) {
        return ResponseEntity.ok(service.create(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PassengerProfileResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<PassengerProfileResponse>> list(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PassengerProfileResponse> update(@PathVariable Long id, @Valid @RequestBody PassengerProfileUpdateRequest req) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
