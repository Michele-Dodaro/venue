package com.venue.app.model.dto;

import java.math.BigDecimal;

public class EventLayoutDTOResponse {

    private Long id;
    private String conformation;
    private String rowField;
    private Integer number;
    private BigDecimal price1;
    private BigDecimal price2;
    private BigDecimal price3;

    public EventLayoutDTOResponse() {
    }

    public EventLayoutDTOResponse(Long id, String conformation, String rowField, Integer number, BigDecimal price1, BigDecimal price2, BigDecimal price3, Long eventId) {
        this.id = id;
        this.conformation = conformation;
        this.rowField = rowField;
        this.number = number;
        this.price1 = price1;
        this.price2 = price2;
        this.price3 = price3;
    }

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

    public String getRowField() {
        return rowField;
    }

    public void setRowField(String rowField) {
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

}
