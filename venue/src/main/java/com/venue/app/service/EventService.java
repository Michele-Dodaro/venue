package com.venue.app.service;

import com.venue.app.model.dto.EventDTORequest;
import com.venue.app.model.dto.EventDTOResponse;
import com.venue.app.model.entity.Event;
import com.venue.app.model.entity.EventLayout; // MODIFICA: importata entità EventLayout
import com.venue.app.repository.EventLayoutRepository; // MODIFICA: importato EventLayoutRepository
import com.venue.app.repository.EventRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final EventLayoutRepository eventLayoutRepository;

    public EventService(EventRepository eventRepository, EventLayoutRepository eventLayoutRepository) {
        this.eventRepository = eventRepository;
        this.eventLayoutRepository = eventLayoutRepository;
    }

    public EventDTOResponse createEvent(EventDTORequest eventDTORequest) {
        Event event = new Event();
        event.setName(eventDTORequest.getName());
        event.setDescription(eventDTORequest.getDescription());
        event.setDate(eventDTORequest.getDate() != null ? eventDTORequest.getDate() : LocalDateTime.now());
        event.setActive(eventDTORequest.getActive());
        event.setGenre(eventDTORequest.getGenre());

        if (eventDTORequest.getLayoutId() != null) {
            EventLayout layout = eventLayoutRepository.findById(eventDTORequest.getLayoutId())
                    .orElseThrow(() -> new RuntimeException("Layout not found with id: " + eventDTORequest.getLayoutId()));
            event.setLayout(layout);
        }

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
            event.setActive(eventDTORequest.getActive());
            event.setGenre(eventDTORequest.getGenre());

            if (eventDTORequest.getLayoutId() != null) {
                EventLayout layout = eventLayoutRepository.findById(eventDTORequest.getLayoutId())
                        .orElseThrow(() -> new RuntimeException("Layout not found with id: " + eventDTORequest.getLayoutId()));
                event.setLayout(layout);
            } else {
                event.setLayout(null);
            }

            Event updatedEvent = eventRepository.save(event);
            return EventDTOResponse.toDTO(updatedEvent);
        }
        throw new RuntimeException("Event not found with id: " + id);
    }

    @Transactional
    public void deleteEvent(Long id) {
        if (eventRepository.existsById(id)) {
            eventRepository.deleteById(id);
            System.out.println("Evento eliminato con successo: " + id);
        } else {
            System.out.println("Tentativo di eliminare evento inesistente: " + id);
        }
    }

    public Optional<EventDTOResponse> findById(Long id) {
        return eventRepository.findById(id)
                .map(EventDTOResponse::toDTO);
    }
}