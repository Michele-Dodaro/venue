package com.venue.app.repository;

import com.venue.app.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByEventLayoutId(Long layoutId);

    @Query("SELECT t FROM Ticket t WHERE t.eventLayout.id IN (SELECT e.layout.id FROM Event e WHERE e.id = :eventId)")
    List<Ticket> findByEventId(@Param("eventId") Long eventId);
}