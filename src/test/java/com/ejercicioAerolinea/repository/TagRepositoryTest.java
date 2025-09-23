package com.ejercicioAerolinea.repository;

import com.ejercicioAerolinea.repositories.TagRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class TagRepositoryTest extends BaseRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Test
    void debeEncontrarPorNombreIgnoreCase() {
        Tag t = new Tag();
        t.setName("promo");
        tagRepository.save(t);

        var found = tagRepository.findByNameIgnoreCase("PROMO");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("promo");
    }
}

