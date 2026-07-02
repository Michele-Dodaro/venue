package com.venue.app.service;

import com.venue.app.model.dto.EventDTO;
import com.venue.app.model.entity.Event;
import com.venue.app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDescription(eventDTO.getDescription());
        event.setDate(eventDTO.getDate() != null ? eventDTO.getDate() : LocalDateTime.now());
        Event savedEvent = eventRepository.save(event);
        return EventDTO.toDTO(savedEvent);
    }

    public List<EventDTO> findAll() {
        return eventRepository.findAll().stream()
                .map(EventDTO::toDTO)
                .collect(Collectors.toList());
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setName(eventDTO.getName());
            event.setDescription(eventDTO.getDescription());
            event.setDate(eventDTO.getDate());
            Event updatedEvent = eventRepository.save(event);
            return EventDTO.toDTO(updatedEvent);
        }
        return null;
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }
}
