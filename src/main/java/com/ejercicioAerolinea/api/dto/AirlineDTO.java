package com.ejercicioAerolinea.api.dto;

import java.io.Serializable;

public class AirlineDTO {

    public record AirlineCreateRequest(String code, String name) implements Serializable {}
    public record AirlineUpdateRequest(String code, String name) implements Serializable {}
    public record AirlineResponse(Long id, String code, String name) implements Serializable {}
}
