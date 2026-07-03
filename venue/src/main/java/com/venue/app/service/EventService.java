package com.venue.app.service;

import com.venue.app.model.dto.EventDTORequest;
import com.venue.app.model.dto.EventDTOResponse;
import com.venue.app.model.entity.Event;
import com.venue.app.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventDTOResponse createEvent(EventDTORequest eventDTORequest) {
        Event event = new Event();
        event.setName(eventDTORequest.getName());
        event.setDescription(eventDTORequest.getDescription());
        event.setDate(eventDTORequest.getDate() != null ? eventDTORequest.getDate() : LocalDateTime.now());
        Event savedEvent = eventRepository.save(event);
        return EventDTOResponse.toDTO(savedEvent);
    }

    public List<EventDTOResponse> findAll() {
        return eventRepository.findAll().stream()
                .map(EventDTOResponse::toDTO)
                .collect(Collectors.toList());
    }

    public EventDTOResponse updateEvent(Long id, EventDTORequest eventDTORequest) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setName(eventDTORequest.getName());
            event.setDescription(eventDTORequest.getDescription());
            event.setDate(eventDTORequest.getDate());
            Event updatedEvent = eventRepository.save(event);
            return EventDTOResponse.toDTO(updatedEvent);
        }
        throw new RuntimeException("Event not found with id: " + id);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
