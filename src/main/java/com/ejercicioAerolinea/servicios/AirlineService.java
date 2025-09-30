package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.AirlineDTO;
import com.ejercicioAerolinea.entities.Airline;
import com.ejercicioAerolinea.mappers.AirlineMapper;
import com.ejercicioAerolinea.repositories.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AirlineService {

    private final AirlineRepository airlineRepository;

    @Transactional
    public AirlineDTO.AirlineResponse create(AirlineDTO.AirlineCreateRequest dto) {
        Airline a = AirlineMapper.toEntity(dto);
        airlineRepository.save(a);
        return AirlineMapper.toResponse(a);
    }

    @Transactional(readOnly = true)
    public AirlineDTO.AirlineResponse getById(Long id) {
        Airline a = airlineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airline not found"));
        return AirlineMapper.toResponse(a);
    }

    @Transactional
    public AirlineDTO.AirlineResponse update(Long id, AirlineDTO.AirlineUpdateRequest dto) {
        Airline a = airlineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airline not found"));
        AirlineMapper.updateEntity(a, dto);
        return AirlineMapper.toResponse(a);
    }
}
