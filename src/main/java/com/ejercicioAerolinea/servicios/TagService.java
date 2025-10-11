package com.ejercicioAerolinea.servicios;

import com.ejercicioAerolinea.api.dto.TagDTO;
import com.ejercicioAerolinea.entities.Tag;
import com.ejercicioAerolinea.mappers.TagMapper;
import com.ejercicioAerolinea.repositories.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    public TagDTO.TagResponse create(TagDTO.TagCreateRequest dto) {
        Tag t = tagMapper.toEntity(dto);
        tagRepository.save(t);
        return tagMapper.toResponse(t);
    }

    @Transactional(readOnly = true)
    public TagDTO.TagResponse getById(Long id) {
        return tagRepository.findById(id)
                .map(tagMapper::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
    }

    @Transactional(readOnly = true)
    public Page<TagDTO.TagResponse> list(Pageable pageable) {
        return tagRepository.findAll(pageable).map(tagMapper::toResponse);
    }

    public TagDTO.TagResponse update(Long id, TagDTO.TagUpdateRequest dto) {
        Tag t = tagRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        tagMapper.updateEntity(dto, t);
        return tagMapper.toResponse(t);
    }

    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new EntityNotFoundException("Tag not found");
        }
        tagRepository.deleteById(id);
    }
}
