package com.venue.app.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// MODIFICA: Creazione del DTO di risposta per le prenotazioni, formattato come classe standard senza Lombok.
public class ReservationDTOResponse {

    private Long id;
    private Long eventId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Integer numberOfParticipants;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String reservationStatus;
    private LocalDateTime creationTimestamp;

    public ReservationDTOResponse() {
    }

    public ReservationDTOResponse(Long id, Long eventId, String customerName, String customerEmail, String customerPhone, Integer numberOfParticipants, BigDecimal totalAmount, String paymentStatus, String reservationStatus, LocalDateTime creationTimestamp) {
        this.id = id;
        this.eventId = eventId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.numberOfParticipants = numberOfParticipants;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
        this.creationTimestamp = creationTimestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public Integer getNumberOfParticipants() {
        return numberOfParticipants;
    }

    public void setNumberOfParticipants(Integer numberOfParticipants) {
        this.numberOfParticipants = numberOfParticipants;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}