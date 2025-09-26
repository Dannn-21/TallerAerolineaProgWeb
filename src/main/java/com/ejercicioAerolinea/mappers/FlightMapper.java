package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.FlightDTO;
import com.ejercicioAerolinea.entities.Airline;
import com.ejercicioAerolinea.entities.Airport;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FlightMapper {

    public static Flight toEntity(FlightDTO.FlightCreateRequest dto,
                                  Airline airline,
                                  Airport origin,
                                  Airport destination,
                                  Set<Tag> tags) {
        Flight f = Flight.builder()
                .number(dto.number())
                .airline(airline)
                .origin(origin)
                .destination(destination)
                .departureTime(dto.departureTime())
                .arrivalTime(dto.arrivalTime())
                .build();

        if (tags != null) {
            tags.forEach(f::addTag); // helper mantiene ambos lados
        }
        return f;
    }

    public static void updateEntity(Flight f,
                                    FlightDTO.FlightUpdateRequest dto,
                                    Airline airline,        // puede ser null si no cambia
                                    Airport origin,
                                    Airport destination,
                                    Set<Tag> tags) {
        if (dto.number() != null) f.setNumber(dto.number());
        if (airline != null) f.setAirline(airline);
        if (origin != null) f.setOrigin(origin);
        if (destination != null) f.setDestination(destination);
        if (dto.departureTime() != null) f.setDepartureTime(dto.departureTime());
        if (dto.arrivalTime() != null) f.setArrivalTime(dto.arrivalTime());

        if (tags != null) {
            if (f.getTags() != null) {
                f.getTags().forEach(t -> t.getFlights().remove(f));
                f.getTags().clear();
            }
            tags.forEach(f::addTag);
        }
    }

    public static FlightDTO.FlightResponse toResponse(Flight f) {
        Airline a = f.getAirline();
        Airport o = f.getOrigin();
        Airport d = f.getDestination();

        FlightDTO.AirlineSummary airline = new FlightDTO.AirlineSummary(a.getCode(), a.getName());
        FlightDTO.AirportSummary origin = new FlightDTO.AirportSummary(o.getCode(), o.getName(), o.getCity());
        FlightDTO.AirportSummary dest   = new FlightDTO.AirportSummary(d.getCode(), d.getName(), d.getCity());

        List<String> tagNames = f.getTags() == null ? List.of()
                : f.getTags().stream().map(Tag::getName).sorted().collect(Collectors.toList());

        return new FlightDTO.FlightResponse(
                f.getId(), f.getNumber(),
                airline, origin, dest,
                f.getDepartureTime(), f.getArrivalTime(),
                tagNames
        );
    }
}
