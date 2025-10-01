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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FlightService {

    private final FlightRepository flightRepository;
    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final TagRepository tagRepository;

    @Transactional
    public FlightDTO.FlightResponse create(FlightDTO.FlightCreateRequest dto) {
        Airline a = airlineRepository.findByCode(dto.airlineCode())
                .orElseThrow(() -> new EntityNotFoundException("Airline not found"));
        Airport origin = airportRepository.findByCode(dto.originCode())
                .orElseThrow(() -> new EntityNotFoundException("Origin not found"));
        Airport dest = airportRepository.findByCode(dto.destinationCode())
                .orElseThrow(() -> new EntityNotFoundException("Destination not found"));

        Set<Tag> tags = dto.tagNames() == null ? Set.of() :
                dto.tagNames().stream()
                        .map(name -> tagRepository.findByNameIgnoreCase(name)
                                .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + name)))
                        .collect(Collectors.toSet());

        Flight f = FlightMapper.toEntity(dto, a, origin, dest, tags);
        flightRepository.save(f);
        return FlightMapper.toResponse(f);
    }

    @Transactional(readOnly = true)
    public FlightDTO.FlightResponse getById(Long id) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));
        return FlightMapper.toResponse(f);
    }

    @Transactional
    public FlightDTO.FlightResponse update(Long id, FlightDTO.FlightUpdateRequest dto) {
        Flight f = flightRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Flight not found"));

        Airline a = (dto.airlineCode() == null) ? null :
                airlineRepository.findByCode(dto.airlineCode())
                        .orElseThrow(() -> new EntityNotFoundException("Airline not found"));
        Airport origin = (dto.originCode() == null) ? null :
                airportRepository.findByCode(dto.originCode())
                        .orElseThrow(() -> new EntityNotFoundException("Origin not found"));
        Airport dest = (dto.destinationCode() == null) ? null :
                airportRepository.findByCode(dto.destinationCode())
                        .orElseThrow(() -> new EntityNotFoundException("Destination not found"));

        Set<Tag> tags = (dto.tagNames() == null) ? null :
                dto.tagNames().stream()
                        .map(name -> tagRepository.findByNameIgnoreCase(name)
                                .orElseThrow(() -> new EntityNotFoundException("Tag not found: " + name)))
                        .collect(Collectors.toSet());

        FlightMapper.updateEntity(f, dto, a, origin, dest, tags);
        return FlightMapper.toResponse(f);
    }
}
