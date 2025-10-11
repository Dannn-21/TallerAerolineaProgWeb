package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.PassengerDTO;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.mappers.PassengerMapper;
import com.ejercicioAerolinea.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PassengerService {

    private final PassengerMapper passengerMapper;
    private final PassengerRepository passengerRepository;

    public PassengerDTO.PassengerResponse create(PassengerDTO.PassengerCreateRequest dto) {
        Passenger p = passengerMapper.toEntity(dto);
        passengerRepository.save(p);
        return passengerMapper.toResponse(p);
    }

    @Transactional(readOnly = true)
    public PassengerDTO.PassengerResponse getById(Long id) {
        return passengerRepository.findById(id)
                .map(passengerMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
    }

    @Transactional(readOnly = true)
    public Page<PassengerDTO.PassengerResponse> list(Pageable pageable) {
        return passengerRepository.findAll(pageable).map(passengerMapper::toResponse);
    }

    public PassengerDTO.PassengerResponse update(Long id, PassengerDTO.PassengerUpdateRequest dto) {
        Passenger p = passengerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        passengerMapper.updateEntity(dto, p);
        return passengerMapper.toResponse(p);
    }

    public void delete(Long id) {
        if (!passengerRepository.existsById(id)) {
            throw new EntityNotFoundException("Passenger not found");
        }
        passengerRepository.deleteById(id);
    }
}
