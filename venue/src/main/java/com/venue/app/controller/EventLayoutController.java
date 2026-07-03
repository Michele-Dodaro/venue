package com.venue.app.controller;

import com.venue.app.model.dto.EventLayoutDTORequest;
import com.venue.app.model.dto.EventLayoutDTOResponse;
import com.venue.app.service.EventLayoutService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/event-layouts")
public class EventLayoutController {

    private final EventLayoutService eventLayoutService;

    public EventLayoutController(EventLayoutService eventLayoutService) {
        this.eventLayoutService = eventLayoutService;
    }

    @PostMapping
    public ResponseEntity<EventLayoutDTOResponse> create(@RequestBody EventLayoutDTORequest request) {
        try {
            EventLayoutDTOResponse response = eventLayoutService.createEventLayout(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EventLayoutDTOResponse>> getAll() {
        return ResponseEntity.ok(eventLayoutService.getAllEventLayouts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventLayoutDTOResponse> getById(@PathVariable Long id) {
        Optional<EventLayoutDTOResponse> response = eventLayoutService.getEventLayoutById(id);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventLayoutDTOResponse> update(@PathVariable Long id, @RequestBody EventLayoutDTORequest request) {
        Optional<EventLayoutDTOResponse> response = eventLayoutService.updateEventLayout(id, request);
        return response.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (eventLayoutService.deleteEventLayout(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}