package com.ejercicioAerolinea.repositories;

import com.ejercicioAerolinea.entities.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatInventoryRepository extends JpaRepository<SeatInventory, Long> {

    Optional<SeatInventory> findByFlight_IdAndCabin(Long flightId, SeatInventory.Cabin cabin);

    boolean existsByFlight_IdAndCabinAndAvailableSeatsGreaterThanEqual(
            Long flightId,
            SeatInventory.Cabin cabin,
            Integer min
    );
}
