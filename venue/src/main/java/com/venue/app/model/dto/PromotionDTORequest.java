package com.venue.app.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class PromotionDTORequest {

    private BigDecimal promotionTable;
    private BigDecimal promotionPrice;
    private OffsetDateTime expiresIn;

    public PromotionDTORequest() {
    }

    public PromotionDTORequest(BigDecimal promotionTable, BigDecimal promotionPrice, OffsetDateTime expiresIn) {
        this.promotionTable = promotionTable;
        this.promotionPrice = promotionPrice;
        this.expiresIn = expiresIn;
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

    public OffsetDateTime getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(OffsetDateTime expiresIn) {
        this.expiresIn = expiresIn;
    }
}