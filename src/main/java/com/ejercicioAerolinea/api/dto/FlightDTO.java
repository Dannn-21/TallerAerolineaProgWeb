package com.ejercicioAerolinea.api.dto;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

public class FlightDTO {

    public record FlightCreateRequest(
            String number,
            String airlineCode,
            String originCode,
            String destinationCode,
            OffsetDateTime departureTime,
            OffsetDateTime arrivalTime,
            List<String> tagNames
    ) implements Serializable {}

    public record FlightUpdateRequest(
            String number,
            String airlineCode,
            String originCode,
            String destinationCode,
            OffsetDateTime departureTime,
            OffsetDateTime arrivalTime,
            List<String> tagNames
    ) implements Serializable {}

    // Respuesta con resúmenes simples
    public record AirlineSummary(String code, String name) implements Serializable {}
    public record AirportSummary(String code, String name, String city) implements Serializable {}

    public record FlightResponse(
            Long id,
            String number,
            AirlineSummary airline,
            AirportSummary origin,
            AirportSummary destination,
            OffsetDateTime departureTime,
            OffsetDateTime arrivalTime,
            List<String> tags
    ) implements Serializable {}
}
