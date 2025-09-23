package com.ejercicioAerolinea.repositories;

import com.ejercicioAerolinea.entities.BookingItem;
import com.ejercicioAerolinea.entities.SeatInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface BookingItemRepository extends JpaRepository<BookingItem, Long> {

    List<BookingItem> findByBooking_IdOrderBySegmentOrderAsc(Long bookingId);

    @Query("select coalesce(sum(bi.price), 0) from BookingItem bi where bi.booking.id = :bookingId")
    BigDecimal calculateTotal(@Param("bookingId") Long bookingId);

    long countByFlight_IdAndCabin(Long flightId, SeatInventory.Cabin cabin);
}
