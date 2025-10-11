package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.PassengerProfileDTO;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.entities.PassengerProfile;
import com.ejercicioAerolinea.mappers.PassengerProfileMapper;
import com.ejercicioAerolinea.repositories.PassengerProfileRepository;
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
public class PassengerProfileService {

    private final PassengerProfileMapper passengerProfileMapper;
    private final PassengerRepository passengerRepository;
    private final PassengerProfileRepository passengerProfileRepository;

    public PassengerProfileDTO.PassengerProfileResponse create(PassengerProfileDTO.PassengerProfileCreateRequest dto) {
        Passenger passenger = passengerRepository.findById(dto.passengerId())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        PassengerProfile profile = passengerProfileMapper.toEntity(dto, passenger);
        passengerProfileRepository.save(profile);
        return passengerProfileMapper.toResponse(profile);
    }

    @Transactional(readOnly = true)
    public PassengerProfileDTO.PassengerProfileResponse getById(Long id) {
        return passengerProfileRepository.findById(id)
                .map(passengerProfileMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Passenger profile not found"));
    }

    @Transactional(readOnly = true)
    public Page<PassengerProfileDTO.PassengerProfileResponse> list(Pageable pageable) {
        return passengerProfileRepository.findAll(pageable).map(passengerProfileMapper::toResponse);
    }

    public PassengerProfileDTO.PassengerProfileResponse update(Long id, PassengerProfileDTO.PassengerProfileUpdateRequest dto) {
        PassengerProfile profile = passengerProfileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Passenger profile not found"));
        Passenger passenger = profile.getPassenger();
        passengerProfileMapper.updateEntity(dto, profile, passenger);
        return passengerProfileMapper.toResponse(profile);
    }

    public void delete(Long id) {
        if (!passengerProfileRepository.existsById(id)) {
            throw new EntityNotFoundException("Passenger profile not found");
        }
        passengerProfileRepository.deleteById(id);
    }
}
