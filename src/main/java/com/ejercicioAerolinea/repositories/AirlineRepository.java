package com.ejercicioAerolinea.repositories;

import com.ejercicioAerolinea.entities.Airline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AirlineRepository extends JpaRepository<Airline, Long> {

    Optional<Airline> findByCode(String code);
}
