package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.TagDTO;
import com.ejercicioAerolinea.entities.Tag;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TagMapper {

    // CREATE
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "flights", ignore = true) // relación ManyToMany con Flight
    Tag toEntity(TagDTO.TagCreateRequest dto);

    // UPDATE parcial
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "flights", ignore = true)
    void updateEntity(TagDTO.TagUpdateRequest dto, @MappingTarget Tag entity);

    // Response
    TagDTO.TagResponse toResponse(Tag entity);
}