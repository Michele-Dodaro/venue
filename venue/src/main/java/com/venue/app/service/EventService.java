package com.venue.app.service;

import com.venue.app.model.dto.EventDTO;
import com.venue.app.model.entity.Event;
import com.venue.app.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
}
