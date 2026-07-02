package com.venue.app.model.dto;

import com.venue.app.model.entity.Event;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventDTOResponse {
    private String name;
    private String description;
    private LocalDateTime date;

    public static EventDTOResponse toDTO(Event event ) {
        EventDTOResponse eventDTOResponse = new EventDTOResponse();
        eventDTOResponse.setName(event.getName());
        eventDTOResponse.setDescription(event.getDescription());
        eventDTOResponse.setDate(event.getDate());
        return eventDTOResponse;
    }


}
