package com.venue.app.controller;

import com.venue.app.model.dto.TicketDTORequest;
import com.venue.app.model.dto.TicketDTOResponse;
import com.venue.app.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketDTOResponse> buyTicket(@RequestBody TicketDTORequest request) {
        try {
            TicketDTOResponse response = ticketService.createTicket(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/layout/{layoutId}")
    public ResponseEntity<List<TicketDTOResponse>> getTicketsByLayout(@PathVariable Long layoutId) {
        return ResponseEntity.ok(ticketService.getTicketsByLayoutId(layoutId));
    }
}