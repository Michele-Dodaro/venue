package com.venue.app.model.dto;

import java.util.concurrent.atomic.AtomicLong;

public class MenuItemDTO {
    private static final AtomicLong counter = new AtomicLong();
    private final long id;
    private String name;
    private String url;

    public MenuItemDTO() {
        this.id = counter.incrementAndGet();
    }

    public MenuItemDTO(String name, String url) {
        this.id = counter.incrementAndGet();
        this.name = name;
        this.url = url;
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
}
