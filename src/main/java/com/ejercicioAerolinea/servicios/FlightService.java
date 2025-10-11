package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.FlightDTO;
import com.ejercicioAerolinea.entities.Airline;
import com.ejercicioAerolinea.entities.Airport;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.Tag;
import com.ejercicioAerolinea.mappers.FlightMapper;
import com.ejercicioAerolinea.repositories.AirlineRepository;
import com.ejercicioAerolinea.repositories.AirportRepository;
import com.ejercicioAerolinea.repositories.FlightRepository;
import com.ejercicioAerolinea.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FlightService {

    private final FlightMapper flightMapper;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final FlightRepository flightRepository;
    private final TagRepository tagRepository;

    @Transactional
    public FlightDTO.FlightResponse create(FlightDTO.FlightCreateRequest dto) {
        Airline a = airlineRepository.findByCode(dto.airlineCode())
                .orElseThrow(() -> new EntityNotFoundException("Airline not found"));
        Airport origin = airportRepository.findByCode(dto.originCode())
                .orElseThrow(() -> new EntityNotFoundException("Origin not found"));
        Airport dest = airportRepository.findByCode(dto.destinationCode())
                .orElseThrow(() -> new EntityNotFoundException("Destination not found"));

        Set<Tag> tags = (dto.tagNames() == null) ? Set.of() :
                dto.tagNames().stream()
                        .map(name -> tagRepository.findByNameIgnoreCase(name)
                                .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + name)))
                        .collect(Collectors.toSet());

        Flight f = flightMapper.toEntity(dto, a, origin, dest, tags);
        flightRepository.save(f);
        return flightMapper.toResponse(f);
    }

    @Transactional(readOnly = true)
    public FlightDTO.FlightResponse getById(Long id) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));
        return flightMapper.toResponse(f);
    }

    @Transactional(readOnly = true)
    public Page<FlightDTO.FlightResponse> list(Pageable pageable) {
        return flightRepository.findAll(pageable)
                .map(flightMapper::toResponse);
    }

    @Transactional
    public FlightDTO.FlightResponse update(Long id, FlightDTO.FlightUpdateRequest dto) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

        Airline a = (dto.airlineCode() == null) ? f.getAirline() :
                airlineRepository.findByCode(dto.airlineCode())
                        .orElseThrow(() -> new EntityNotFoundException("Airline not found"));

        Airport origin = (dto.originCode() == null) ? f.getOrigin() :
                airportRepository.findByCode(dto.originCode())
                        .orElseThrow(() -> new EntityNotFoundException("Origin not found"));

        Airport dest = (dto.destinationCode() == null) ? f.getDestination() :
                airportRepository.findByCode(dto.destinationCode())
                        .orElseThrow(() -> new EntityNotFoundException("Destination not found"));

        Set<Tag> tags = (dto.tagNames() == null) ? f.getTags() :
                dto.tagNames().stream()
                        .map(name -> tagRepository.findByNameIgnoreCase(name)
                                .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + name)))
                        .collect(Collectors.toSet());

        flightMapper.updateEntity(dto, f, a, origin, dest, tags);
        return flightMapper.toResponse(f);
    }

    public void delete(Long id) {
        if (!flightRepository.existsById(id)) {
            throw new EntityNotFoundException("Flight not found");
        }
        flightRepository.deleteById(id);
    }
}
