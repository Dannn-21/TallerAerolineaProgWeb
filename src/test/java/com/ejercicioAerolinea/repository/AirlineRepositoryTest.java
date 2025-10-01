package com.ejercicioAerolinea.repository;



import com.ejercicioAerolinea.entities.Airline;
import com.ejercicioAerolinea.repositories.AirlineRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class AirlineRepositoryTest extends BaseRepositoryTest {

    @Autowired
    AirlineRepository airlineRepository;

    @Test
    void debeEncontrarPorCode() {
        Airline a = new Airline();
        a.setCode("AV");
        a.setName("Avianca");
        airlineRepository.save(a);

        var found = airlineRepository.findByCode("AV");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Avianca");
    }
}
