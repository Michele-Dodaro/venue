package com.venue.app.controller;

import com.venue.app.model.entity.Event;
import com.venue.app.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    @PreAuthorize("isAuthenticated()") //Todo: al momento questa annotazione è sbagliata non ti farà mai passare dentro questo metodo
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {/* TODO: meglio non usare mai le Entity direttamente nei controller.
                                                                         *   In generale usiamo i DTO sia in input che in output, per disaccoppiare
                                                                         *   l'API dal modello che andremo a salavre ed evitare problemi di sicurezza
                                                                         *   (campi come id/role settabili dal client).
                                                                         */
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.ok(createdEvent);/*Todo: meglio restituire un 200 OK
                                                    con un messaggio ed unicamente le informazioni necessarie*/
    }
}
