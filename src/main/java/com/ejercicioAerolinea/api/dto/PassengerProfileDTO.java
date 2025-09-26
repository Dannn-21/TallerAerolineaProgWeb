package com.ejercicioAerolinea.api.dto;

import java.io.Serializable;

public class PassengerProfileDTO {

    public record PassengerProfileCreateRequest(
            Long passengerId,
            String phone,
            String countryCode
    ) implements Serializable {}

    public record PassengerProfileUpdateRequest(
            String phone,
            String countryCode
    ) implements Serializable {}

    public record PassengerProfileResponse(
            Long id,
            Long passengerId,
            String phone,
            String countryCode
    ) implements Serializable {}
}
