package com.venue.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "event_layout")
@Data @NoArgsConstructor @AllArgsConstructor
public class EventLayout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String conformation;

    @Column(name = "row", length = 50)
    private String rowField;

    @Column(nullable = false)
    private Integer number;

    @Column(name = "price1", nullable = false, precision = 10, scale = 2)
    private BigDecimal price1;

    @Column(name = "price2", precision = 10, scale = 2)
    private BigDecimal price2;

    @Column(name = "price3", precision = 10, scale = 2)
    private BigDecimal price3;

}