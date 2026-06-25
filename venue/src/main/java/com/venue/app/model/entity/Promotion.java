package com.venue.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "promotion")
@Data @NoArgsConstructor @AllArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "promotion_table", precision = 10, scale = 2)
    private BigDecimal promotionTable;

    @Column(name = "promotion_price", precision = 10, scale = 2)
    private BigDecimal promotionPrice;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(name = "expires_in", insertable = false, updatable = false)
    private OffsetDateTime expiresIn;
}