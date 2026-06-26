package com.venue.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name = "reservation_items")
@Data @NoArgsConstructor @AllArgsConstructor
public class ReservationItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private Reservations reservation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_layout_id", nullable = false)
    private EventLayout eventLayout;
}