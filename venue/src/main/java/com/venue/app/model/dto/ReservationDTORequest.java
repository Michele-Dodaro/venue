package com.venue.app.model.dto;

import java.math.BigDecimal;

// MODIFICA: Creazione del DTO di richiesta per le prenotazioni, utilizzando una classe Java tradizionale senza Lombok.
public class ReservationDTORequest {

    private Long eventId;
    private String customerName;
    private String customerEmail;
    private String customerPhone;
    private Integer numberOfParticipants;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String reservationStatus;

    public ReservationDTORequest() {
    }

    public ReservationDTORequest(Long eventId, String customerName, String customerEmail, String customerPhone, Integer numberOfParticipants, BigDecimal totalAmount, String paymentStatus, String reservationStatus) {
        this.eventId = eventId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.customerPhone = customerPhone;
        this.numberOfParticipants = numberOfParticipants;
        this.totalAmount = totalAmount;
        this.paymentStatus = paymentStatus;
        this.reservationStatus = reservationStatus;
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
}