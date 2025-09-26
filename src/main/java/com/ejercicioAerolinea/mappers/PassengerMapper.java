package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.PassengerDTO;
import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.entities.PassengerProfile;

public class PassengerMapper {

    public static Passenger toEntity(PassengerDTO.PassengerCreateRequest dto) {
        Passenger.PassengerBuilder builder = Passenger.builder()
                .fullName(dto.fullName())
                .email(dto.email());

        if (dto.profile() != null) {
            PassengerProfile profile = PassengerProfile.builder()
                    .phone(dto.profile().phone())
                    .countryCode(dto.profile().countryCode())
                    .build();
            builder.profile(profile);
        }

        return builder.build();
    }

    public static void updateEntity(Passenger passenger, PassengerDTO.PassengerUpdateRequest dto) {
        if (dto.fullName() != null) {
            passenger.setFullName(dto.fullName());
        }
        if (dto.email() != null) {
            passenger.setEmail(dto.email());
        }
        if (dto.profile() != null) {
            if (passenger.getProfile() == null) {
                passenger.setProfile(PassengerProfile.builder().build());
            }
            passenger.getProfile().setPhone(dto.profile().phone());
            passenger.getProfile().setCountryCode(dto.profile().countryCode());
        }
    }

    public static PassengerDTO.PassengerResponse toResponse(Passenger passenger) {
        PassengerDTO.PassengerProfileDto profileDto = null;

        if (passenger.getProfile() != null) {
            profileDto = new PassengerDTO.PassengerProfileDto(
                    passenger.getProfile().getPhone(),
                    passenger.getProfile().getCountryCode()
            );
        }

        return new PassengerDTO.PassengerResponse(
                passenger.getId(),
                passenger.getFullName(),
                passenger.getEmail(),
                profileDto
        );
    }
}