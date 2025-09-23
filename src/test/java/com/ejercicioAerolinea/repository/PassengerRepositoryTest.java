package com.ejercicioAerolinea.repository;

import com.ejercicioAerolinea.entities.Passenger;
import com.ejercicioAerolinea.entities.PassengerProfile;
import com.ejercicioAerolinea.repositories.PassengerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class PassengerRepositoryTest extends BaseRepositoryTest {

    @Autowired
    PassengerRepository passengerRepository;

    @Test
    void debeEncontrarPassengerPorEmailIgnoreCase() {
        Passenger p = new Passenger();
        p.setFullName("Ana");
        p.setEmail("ANA@example.com");
        passengerRepository.save(p);

        Optional<Passenger> found = passengerRepository.findByEmailIgnoreCase("ana@example.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("ana@example.com");
    }

    @Test
    void fetchWithProfileByEmail_traePerfil() {
        Passenger p = new Passenger();
        p.setFullName("Luis");
        p.setEmail("luis@example.com");

        PassengerProfile prof = new PassengerProfile();
        prof.setPhone("123");
        prof.setCountryCode("CO");
        p.setProfile(prof);

        passengerRepository.save(p);

        Passenger loaded = passengerRepository.fetchWithProfileByEmail("LUIS@EXAMPLE.COM").orElseThrow();
        assertThat(loaded.getProfile()).isNotNull();
        assertThat(loaded.getProfile().getPhone()).isEqualTo("123");
    }
}
