package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.PassengerProfileDTO;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.entities.PassengerProfile;
import com.ejercicioAerolinea.mappers.PassengerProfileMapper;
import com.ejercicioAerolinea.repositories.PassengerProfileRepository;
import com.ejercicioAerolinea.repositories.PassengerRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PassengerProfileService {

    private final PassengerProfileRepository profileRepository;
    private final PassengerRepository passengerRepository;

    @Transactional
    public PassengerProfileDTO.PassengerProfileResponse create(PassengerProfileDTO.PassengerProfileCreateRequest dto) {
        Passenger p = passengerRepository.findById(dto.passengerId())
                .orElseThrow(() -> new EntityNotFoundException("Passenger not found"));
        PassengerProfile profile = PassengerProfileMapper.toEntity(dto, p);
        // por cascade desde Passenger → Profile, puede bastar con guardar passenger; aquí guardamos perfil por claridad
        profileRepository.save(profile);
        return PassengerProfileMapper.toResponse(profile);
    }

    @Transactional(readOnly = true)
    public PassengerProfileDTO.PassengerProfileResponse getById(Long id) {
        PassengerProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PassengerProfile not found"));
        return PassengerProfileMapper.toResponse(profile);
    }

    @Transactional
    public PassengerProfileDTO.PassengerProfileResponse update(Long id, PassengerProfileDTO.PassengerProfileUpdateRequest dto) {
        PassengerProfile profile = profileRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("PassengerProfile not found"));
        PassengerProfileMapper.updateEntity(profile, dto);
        return PassengerProfileMapper.toResponse(profile);
    }
}
