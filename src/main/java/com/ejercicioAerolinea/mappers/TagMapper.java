package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.TagDTO;
import com.ejercicioAerolinea.entities.Tag;

public class TagMapper {

    public static Tag toEntity(TagDTO.TagCreateRequest dto) {
        return Tag.builder().name(dto.name()).build();
    }

    public static void updateEntity(Tag t, TagDTO.TagUpdateRequest dto) {
        if (dto.name() != null) t.setName(dto.name());
    }

    public static TagDTO.TagResponse toResponse(Tag t) {
        return new TagDTO.TagResponse(t.getId(), t.getName());
    }
}
