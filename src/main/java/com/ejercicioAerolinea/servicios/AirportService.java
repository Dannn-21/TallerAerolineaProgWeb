package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.AirportDTO;
import com.ejercicioAerolinea.entities.Airport;
import com.ejercicioAerolinea.mappers.AirportMapper;
import com.ejercicioAerolinea.repositories.AirportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AirportService {

    private final AirportRepository airportRepository;

    @Transactional
    public AirportDTO.AirportResponse create(AirportDTO.AirportCreateRequest dto) {
        Airport ap = AirportMapper.toEntity(dto);
        airportRepository.save(ap);
        return AirportMapper.toResponse(ap);
    }

    @Transactional(readOnly = true)
    public AirportDTO.AirportResponse getById(Long id) {
        Airport ap = airportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found"));
        return AirportMapper.toResponse(ap);
    }

    @Transactional
    public AirportDTO.AirportResponse update(Long id, AirportDTO.AirportUpdateRequest dto) {
        Airport ap = airportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found"));
        AirportMapper.updateEntity(ap, dto);
        return AirportMapper.toResponse(ap);
    }
}
