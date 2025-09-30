package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.TagDTO;
import com.ejercicioAerolinea.entities.Tag;
import com.ejercicioAerolinea.mappers.TagMapper;
import com.ejercicioAerolinea.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    @Transactional
    public TagDTO.TagResponse create(TagDTO.TagCreateRequest dto) {
        Tag t = TagMapper.toEntity(dto);
        tagRepository.save(t);
        return TagMapper.toResponse(t);
    }

    @Transactional(readOnly = true)
    public TagDTO.TagResponse getById(Long id) {
        Tag t = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        return TagMapper.toResponse(t);
    }

    @Transactional
    public TagDTO.TagResponse update(Long id, TagDTO.TagUpdateRequest dto) {
        Tag t = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        TagMapper.updateEntity(t, dto);
        return TagMapper.toResponse(t);
    }
}
