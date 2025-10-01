package com.ejercicioAerolinea.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "airlines")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "airline",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<Flight> flights = new ArrayList<>();

   public void addFlight(Flight flight){
       if(this.flights == null){
           this.flights = new ArrayList<>();
       }
       this.flights.add(flight);
       flight.setAirline(this);    ;
   }
}
