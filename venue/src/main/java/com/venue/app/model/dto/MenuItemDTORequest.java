package com.venue.app.model.dto;

import java.math.BigDecimal;

public class MenuItemDTORequest {

    private String plate;
    private String description;
    private BigDecimal originalPrice;
    private Long categoryId;

    public MenuItemDTORequest() {
    }

    public MenuItemDTORequest(String plate, String description, BigDecimal originalPrice, Long categoryId) {
        this.plate = plate;
        this.description = description;
        this.originalPrice = originalPrice;
        this.categoryId = categoryId;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}