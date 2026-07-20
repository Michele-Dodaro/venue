package com.venue.app.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.venue.app.config.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

public class EventDTORequest {

    private String image;
    private String name;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss[.SSS]")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime date;
    private Boolean active;
    private String genre;
    private Long layoutId;

    public EventDTORequest() {}

    public EventDTORequest(String image, String name, String description, LocalDateTime date, String genre, Boolean active, Long layoutId) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.date = date;
        this.genre = genre;
        this.active = active;
        this.layoutId = layoutId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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