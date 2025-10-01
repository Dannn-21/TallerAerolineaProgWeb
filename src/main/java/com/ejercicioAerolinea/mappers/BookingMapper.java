package com.ejercicioAerolinea.mappers;

import com.ejercicioAerolinea.api.dto.BookingDTO;
import com.ejercicioAerolinea.entities.Booking;
import com.ejercicioAerolinea.entities.BookingItem;
import com.ejercicioAerolinea.entities.Passenger;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookingItemMapper.class})
public interface BookingMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "passenger", expression = "java(passenger)")
    @Mapping(target = "createdAt", expression = "java(java.time.OffsetDateTime.now())")
    @Mapping(target = "items", expression = "java(items)")
    Booking toEntity(BookingDTO.BookingCreateRequest dto, Passenger passenger, List<BookingItem> items);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "passenger", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(BookingDTO.BookingUpdateRequest dto, @MappingTarget Booking entity);

    @Mapping(target = "passengerEmail", source = "passenger.email")
    BookingDTO.BookingResponse toResponse(Booking entity);

    @AfterMapping
    default void link(@MappingTarget Booking b){
        if (b.getItems()!=null) b.getItems().forEach(it -> it.setBooking(b));
    }
}

