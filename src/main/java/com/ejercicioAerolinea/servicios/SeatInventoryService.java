package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.SeatInventoryDTO;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.SeatInventory;
import com.ejercicioAerolinea.mappers.SeatInventoryMapper;
import com.ejercicioAerolinea.repositories.FlightRepository;
import com.ejercicioAerolinea.repositories.SeatInventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatInventoryService {

    private final SeatInventoryMapper seatInventoryMapper;
    private final SeatInventoryRepository seatInventoryRepository;
    private final FlightRepository flightRepository;

    public SeatInventoryDTO.SeatInventoryResponse create(SeatInventoryDTO.SeatInventoryCreateRequest dto) {
        Flight f = flightRepository.findById(dto.flightId())
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));
        SeatInventory inv = seatInventoryMapper.toEntity(dto, f);
        seatInventoryRepository.save(inv);
        return seatInventoryMapper.toResponse(inv);
    }

    @Transactional(readOnly = true)
    public SeatInventoryDTO.SeatInventoryResponse getById(Long id) {
        return seatInventoryRepository.findById(id)
                .map(seatInventoryMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Seat inventory not found"));
    }

    @Transactional(readOnly = true)
    public Page<SeatInventoryDTO.SeatInventoryResponse> list(Pageable pageable) {
        return seatInventoryRepository.findAll(pageable).map(seatInventoryMapper::toResponse);
    }

    public SeatInventoryDTO.SeatInventoryResponse update(Long id, SeatInventoryDTO.SeatInventoryUpdateRequest dto) {
        SeatInventory inv = seatInventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seat inventory not found"));
        Flight f = inv.getFlight(); // mantener vuelo actual
        seatInventoryMapper.updateEntity(dto, inv, f);
        return seatInventoryMapper.toResponse(inv);
    }

    public void delete(Long id) {
        if (!seatInventoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Seat inventory not found");
        }
        seatInventoryRepository.deleteById(id);
    }
}
