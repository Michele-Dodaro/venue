package com.venue.app.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private String name;
    private String description;
    private LocalDateTime date;
}
