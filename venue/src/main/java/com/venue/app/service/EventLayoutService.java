package com.venue.app.service;

import com.venue.app.model.dto.EventLayoutDTORequest;
import com.venue.app.model.dto.EventLayoutDTOResponse;
import com.venue.app.model.entity.Event;
import com.venue.app.model.entity.EventLayout;
import com.venue.app.repository.EventLayoutRepository;
import com.venue.app.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventLayoutService {

    private final EventLayoutRepository eventLayoutRepository;
    private final EventRepository eventRepository;

    public EventLayoutService(EventLayoutRepository eventLayoutRepository, EventRepository eventRepository) {
        this.eventLayoutRepository = eventLayoutRepository;
        this.eventRepository = eventRepository;
    }

    public EventLayoutDTOResponse createEventLayout(EventLayoutDTORequest request) {
        Optional<Event> eventOpt = eventRepository.findById(request.getEventId());
        if (eventOpt.isEmpty()) {
            throw new IllegalArgumentException("Event not found with ID: " + request.getEventId());
        }

        EventLayout layout = new EventLayout();
        layout.setConformation(request.getConformation());
        layout.setRowField(request.getRowField());
        layout.setNumber(request.getNumber());
        layout.setPrice1(request.getPrice1());
        layout.setPrice2(request.getPrice2());
        layout.setPrice3(request.getPrice3());
        layout.setEvent(eventOpt.get());

        EventLayout savedLayout = eventLayoutRepository.save(layout);
        return mapToResponse(savedLayout);
    }

    public List<EventLayoutDTOResponse> getAllEventLayouts() {
        return eventLayoutRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<EventLayoutDTOResponse> getEventLayoutById(Long id) {
        return eventLayoutRepository.findById(id)
                .map(this::mapToResponse);
    }

    public Optional<EventLayoutDTOResponse> updateEventLayout(Long id, EventLayoutDTORequest request) {
        Optional<EventLayout> existingLayoutOpt = eventLayoutRepository.findById(id);

        if (existingLayoutOpt.isPresent()) {
            EventLayout layout = existingLayoutOpt.get();
            layout.setConformation(request.getConformation());
            layout.setRowField(request.getRowField());
            layout.setNumber(request.getNumber());
            layout.setPrice1(request.getPrice1());
            layout.setPrice2(request.getPrice2());
            layout.setPrice3(request.getPrice3());

            if (!layout.getEvent().getId().equals(request.getEventId())) {
                Optional<Event> eventOpt = eventRepository.findById(request.getEventId());
                eventOpt.ifPresent(layout::setEvent);
            }

            EventLayout updatedLayout = eventLayoutRepository.save(layout);
            return Optional.of(mapToResponse(updatedLayout));
        }
        return Optional.empty();
    }

    public boolean deleteEventLayout(Long id) {
        if (eventLayoutRepository.existsById(id)) {
            eventLayoutRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private EventLayoutDTOResponse mapToResponse(EventLayout layout) {
        EventLayoutDTOResponse response = new EventLayoutDTOResponse();
        response.setId(layout.getId());
        response.setConformation(layout.getConformation());
        response.setRowField(layout.getRowField());
        response.setNumber(layout.getNumber());
        response.setPrice1(layout.getPrice1());
        response.setPrice2(layout.getPrice2());
        response.setPrice3(layout.getPrice3());
        response.setEventId(layout.getEvent() != null ? layout.getEvent().getId() : null);
        return response;
    }
}