package com.venue.app.model.dto;

import com.venue.app.model.entity.Event;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTO {
    private String name;
    private String description;
    private LocalDateTime date;

    public static EventDTO toDTO( Event event ) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName(event.getName());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setDate(event.getDate());
        return eventDTO;
    }


}
