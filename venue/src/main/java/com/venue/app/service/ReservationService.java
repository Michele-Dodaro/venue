package com.venue.app.service;

import com.venue.app.model.dto.ReservationDTORequest;
import com.venue.app.model.dto.ReservationDTOResponse;
import com.venue.app.model.entity.Event;
import com.venue.app.model.entity.Reservations;
import com.venue.app.repository.EventRepository;
import com.venue.app.repository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final EventRepository eventRepository;
    private final QRCodeService qrCodeService;
    private final EmailService emailService;

    public ReservationService(ReservationRepository reservationRepository,
                              EventRepository eventRepository,
                              QRCodeService qrCodeService,
                              EmailService emailService) {
        this.reservationRepository = reservationRepository;
        this.eventRepository = eventRepository;
        this.qrCodeService = qrCodeService;
        this.emailService = emailService;
    }

    @Transactional
    public ReservationDTOResponse createReservation(ReservationDTORequest request) {
        Optional<Event> eventOpt = eventRepository.findById(request.getEventId());
        if (eventOpt.isEmpty()) {
            throw new IllegalArgumentException("Event not found with ID: " + request.getEventId());
        }

        Reservations reservation = new Reservations();
        reservation.setEvent(eventOpt.get());
        reservation.setCustomerName(request.getCustomerName());
        reservation.setCustomerEmail(request.getCustomerEmail());
        reservation.setCustomerPhone(request.getCustomerPhone());
        reservation.setNumberOfParticipants(request.getNumberOfParticipants() != null ? request.getNumberOfParticipants() : 1);
        reservation.setTotalAmount(request.getTotalAmount());
        reservation.setPaymentStatus(request.getPaymentStatus());
        reservation.setReservationStatus(request.getReservationStatus());

        Reservations savedReservation = reservationRepository.save(reservation);

        try {
            String qrData = "ReservationID:" + savedReservation.getId() + "-EventID:" + request.getEventId() + "-Name:" + request.getCustomerName();
            byte[] qrCodeImage = qrCodeService.generateQRCodeImage(qrData, 250, 250);

            String emailBody = "Dear " + request.getCustomerName() + ",\n\n" +
                    "Your reservation for the event is confirmed.\n" +
                    "Attached is your QR Code to present at the entrance.\n\n" +
                    "Total paid: " + request.getTotalAmount() + " EUR\n" +
                    "Status: " + request.getReservationStatus() + "\n\n" +
                    "Thank you!";

            emailService.sendReservationEmailWithQR(
                    request.getCustomerEmail(),
                    "Event Reservation Confirmation",
                    emailBody,
                    qrCodeImage
            );
        } catch (Exception e) {
            System.err.println("Error during QR code generation or email sending: " + e.getMessage());
        }

        return mapToResponse(savedReservation);
    }

    public List<ReservationDTOResponse> getAllReservations() {
        return reservationRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public Optional<ReservationDTOResponse> getReservationById(Long id) {
        return reservationRepository.findById(id)
                .map(this::mapToResponse);
    }

    @Transactional
    public Optional<ReservationDTOResponse> updateReservation(Long id, ReservationDTORequest request) {
        Optional<Reservations> existingReservationOpt = reservationRepository.findById(id);

        if (existingReservationOpt.isPresent()) {
            Reservations reservation = existingReservationOpt.get();
            reservation.setCustomerName(request.getCustomerName());
            reservation.setCustomerEmail(request.getCustomerEmail());
            reservation.setCustomerPhone(request.getCustomerPhone());
            reservation.setNumberOfParticipants(request.getNumberOfParticipants());
            reservation.setTotalAmount(request.getTotalAmount());
            reservation.setPaymentStatus(request.getPaymentStatus());
            reservation.setReservationStatus(request.getReservationStatus());

            if (!reservation.getEvent().getId().equals(request.getEventId())) {
                Optional<Event> eventOpt = eventRepository.findById(request.getEventId());
                eventOpt.ifPresent(reservation::setEvent);
            }

            Reservations updatedReservation = reservationRepository.save(reservation);
            return Optional.of(mapToResponse(updatedReservation));
        }
        return Optional.empty();
    }

    public boolean deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ReservationDTOResponse mapToResponse(Reservations reservation) {
        ReservationDTOResponse response = new ReservationDTOResponse();
        response.setId(reservation.getId());
        response.setEventId(reservation.getEvent() != null ? reservation.getEvent().getId() : null);
        response.setCustomerName(reservation.getCustomerName());
        response.setCustomerEmail(reservation.getCustomerEmail());
        response.setCustomerPhone(reservation.getCustomerPhone());
        response.setNumberOfParticipants(reservation.getNumberOfParticipants());
        response.setTotalAmount(reservation.getTotalAmount());
        response.setPaymentStatus(reservation.getPaymentStatus());
        response.setReservationStatus(reservation.getReservationStatus());
        response.setCreationTimestamp(reservation.getCreationTimestamp());
        return response;
    }
}
