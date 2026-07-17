package com.venue.app.model.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "event_layout")
public class EventLayout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String conformation;

    @Column(name = "row", length = 50)
    private Integer rowField;

    @Column(nullable = false)
    private Integer number;

    @Column(name = "price1", nullable = false, precision = 10, scale = 2)
    private BigDecimal price1;

    @Column(name = "price2", precision = 10, scale = 2)
    private BigDecimal price2;

    @Column(name = "price3", precision = 10, scale = 2)
    private BigDecimal price3;

    @OneToMany(mappedBy = "eventLayout")
    private List<Ticket> tickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConformation() {
        return conformation;
    }

    public void setConformation(String conformation) {
        this.conformation = conformation;
    }

    public Integer getRowField() {
        return rowField;
    }

    public void setRowField(Integer rowField) {
        this.rowField = rowField;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getPrice1() {
        return price1;
    }

    public void setPrice1(BigDecimal price1) {
        this.price1 = price1;
    }

    public BigDecimal getPrice2() {
        return price2;
    }

    public void setPrice2(BigDecimal price2) {
        this.price2 = price2;
    }

    public BigDecimal getPrice3() {
        return price3;
    }

    public void setPrice3(BigDecimal price3) {
        this.price3 = price3;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}