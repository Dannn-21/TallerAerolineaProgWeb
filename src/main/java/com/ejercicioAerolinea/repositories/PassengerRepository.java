package com.ejercicioAerolinea.repositories;

import com.ejercicioAerolinea.entities.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {


    Optional<Passenger> findByEmailIgnoreCase(String email);

    @Query("""
           select p
           from Passenger p
           left join fetch p.profile
           where lower(p.email) = lower(:email)
           """)
    Optional<Passenger> fetchWithProfileByEmail(@Param("email") String email);
}
