package com.venue.app.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PromotionDTOResponse {

    private Long id;
    private BigDecimal promotionTable;
    private BigDecimal promotionPrice;
    private OffsetDateTime createdAt;
    private OffsetDateTime expiresIn;

    public PromotionDTOResponse() {
    }

    public PromotionDTOResponse(Long id, BigDecimal promotionTable, BigDecimal promotionPrice, OffsetDateTime createdAt, OffsetDateTime expiresIn) {
        this.id = id;
        this.promotionTable = promotionTable;
        this.promotionPrice = promotionPrice;
        this.createdAt = createdAt;
        this.expiresIn = expiresIn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPromotionTable() {
        return promotionTable;
    }

    public void setPromotionTable(BigDecimal promotionTable) {
        this.promotionTable = promotionTable;
    }

    public BigDecimal getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(BigDecimal promotionPrice) {
        this.promotionPrice = promotionPrice;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OffsetDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(OffsetDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }
}