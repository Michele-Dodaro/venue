package com.venue.app.service;

import com.venue.app.model.dto.TicketDTORequest;
import com.venue.app.model.dto.TicketDTOResponse;
import com.venue.app.model.entity.EventLayout;
import com.venue.app.model.entity.Ticket;
import com.venue.app.repository.EventLayoutRepository;
import com.venue.app.repository.TicketRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final EventLayoutRepository eventLayoutRepository;

    public TicketService(TicketRepository ticketRepository, EventLayoutRepository eventLayoutRepository) {
        this.ticketRepository = ticketRepository;
        this.eventLayoutRepository = eventLayoutRepository;
    }

    public TicketDTOResponse createTicket(TicketDTORequest request) {
        EventLayout eventLayout = eventLayoutRepository.findById(request.getLayoutId())
                .orElseThrow(() -> new IllegalArgumentException("Layout non trovato: " + request.getLayoutId()));

        Ticket ticket = new Ticket();
        ticket.setRowField(request.getRowField());
        ticket.setColumnField(request.getColumnField());
        ticket.setEventLayout(eventLayout);
        ticket.setAvailable(request.isAvailable());

        @SuppressWarnings({"rawtypes", "unchecked"})
        Ticket saved = (Ticket) ((org.springframework.data.jpa.repository.JpaRepository) ticketRepository).save(ticket);
        return mapToResponse(saved);
    }

    public List<TicketDTOResponse> getTicketsByLayoutId(Long layoutId) {
        return ticketRepository.findByEventLayoutId(layoutId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private TicketDTOResponse mapToResponse(Object ticketObject) {
        TicketDTOResponse response = new TicketDTOResponse();
        response.setId((Long) invokeGetter(ticketObject, "getId"));
        response.setRowField(parseRowField(invokeGetter(ticketObject, "getRowField")));
        response.setColumnField((Integer) invokeGetter(ticketObject, "getColumnField"));

        Object eventLayout = invokeGetter(ticketObject, "getEventLayout");
        response.setLayoutId(eventLayout != null ? (Long) invokeGetter(eventLayout, "getId") : null);
        response.setAvailable(Boolean.TRUE.equals(invokeGetter(ticketObject, "isAvailable")));
        return response;
    }

    private TicketDTOResponse mapToResponse(Ticket ticket) {
        TicketDTOResponse response = new TicketDTOResponse();
        response.setId(ticket.getId());
        response.setRowField(parseRowField(ticket.getRowField()));
        response.setColumnField(ticket.getColumnField());
        response.setLayoutId(ticket.getEventLayout() != null ? ticket.getEventLayout().getId() : null);
        response.setAvailable(ticket.isAvailable());
        return response;
    }

    private Integer parseRowField(String rowField) {
        if (rowField == null) {
            return null;
        }

        try {
            return Integer.valueOf(rowField);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private Integer parseRowField(Object rowField) {
        if (rowField == null) {
            return null;
        }
        if (rowField instanceof Integer) {
            return (Integer) rowField;
        }
        if (rowField instanceof String) {
            return parseRowField((String) rowField);
        }
        return null;
    }

    private Object invokeGetter(Object target, String methodName) {
        if (target == null) {
            return null;
        }

        try {
            return target.getClass().getMethod(methodName).invoke(target);
        } catch (ReflectiveOperationException ex) {
            throw new IllegalStateException("Impossibile leggere il campo '" + methodName + "' da " + target.getClass().getName(), ex);
        }
    }
}