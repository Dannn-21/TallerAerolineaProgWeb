package com.ejercicioAerolinea.api.dto;

import java.io.Serializable;

public class TagDTO {
    public record TagCreateRequest(String name) implements Serializable {}
    public record TagUpdateRequest(String name) implements Serializable {}
    public record TagResponse(Long id, String name) implements Serializable {}
}
