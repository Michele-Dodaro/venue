package com.venue.app.repository;

import com.venue.app.model.entity.PromotionItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromotionItemsRepository extends JpaRepository<PromotionItems, Long> {

    List<PromotionItems> findByMenuItemId(Long menuItemId);

}