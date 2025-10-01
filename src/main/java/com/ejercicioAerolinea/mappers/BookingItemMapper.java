package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.BookingItemDTO;
import com.ejercicioAerolinea.entities.Booking;
import com.ejercicioAerolinea.entities.BookingItem;
import com.ejercicioAerolinea.entities.Flight;
import com.ejercicioAerolinea.entities.SeatInventory;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface BookingItemMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "booking", expression = "java(booking)")
    @Mapping(target = "flight", expression = "java(flight)")
    @Mapping(target = "cabin", qualifiedByName = "toCabin")
    BookingItem toEntity(BookingItemDTO.BookingItemCreateRequest dto, Booking booking, Flight flight);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "flight", expression = "java(flight)")
    @Mapping(target = "cabin", qualifiedByName = "toCabinIfNotNull")
    void updateEntity(BookingItemDTO.BookingItemUpdateRequest dto, @MappingTarget BookingItem entity, Flight flight);

    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "flightNumber", source = "flight.number")
    @Mapping(target = "cabin", qualifiedByName = "fromCabin")
    BookingItemDTO.BookingItemResponse toResponse(BookingItem entity);

    @Named("toCabin") default SeatInventory.Cabin toCabin(String s){ return s==null?null:SeatInventory.Cabin.valueOf(s); }
    @Named("toCabinIfNotNull") default SeatInventory.Cabin toCabinIfNotNull(String s){ return s==null?null:SeatInventory.Cabin.valueOf(s); }
    @Named("fromCabin") default String fromCabin(SeatInventory.Cabin c){ return c==null?null:c.name(); }

    @AfterMapping
    default void link(@MappingTarget BookingItem item){
        if (item.getBooking()!=null) item.getBooking().getItems().add(item);
    }
}
