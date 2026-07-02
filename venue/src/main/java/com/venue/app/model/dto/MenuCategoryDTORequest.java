package com.venue.app.model.dto;

public class MenuCategoryDTORequest {
    private String type;

    public MenuCategoryDTORequest() {
    }

    public MenuCategoryDTORequest(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}