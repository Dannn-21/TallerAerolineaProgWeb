package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.PassengerDTO;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.mappers.PassengerMapper;
import com.ejercicioAerolinea.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassengerService {

    private final PassengerRepository passengerRepository;

    @Transactional
    public PassengerDTO.PassengerResponse create(PassengerDTO.PassengerCreateRequest dto) {
        Passenger p = PassengerMapper.toEntity(dto);
        passengerRepository.save(p);
        return PassengerMapper.toResponse(p);
    }

    @Transactional(readOnly = true)
    public PassengerDTO.PassengerResponse getById(Long id) {
        Passenger p = passengerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        return PassengerMapper.toResponse(p);
    }

    @Transactional
    public PassengerDTO.PassengerResponse update(Long id, PassengerDTO.PassengerUpdateRequest dto) {
        Passenger p = passengerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        PassengerMapper.updateEntity(p, dto);
        return PassengerMapper.toResponse(p);
    }
}
