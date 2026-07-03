package com.venue.app.repository;

import com.venue.app.model.entity.EventLayout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventLayoutRepository extends JpaRepository<EventLayout, Long> {
}