package com.venue.app.repository;

import com.venue.app.model.entity.PromotionItems;
import com.venue.app.model.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PromotionItemsRepository extends JpaRepository<PromotionItems, Long> {

    @Query("SELECT p FROM Promotion p " +
           "WHERE (p.expiresIn IS NULL OR p.expiresIn > CURRENT_TIMESTAMP) " +
           "ORDER BY p.id DESC")
    List<Promotion> findAllActivePromotions();

    @Query("SELECT p FROM Promotion p " +
           "INNER JOIN PromotionItems pi ON pi.promotion.id = p.id " +
           "WHERE pi.menuItem.id = :menuItemId " +
           "AND (p.expiresIn IS NULL OR p.expiresIn > CURRENT_TIMESTAMP) " +
           "ORDER BY p.id DESC " +
           "LIMIT 1")
    Promotion findActivePromotionByMenuItemId(@Param("menuItemId") Long menuItemId);

    @Query("SELECT p FROM Promotion p " +
           "INNER JOIN PromotionItems pi ON pi.promotion.id = p.id " +
           "WHERE pi.eventLayout.id = :layoutId " +
           "AND (p.expiresIn IS NULL OR p.expiresIn > CURRENT_TIMESTAMP) " +
           "ORDER BY p.id DESC " +
           "LIMIT 1")
    Promotion findActivePromotionByEventLayoutId(@Param("layoutId") Long layoutId);
}