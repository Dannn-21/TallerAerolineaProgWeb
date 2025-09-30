package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.SeatInventoryDTO;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.SeatInventory;
import com.ejercicioAerolinea.mappers.SeatInventoryMapper;
import com.ejercicioAerolinea.repositories.FlightRepository;
import com.ejercicioAerolinea.repositories.SeatInventoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SeatInventoryService {

    private final SeatInventoryRepository seatInventoryRepository;
    private final FlightRepository flightRepository;

    @Transactional
    public SeatInventoryDTO.SeatInventoryResponse create(SeatInventoryDTO.SeatInventoryCreateRequest dto) {
        Flight f = flightRepository.findById(dto.flightId())
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));
        SeatInventory si = SeatInventoryMapper.toEntity(dto, f);
        seatInventoryRepository.save(si);
        return SeatInventoryMapper.toResponse(si);
    }

    @Transactional(readOnly = true)
    public SeatInventoryDTO.SeatInventoryResponse getById(Long id) {
        SeatInventory si = seatInventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SeatInventory not found"));
        return SeatInventoryMapper.toResponse(si);
    }

    @Transactional
    public SeatInventoryDTO.SeatInventoryResponse update(
            Long id,
            SeatInventoryDTO.SeatInventoryUpdateRequest dto
    ) {
        SeatInventory si = seatInventoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("SeatInventory not found"));

        Flight f = null;

        SeatInventoryMapper.updateEntity(si, dto, f);
        return SeatInventoryMapper.toResponse(si);
    }
}
