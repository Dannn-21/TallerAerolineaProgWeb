package com.ejercicioAerolinea.repositories;

import com.ejercicioAerolinea.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findByPassenger_EmailIgnoreCaseOrderByCreatedAtDesc(String email, Pageable pageable);

    @EntityGraph(attributePaths = {"items","items.flight","passenger"})
    @Query("select b from Booking b where b.id = :id")
    Optional<Booking> fetchDetailById(@Param("id") Long id);
}