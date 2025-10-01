package com.ejercicioAerolinea.repository;

import com.ejercicioAerolinea.entities.*;
import com.ejercicioAerolinea.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookingRepositoryTest extends BaseRepositoryTest {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    PassengerRepository passengerRepository;
    @Autowired
    FlightRepository flightRepository;
    @Autowired
    AirlineRepository airlineRepository;
    @Autowired
    AirportRepository airportRepository;
    @Autowired
    BookingItemRepository bookingItemRepository;

    @Test
    void listarBookingsDeUnPasajero_basico() {
        Passenger p = new Passenger();
        p.setFullName("Ana");
        p.setEmail("ANA@example.com");
        passengerRepository.save(p);

        Booking b1 = new Booking();
        b1.setPassenger(p);
        b1.setCreatedAt(OffsetDateTime.now().minusDays(1));
        bookingRepository.save(b1);

        Booking b2 = new Booking();
        b2.setPassenger(p);
        b2.setCreatedAt(OffsetDateTime.now());
        bookingRepository.save(b2);

        var page = bookingRepository
                .findByPassenger_EmailIgnoreCaseOrderByCreatedAtDesc("ana@example.com", PageRequest.of(0, 10));

        assertThat(page.getTotalElements()).isEqualTo(2);
    }

    @Test
    void fetchDetailById_traeItemsYFlight() {
        Passenger p = new Passenger();
        p.setFullName("Luis");
        p.setEmail("luis@example.com");
        passengerRepository.save(p);

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
        f.setNumber("AV200");
        f.setAirline(av);
        f.setOrigin(bog);
        f.setDestination(mad);
        f.setDepartureTime(OffsetDateTime.now().plusDays(3));
        f.setArrivalTime(OffsetDateTime.now().plusDays(3).plusHours(10));
        flightRepository.save(f);

        Booking bk = new Booking();
        bk.setPassenger(p);
        bk.setCreatedAt(OffsetDateTime.now());
        bookingRepository.save(bk);

        BookingItem it = new BookingItem();
        it.setBooking(bk);
        it.setFlight(f);
        it.setCabin(SeatInventory.Cabin.ECONOMY);
        it.setPrice(new BigDecimal("350.00"));
        it.setSegmentOrder(1);
        bookingItemRepository.save(it);

        var loaded = bookingRepository.fetchDetailById(bk.getId()).orElse(null);
        assertThat(loaded).isNotNull();
        assertThat(loaded.getItems());
    }
}

