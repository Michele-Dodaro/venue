package com.venue.app.service;

import com.venue.app.model.dto.EventDTORequest;
import com.venue.app.model.dto.EventDTOResponse;
import com.venue.app.model.entity.Event;
import com.venue.app.model.entity.EventLayout;
import com.venue.app.repository.EventLayoutRepository;
import com.venue.app.repository.EventRepository;
import com.venue.app.util.UrlUtil;
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

    // URL immagine di default
    private static final String DEFAULT_IMAGE = "https://via.placeholder.com/800x400?text=Event+Image";

    public EventService(EventRepository eventRepository, EventLayoutRepository eventLayoutRepository) {
        this.eventRepository = eventRepository;
        this.eventLayoutRepository = eventLayoutRepository;
    }

    public EventDTOResponse createEvent(EventDTORequest eventDTORequest) {
        Event event = new Event();

        // L'immagine è opzionale: valida e sanitizza l'URL
        String imageUrl = eventDTORequest.getImage();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            // Usa la URL fornita se valida
            String validatedUrl = UrlUtil.validateAndSanitizeUrl(imageUrl);
            if (validatedUrl != null) {
                event.setImage(validatedUrl);
                System.out.println("✓ Immagine valida caricata: " + validatedUrl);
            } else {
                System.out.println("✗ URL immagine non valido: " + imageUrl + " - Usando default");
                event.setImage(DEFAULT_IMAGE);
            }
        } else {
            System.out.println("✓ Nessuna immagine fornita - Usando default");
            event.setImage(DEFAULT_IMAGE);
        }

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
                .map(event -> {
                    EventDTOResponse dto = EventDTOResponse.toDTO(event);
                    // Valida e sanitizza ogni immagine
                    String image = dto.getImage();
                    if (image == null || image.isEmpty()) {
                        System.out.println("⚠ Evento " + dto.getId() + " senza immagine - Usando default");
                        dto.setImage(DEFAULT_IMAGE);
                    } else {
                        // Valida l'URL esistente
                        String validatedUrl = UrlUtil.validateAndSanitizeUrl(image);
                        if (validatedUrl != null) {
                            dto.setImage(validatedUrl);
                        } else {
                            System.out.println("⚠ URL non valido per evento " + dto.getId() + ": " + image);
                            dto.setImage(DEFAULT_IMAGE);
                        }
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public EventDTOResponse updateEvent(Long id, EventDTORequest eventDTORequest) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();

            // Aggiorna solo i campi se forniti
            if (eventDTORequest.getImage() != null && !eventDTORequest.getImage().isEmpty()) {
                String validatedUrl = UrlUtil.validateAndSanitizeUrl(eventDTORequest.getImage());
                if (validatedUrl != null) {
                    event.setImage(validatedUrl);
                    System.out.println("✓ Immagine aggiornata: " + validatedUrl);
                } else {
                    System.out.println("✗ URL immagine non valido durante update: " + eventDTORequest.getImage());
                }
            }
            if (eventDTORequest.getName() != null && !eventDTORequest.getName().isEmpty()) {
                event.setName(eventDTORequest.getName());
            }
            if (eventDTORequest.getDescription() != null && !eventDTORequest.getDescription().isEmpty()) {
                event.setDescription(eventDTORequest.getDescription());
            }
            if (eventDTORequest.getDate() != null) {
                event.setDate(eventDTORequest.getDate());
            }
            if (eventDTORequest.getActive() != null) {
                event.setActive(eventDTORequest.getActive());
            }
            if (eventDTORequest.getGenre() != null && !eventDTORequest.getGenre().isEmpty()) {
                event.setGenre(eventDTORequest.getGenre());
            }
            if (eventDTORequest.getLayoutId() != null) {
                EventLayout layout = eventLayoutRepository.findById(eventDTORequest.getLayoutId())
                        .orElseThrow(() -> new RuntimeException("Layout not found with id: " + eventDTORequest.getLayoutId()));
                event.setLayout(layout);
            }

            Event updatedEvent = eventRepository.save(event);
            EventDTOResponse dto = EventDTOResponse.toDTO(updatedEvent);
            // Valida l'immagine nella risposta
            String image = dto.getImage();
            if (image == null || image.isEmpty()) {
                dto.setImage(DEFAULT_IMAGE);
            } else {
                String validatedUrl = UrlUtil.validateAndSanitizeUrl(image);
                if (validatedUrl != null) {
                    dto.setImage(validatedUrl);
                } else {
                    dto.setImage(DEFAULT_IMAGE);
                }
            }
            return dto;
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
                .map(event -> {
                    EventDTOResponse dto = EventDTOResponse.toDTO(event);
                    // Valida e sanitizza l'immagine
                    String image = dto.getImage();
                    if (image == null || image.isEmpty()) {
                        System.out.println("⚠ Evento " + id + " senza immagine - Usando default");
                        dto.setImage(DEFAULT_IMAGE);
                    } else {
                        String validatedUrl = UrlUtil.validateAndSanitizeUrl(image);
                        if (validatedUrl != null) {
                            dto.setImage(validatedUrl);
                        } else {
                            System.out.println("⚠ URL non valido per evento " + id + ": " + image);
                            dto.setImage(DEFAULT_IMAGE);
                        }
                    }
                    return dto;
                });
    }
    public List<EventDTOResponse> searchEvents(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return findAll();
        }
        String lowerKeyword = keyword.toLowerCase();

        return eventRepository.findAll().stream()
                .filter(event ->
                        (event.getName() != null && event.getName().toLowerCase().contains(lowerKeyword)) ||
                                (event.getDescription() != null && event.getDescription().toLowerCase().contains(lowerKeyword)) ||
                                (event.getGenre() != null && event.getGenre().toLowerCase().contains(lowerKeyword))
                )
                .map(event -> {
                    EventDTOResponse dto = EventDTOResponse.toDTO(event);
                    String image = dto.getImage();
                    if (image == null || image.isEmpty() || UrlUtil.validateAndSanitizeUrl(image) == null) {
                        dto.setImage(DEFAULT_IMAGE);
                    } else {
                        dto.setImage(UrlUtil.validateAndSanitizeUrl(image));
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }
}