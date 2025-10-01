package com.ejercicioAerolinea.repository;

import com.ejercicioAerolinea.entities.*;
import com.ejercicioAerolinea.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static jdk.internal.org.jline.reader.impl.LineReaderImpl.CompletionType.List;
import static org.assertj.core.api.Assertions.assertThat;

class BookingItemRepositoryTest extends BaseRepositoryTest {

    @Autowired
    BookingItemRepository bookingItemRepository;
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

    @Test
    void listarItemsYCalcularTotal_basico() {
        Passenger p = new Passenger();
        p.setFullName("Ana");
        p.setEmail("ana@example.com");
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

        Flight f1 = new Flight();
        f1.setNumber("AV300");
        f1.setAirline(av);
        f1.setOrigin(bog);
        f1.setDestination(mad);
        f1.setDepartureTime(OffsetDateTime.now().plusDays(5));
        f1.setArrivalTime(OffsetDateTime.now().plusDays(5).plusHours(10));
        flightRepository.save(f1);

        Flight f2 = new Flight();
        f2.setNumber("AV301");
        f2.setAirline(av);
        f2.setOrigin(mad);
        f2.setDestination(bog);
        f2.setDepartureTime(OffsetDateTime.now().plusDays(10));
        f2.setArrivalTime(OffsetDateTime.now().plusDays(10).plusHours(10));
        flightRepository.save(f2);

        Booking b = new Booking();
        b.setPassenger(p);
        b.setCreatedAt(OffsetDateTime.now());
        bookingRepository.save(b);

        bookingItemRepository.saveAll(List.of(
                crearItem(b, f1, "200.00", 1),
                crearItem(b, f2, "250.00", 2)
        ));

        var items = bookingItemRepository.findByBooking_IdOrderBySegmentOrderAsc(b.getId());
        assertThat(items).hasSize(2);

        var total = bookingItemRepository.calculateTotal(b.getId());
        assertThat(total).isEqualByComparingTo("450.00");
    }

    private BookingItem crearItem(Booking b, Flight f, String price, int order) {
        BookingItem it = new BookingItem();
        it.setBooking(b);
        it.setFlight(f);
        it.setCabin(SeatInventory.Cabin.ECONOMY);
        it.setPrice(new BigDecimal(price));
        it.setSegmentOrder(order);
        return it;
    }
}

