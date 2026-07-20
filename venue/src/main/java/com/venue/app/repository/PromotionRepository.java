package com.venue.app.repository;

import com.venue.app.model.entity.Promotion;
import com.venue.app.model.entity.PromotionItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
