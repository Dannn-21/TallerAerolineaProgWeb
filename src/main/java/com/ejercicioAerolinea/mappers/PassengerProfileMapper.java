package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.PassengerProfileDTO;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.entities.PassengerProfile;

public class PassengerProfileMapper {

    public static PassengerProfile toEntity(PassengerProfileDTO.PassengerProfileCreateRequest dto, Passenger passenger) {
        PassengerProfile profile = PassengerProfile.builder()
                .phone(dto.phone())
                .countryCode(dto.countryCode())
                .build();

        passenger.setProfile(profile);
        return profile;
    }

    public static void updateEntity(PassengerProfile profile, PassengerProfileDTO.PassengerProfileUpdateRequest dto) {
        if (dto.phone() != null) profile.setPhone(dto.phone());
        if (dto.countryCode() != null) profile.setCountryCode(dto.countryCode());
    }

    public static PassengerProfileDTO.PassengerProfileResponse toResponse(PassengerProfile profile) {
        Long passengerId = profile.getPassenger() != null ? profile.getPassenger().getId() : null;
        return new PassengerProfileDTO.PassengerProfileResponse(
                profile.getId(),
                passengerId,
                profile.getPhone(),
                profile.getCountryCode()
        );
    }
}
