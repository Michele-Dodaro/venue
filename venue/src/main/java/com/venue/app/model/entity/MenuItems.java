package com.venue.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
@Getter
@Setter
@Entity
@Table(name = "menu_items")
@Data @NoArgsConstructor @AllArgsConstructor
public class MenuItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String plate;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "original_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal originalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_categories_id")
    private MenuCategories menuCategory;
}