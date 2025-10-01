package com.ejercicioAerolinea.repository;

import com.ejercicioAerolinea.entities.Airline;
import com.ejercicioAerolinea.entities.Airport;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.SeatInventory;
import com.ejercicioAerolinea.repositories.AirlineRepository;
import com.ejercicioAerolinea.repositories.AirportRepository;
import com.ejercicioAerolinea.repositories.FlightRepository;
import com.ejercicioAerolinea.repositories.SeatInventoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class SeatInventoryRepositoryTest extends BaseRepositoryTest {

    @Autowired
    SeatInventoryRepository seatInventoryRepository;
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    AirlineRepository airlineRepository;
    @Autowired
    AirportRepository airportRepository;

    @Test
    void findByFlightIdAndCabin_y_existsBasico() {
        Airline av = new Airline();
        av.setCode("AV");
        av.setName("Avianca");
        airlineRepository.save(av);

        Airport bog = new Airport();
        bog.setCode("BOG");
        bog.setName("El Dorado");
        bog.setCity("Bogotá");
        airportRepository.save(bog);

        Airport mad = new Airport();
        mad.setCode("MAD");
        mad.setName("Barajas");
        mad.setCity("Madrid");
        airportRepository.save(mad);

        Flight f = new Flight();
        f.setNumber("AV050");
        f.setAirline(av);
        f.setOrigin(bog);
        f.setDestination(mad);
        f.setDepartureTime(OffsetDateTime.now().plusDays(1));
        f.setArrivalTime(OffsetDateTime.now().plusDays(1).plusHours(10));
        flightRepository.save(f);

        SeatInventory si = new SeatInventory();
        si.setFlight(f);
        si.setCabin(SeatInventory.Cabin.ECONOMY);
        si.setTotalSeats(100);
        si.setAvailableSeats(20);
        seatInventoryRepository.saveAndFlush(si);

        assertThat(seatInventoryRepository.findByFlight_IdAndCabin(f.getId(), SeatInventory.Cabin.ECONOMY)).isPresent();
        assertThat(seatInventoryRepository.existsByFlight_IdAndCabinAndAvailableSeatsGreaterThanEqual(
                f.getId(), SeatInventory.Cabin.ECONOMY, 10)).isTrue();
    }

    @Test
    void validateSeats_noPermiteValoresInvalidos() {
        Airline av = new Airline();
        av.setCode("AV");
        av.setName("Avianca");
        airlineRepository.save(av);

        Airport bog = new Airport();
        bog.setCode("BOG");
        bog.setName("El Dorado");
        bog.setCity("Bogotá");
        airportRepository.save(bog);

        Airport mad = new Airport();
        mad.setCode("MAD");
        mad.setName("Barajas");
        mad.setCity("Madrid");
        airportRepository.save(mad);

        Flight f = new Flight();
        f.setNumber("AV060");
        f.setAirline(av);
        f.setOrigin(bog);
        f.setDestination(mad);
        f.setDepartureTime(OffsetDateTime.now().plusDays(1));
        f.setArrivalTime(OffsetDateTime.now().plusDays(1).plusHours(10));
        flightRepository.save(f);

        SeatInventory si = new SeatInventory();
        si.setFlight(f);
        si.setCabin(SeatInventory.Cabin.BUSINESS);
        si.setTotalSeats(10);
        si.setAvailableSeats(20); // inválido

        assertThatThrownBy(() -> seatInventoryRepository.saveAndFlush(si))
                .isInstanceOf(Exception.class);
    }
}

