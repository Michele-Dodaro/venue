package com.venue.app.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "promotion_items")
@Data @NoArgsConstructor @AllArgsConstructor
public class PromotionItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_items_id")
    private MenuItems menuItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_layout_id")
    private EventLayout eventLayout;
}
