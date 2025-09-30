package com.ejercicioAerolinea.repositories;

import com.ejercicioAerolinea.entities.PassengerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerProfileRepository extends JpaRepository <PassengerProfile, Long>{
}
