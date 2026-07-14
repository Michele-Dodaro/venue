package com.venue.app.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class MenuCategoryDTOResponse {
    private String type;
    private List<MenuItemDTOResponse> items = new ArrayList<>();

    public MenuCategoryDTOResponse() {
    }

    public MenuCategoryDTOResponse(String name) {
        this.type = name;
    }

}
