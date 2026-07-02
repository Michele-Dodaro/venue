package com.venue.app.controller;

import com.venue.app.model.dto.EventDTORequest;
import com.venue.app.model.dto.EventDTOResponse;
import com.venue.app.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventDTOResponse> createEvent(@RequestBody EventDTORequest eventDTORequest) {
        EventDTOResponse createdEvent = eventService.createEvent(eventDTORequest);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EventDTOResponse>> getAllEvents() {
        List<EventDTOResponse> events = eventService.findAll();
        return new ResponseEntity<>(events, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<EventDTOResponse> updateEvent(@PathVariable Long id, @RequestBody EventDTORequest eventDTORequest) {
        try {
            EventDTOResponse updatedEvent = eventService.updateEvent(id, eventDTORequest);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}