package com.venue.app.model.entity;

import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name = "menu_categories")
@Data @NoArgsConstructor @AllArgsConstructor
public class MenuCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String type;
}