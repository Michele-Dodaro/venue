package com.venue.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.venue.app.model.entity.Event;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTOResponse {
    private Long id;
    private String image;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
    private Boolean active;
    private String genre;
    private Long layoutId;

    public static EventDTOResponse toDTO(Event event) {
        EventDTOResponse eventDTOResponse = new EventDTOResponse();
        eventDTOResponse.setId(event.getId());
        eventDTOResponse.setImage(event.getImage());
        eventDTOResponse.setName(event.getName());
        eventDTOResponse.setDescription(event.getDescription());
        eventDTOResponse.setDate(event.getDate());
        eventDTOResponse.setActive(event.getActive());
        eventDTOResponse.setGenre(event.getGenre());

        eventDTOResponse.setLayoutId(event.getLayout() != null ? event.getLayout().getId() : null);

        return eventDTOResponse;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public Long getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(Long layoutId) {
        this.layoutId = layoutId;
    }
}