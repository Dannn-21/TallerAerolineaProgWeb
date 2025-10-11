package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.AirlineDTO;
import com.ejercicioAerolinea.entities.Airline;
import com.ejercicioAerolinea.mappers.AirlineMapper;
import com.ejercicioAerolinea.repositories.AirlineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AirlineService {

    private final AirlineMapper airlineMapper;
    private final AirlineRepository airlineRepository;

    public AirlineDTO.AirlineResponse create(AirlineDTO.AirlineCreateRequest dto) {
        Airline a = airlineMapper.toEntity(dto);
        airlineRepository.save(a);
        return airlineMapper.toResponse(a);
    }

    @Transactional(readOnly = true)
    public AirlineDTO.AirlineResponse getById(Long id) {
        Airline a = airlineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airline not found"));
        return airlineMapper.toResponse(a);
    }

    @Transactional(readOnly = true)
    public Page<AirlineDTO.AirlineResponse> list(Pageable pageable) {
        return airlineRepository.findAll(pageable).map(airlineMapper::toResponse);
    }

    public AirlineDTO.AirlineResponse update(Long id, AirlineDTO.AirlineUpdateRequest dto) {
        Airline a = airlineRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airline not found"));
        airlineMapper.updateEntity(dto, a);
        return airlineMapper.toResponse(a);
    }

    public void delete(Long id) {
        if (!airlineRepository.existsById(id)) {
            throw new EntityNotFoundException("Airline not found");
        }
        airlineRepository.deleteById(id);
    }
}
