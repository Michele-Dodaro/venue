package com.venue.app.controller;

import com.venue.app.model.dto.MenuCategoryDTORequest;
import com.venue.app.model.dto.MenuCategoryDTOResponse;
import com.venue.app.model.dto.MenuItemDTORequest;
import com.venue.app.model.dto.MenuItemDTOResponse;
import com.venue.app.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    public ResponseEntity<List<MenuCategoryDTOResponse>> getMenu() {
        List<MenuCategoryDTOResponse> categories = menuService.getMenu();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/items")
    public ResponseEntity<List<MenuItemDTOResponse>> getAllMenuItems(@RequestParam(required = false) String category) {
        List<MenuItemDTOResponse> items = menuService.getMenuItems(category);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<MenuCategoryDTORequest> createCategory(@RequestBody MenuCategoryDTORequest newCategory) {
        boolean created = menuService.createCategory(newCategory);
        if (!created) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }


    @PutMapping("/categories/modifica/{categoryName}")
    public ResponseEntity<MenuCategoryDTORequest> updateCategory(@PathVariable String categoryName, @RequestBody MenuCategoryDTORequest updatedCategory) {
        boolean updated = menuService.updateCategory(categoryName, updatedCategory);
        if (updated) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/categories/cancella/{categoryName}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String categoryName) {
        menuService.deleteCategory(categoryName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/categories/{categoryName}/items")
    public ResponseEntity<MenuItemDTORequest> addMenuItemToCategory(@PathVariable String categoryName, @RequestBody MenuItemDTORequest menuItemDTORequest) {
        boolean added = menuService.addMenuItemToCategory(categoryName, menuItemDTORequest);
        if (added) {
            return new ResponseEntity<>(menuItemDTORequest, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/categories/{categoryName}/items/{itemId}")
    public ResponseEntity<MenuItemDTORequest> updateMenuItem(@PathVariable String categoryName, @PathVariable Long itemId, @RequestBody MenuItemDTORequest updatedMenuItem) {
        boolean updated = menuService.updateMenuItem(categoryName, itemId, updatedMenuItem);
        if (updated) {
            return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/categories/{categoryName}/items/{itemId}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable String categoryName, @PathVariable Long itemId) {
        boolean deleted = menuService.deleteMenuItem(categoryName, itemId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}