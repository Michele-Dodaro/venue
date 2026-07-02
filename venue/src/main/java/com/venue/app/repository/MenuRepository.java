package com.venue.app.repository;

import com.venue.app.model.entity.MenuCategories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MenuRepository extends JpaRepository<MenuCategories, Long> {
    Optional<MenuCategories> findByType(String type);
    boolean existsByType(String type);
    void deleteByType(String type);
}