package com.venue.app.model.dto;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class MenuItemDTOResponse {
    private static final AtomicLong counter = new AtomicLong();
    private final long id;
    private String name;
    private String url;
    private BigDecimal originalPrice;


    public MenuItemDTOResponse() {
        this.id = counter.incrementAndGet();
    }

    public MenuItemDTOResponse(Long id,String name, String url, BigDecimal originalPrice) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.url = url;
        this.originalPrice = originalPrice;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public BigDecimal getOriginalPrice() {
        return originalPrice;
    }
    public void setOriginalPrice(BigDecimal originalPrice) {
        this.originalPrice = originalPrice;
    }
}
