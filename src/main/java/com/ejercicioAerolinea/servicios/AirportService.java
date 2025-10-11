package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.AirportDTO;
import com.ejercicioAerolinea.entities.Airport;
import com.ejercicioAerolinea.mappers.AirportMapper;
import com.ejercicioAerolinea.repositories.AirportRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AirportService {

    private final AirportMapper airportMapper;
    private final AirportRepository airportRepository;

    public AirportDTO.AirportResponse create(AirportDTO.AirportCreateRequest dto) {
        Airport a = airportMapper.toEntity(dto);
        airportRepository.save(a);
        return airportMapper.toResponse(a);
    }

    @Transactional(readOnly = true)
    public AirportDTO.AirportResponse getById(Long id) {
        return airportRepository.findById(id)
                .map(airportMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found"));
    }

    @Transactional(readOnly = true)
    public Page<AirportDTO.AirportResponse> list(Pageable pageable) {
        return airportRepository.findAll(pageable).map(airportMapper::toResponse);
    }

    public AirportDTO.AirportResponse update(Long id, AirportDTO.AirportUpdateRequest dto) {
        Airport a = airportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Airport not found"));
        airportMapper.updateEntity(dto, a);
        return airportMapper.toResponse(a);
    }

    public void delete(Long id) {
        if (!airportRepository.existsById(id)) {
            throw new EntityNotFoundException("Airport not found");
        }
        airportRepository.deleteById(id);
    }
}
