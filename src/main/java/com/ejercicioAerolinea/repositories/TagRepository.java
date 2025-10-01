package com.ejercicioAerolinea.repositories;

import com.ejercicioAerolinea.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findByNameIgnoreCase(String name);

    List<Tag> findByNameIn(Collection<String> names);
}
