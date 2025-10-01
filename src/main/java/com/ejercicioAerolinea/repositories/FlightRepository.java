package com.ejercicioAerolinea.repositories;

import com.ejercicioAerolinea.entities.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findByAirline_NameIgnoreCase(String airlineName);


    Page<Flight> findByOrigin_CodeIgnoreCaseAndDestination_CodeIgnoreCaseAndDepartureTimeBetween(
            String originCode,
            String destinationCode,
            OffsetDateTime from,
            OffsetDateTime to,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"airline","origin","destination","tags"})
    @Query("""
           select f from Flight f
           where (:origin is null or lower(f.origin.code) = lower(:origin))
             and (:dest is null or lower(f.destination.code) = lower(:dest))
             and f.departureTime between :from and :to
           """)
    List<Flight> searchFlightsWithFilters(@Param("origin") String origin,
                                          @Param("dest") String dest,
                                          @Param("from") OffsetDateTime from,
                                          @Param("to") OffsetDateTime to);

    @Query(value = """
            select f.* from flights f
            join flight_tags ft on f.id = ft.flight_id
            join tags t on t.id = ft.tag_id
            where lower(t.name) in (:tagNames)
            group by f.id
            having count(distinct t.name) = :requiredCount
            """,
            nativeQuery = true)
    List<Flight> findByAllTags(@Param("tagNames") Collection<String> tagNames,
                               @Param("requiredCount") long requiredCount);
}
