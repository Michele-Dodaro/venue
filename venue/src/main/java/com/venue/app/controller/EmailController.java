package com.venue.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.venue.app.service.QRCodeService;
import com.venue.app.service.EmailService;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class EmailController {

    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);

    private final QRCodeService qrCodeService;
    private final EmailService emailService;

    public EmailController(QRCodeService qrCodeService, EmailService emailService) {
        this.qrCodeService = qrCodeService;
        this.emailService = emailService;
    }

    @PostMapping("/api/email/send-ticket")
    public ResponseEntity<?> sendTicket(@RequestBody Map<String, Object> request) {
        try {
            logger.info("Ricevuta richiesta di invio email: {}", request);

            Object emailObj = request.get("email");
            if (emailObj == null || emailObj.toString().trim().isEmpty()) {
                logger.error("Email non fornita nella richiesta");
                return ResponseEntity.badRequest().body("Errore: Email non fornita");
            }
            String email = emailObj.toString();
            logger.info("Email destinatario: {}", email);

            String eventName = request.getOrDefault("eventName", "Evento").toString();
            String eventDate = request.getOrDefault("eventDate", "").toString();
            Object filaObj = request.get("fila");
            Object postoObj = request.get("posto");
            Object priceObj = request.get("price");

            String qrCodeText = "Evento: " + eventName + "\n" +
                                "Data: " + eventDate + "\n" +
                                "Fila: " + (filaObj != null ? filaObj.toString() : "N/A") + "\n" +
                                "Posto: " + (postoObj != null ? postoObj.toString() : "N/A") + "\n" +
                                "Prezzo: " + (priceObj != null ? priceObj.toString() : "N/A");

            logger.info("Generazione QR code...");
            byte[] qrCode = qrCodeService.generateQRCodeImage(qrCodeText, 300, 300);
            logger.info("QR code generato con {} bytes", qrCode.length);

            String subject = "Biglietto - " + eventName;
            logger.info("Invio email con subject: {}", subject);
            emailService.sendReservationEmailWithQR(email, subject, qrCodeText, qrCode);

            logger.info("Email inviata con successo a: {}", email);
            return ResponseEntity.ok("Email inviata");
        } catch (Exception e) {
            logger.error("Errore nell'invio email", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Errore: " + e.getMessage());
        }
    }
}

