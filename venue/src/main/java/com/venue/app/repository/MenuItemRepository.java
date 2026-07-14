package com.venue.app.repository;

import com.venue.app.model.entity.MenuItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItems, Long> {

    @Query("SELECT m FROM MenuItems m WHERE  m.menuCategory.type = :type")
    List<MenuItems> findByMenuCategoryType(@Param("type") String type);

    @Query("SELECT m FROM MenuItems m WHERE m.id = :id AND m.menuCategory.type = :type")
    List<MenuItems> findByIdAndMenuCategoryType(@Param("id") Long id, @Param("type") String type);}