package com.ejercicioAerolinea.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "seat_inventory")
public class Seatinventory {
    public enum Cabin { ECONOMY, PREMIUM_ECONOMY, BUSINESS, FIRST }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private Cabin cabin;

    @Column(nullable = false) private Integer totalSeats;
    @Column(nullable = false) private Integer availableSeats;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;
}
