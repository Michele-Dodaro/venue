package com.venue.app.model.dto;

import com.venue.app.model.entity.Event;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTOResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private Boolean active;
    private String genre;

    public static EventDTOResponse toDTO(Event event ) {
        EventDTOResponse eventDTOResponse = new EventDTOResponse();
        eventDTOResponse.setId(event.getId());
        eventDTOResponse.setName(event.getName());
        eventDTOResponse.setDescription(event.getDescription());
        eventDTOResponse.setDate(event.getDate());
        eventDTOResponse.setActive(event.getActive());
        eventDTOResponse.setGenre(event.getGenre());
        return eventDTOResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}
