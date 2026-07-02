package com.venue.app.controller;

import com.venue.app.model.dto.MenuCategoryDTO;
import com.venue.app.model.dto.MenuItemDTO;
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
    public ResponseEntity<List<MenuCategoryDTO>> getMenu() {
        List<MenuCategoryDTO> categories = menuService.getMenu();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @PostMapping("/categories")
    public ResponseEntity<MenuCategoryDTO> createCategory(@RequestBody MenuCategoryDTO newCategory) {
        boolean created = menuService.createCategory(newCategory);
        if (!created) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @PutMapping("/categories/modifica/{categoryName}")
    public ResponseEntity<MenuCategoryDTO> updateCategory(@PathVariable String categoryName, @RequestBody MenuCategoryDTO updatedCategory) {
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
    public ResponseEntity<MenuItemDTO> addMenuItemToCategory(@PathVariable String categoryName, @RequestBody MenuItemDTO menuItemDTO) {
        boolean added = menuService.addMenuItemToCategory(categoryName, menuItemDTO);
        if (added) {
            return new ResponseEntity<>(menuItemDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/categories/{categoryName}/items/{itemId}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable String categoryName, @PathVariable Long itemId, @RequestBody MenuItemDTO updatedMenuItem) {
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