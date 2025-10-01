package com.ejercicioAerolinea.repository;


import com.ejercicioAerolinea.entities.Airport;
import com.ejercicioAerolinea.repositories.AirportRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AirportRepositoryTest extends BaseRepositoryTest {

    @Autowired
    AirportRepository airportRepository;

    @Test
    void debeEncontrarPorCode() {
        Airport ap = new Airport();
        ap.setCode("BOG");
        ap.setName("El Dorado");
        ap.setCity("Bogotá");
        airportRepository.save(ap);

        var found = airportRepository.findByCode("BOG");
        assertThat(found).isPresent();
        assertThat(found.get().getCity()).isEqualTo("Bogotá");
    }
}
