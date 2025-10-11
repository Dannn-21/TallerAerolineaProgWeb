package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.FlightDTO;
import com.ejercicioAerolinea.entities.Airline;
import com.ejercicioAerolinea.entities.Airport;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.Tag;
import org.mapstruct.*;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface FlightMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "airline", expression = "java(airline)")
    @Mapping(target = "origin", expression = "java(origin)")
    @Mapping(target = "destination", expression = "java(destination)")
    @Mapping(target = "tags", expression = "java(tags)")
    Flight toEntity(FlightDTO.FlightCreateRequest dto, Airline airline, Airport origin, Airport destination, Set<Tag> tags);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "airline", expression = "java(airline)")
    @Mapping(target = "origin", expression = "java(origin)")
    @Mapping(target = "destination", expression = "java(destination)")
    @Mapping(target = "tags", expression = "java(tags)")
    void updateEntity(FlightDTO.FlightUpdateRequest dto, @MappingTarget Flight entity,
                      Airline airline, Airport origin, Airport destination, Set<Tag> tags);

    @Mapping(target = "airline", expression = "java(new FlightDTO.AirlineSummary(f.getAirline().getCode(), f.getAirline().getName()))")
    @Mapping(target = "origin", expression = "java(new FlightDTO.AirportSummary(f.getOrigin().getCode(), f.getOrigin().getName(), f.getOrigin().getCity()))")
    @Mapping(target = "destination", expression = "java(new FlightDTO.AirportSummary(f.getDestination().getCode(), f.getDestination().getName(), f.getDestination().getCity()))")
    @Mapping(target = "tags", expression = "java(f.getTags()==null? java.util.List.of() : f.getTags().stream().map(Tag::getName).sorted().toList())")
    FlightDTO.FlightResponse toResponse(Flight f);
}