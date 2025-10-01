package com.ejercicioAerolinea.api.dto;

import java.io.Serializable;

public class PassengerDTO {

    public record PassengerCreateRequest(
            String fullName,
            String email,
            PassengerProfileDto profile
    ) implements Serializable {}

    public record PassengerUpdateRequest(
            String fullName,
            String email,
            PassengerProfileDto profile
    ) implements Serializable {}

    public record PassengerResponse(
            Long id,
            String fullName,
            String email,
            PassengerProfileDto profile
    ) implements Serializable {}

    public record PassengerProfileDto(
            String phone,
            String countryCode
    ) implements Serializable {}
}