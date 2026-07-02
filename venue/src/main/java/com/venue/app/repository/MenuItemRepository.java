package com.venue.app.repository;

import com.venue.app.model.entity.MenuItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItems, Long> {
    List<MenuItems> findByMenuCategoryType(String type);
    Optional<MenuItems> findByIdAndMenuCategoryType(Long id, String type);
}