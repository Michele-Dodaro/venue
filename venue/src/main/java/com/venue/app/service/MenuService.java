package com.venue.app.service;

import com.venue.app.model.dto.MenuCategoryDTO;
import com.venue.app.model.dto.MenuItemDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuService {

    private final JdbcTemplate jdbcTemplate;

    public MenuService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<MenuCategoryDTO> getMenu() {
        List<MenuCategoryDTO> categories = jdbcTemplate.query(
                "SELECT type FROM menu_categories",
                (rs, rowNum) -> new MenuCategoryDTO(rs.getString("type"))
        );

        for (MenuCategoryDTO cat : categories) {
            List<MenuItemDTO> items = jdbcTemplate.query(
                    "SELECT plate, description FROM menu_items WHERE menu_categories_id = (SELECT id FROM menu_categories WHERE type = ?)",
                    (rs, rowNum) -> new MenuItemDTO(rs.getString("plate"), rs.getString("description")),
                    cat.getName()
            );
            cat.setItems(items);
        }
        return categories;
    }

    public boolean createCategory(MenuCategoryDTO newCategory) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM menu_categories WHERE type = ?",
                Integer.class,
                newCategory.getName()
        );

        if (count != null && count > 0) {
            return false;
        }

        jdbcTemplate.update("INSERT INTO menu_categories (type) VALUES (?)", newCategory.getName());
        return true;
    }

    public boolean updateCategory(String categoryName, MenuCategoryDTO updatedCategory) {
        int updated = jdbcTemplate.update(
                "UPDATE menu_categories SET type = ? WHERE type = ?",
                updatedCategory.getName(),
                categoryName
        );
        return updated > 0;
    }

    public void deleteCategory(String categoryName) {
        jdbcTemplate.update("DELETE FROM menu_categories WHERE type = ?", categoryName);
    }

    public boolean addMenuItemToCategory(String categoryName, MenuItemDTO menuItemDTO) {
        List<Long> categoryIds = jdbcTemplate.queryForList(
                "SELECT id FROM menu_categories WHERE type = ?",
                Long.class,
                categoryName
        );

        if (!categoryIds.isEmpty()) {
            Long categoryId = categoryIds.get(0);
            jdbcTemplate.update(
                    "INSERT INTO menu_items (plate, description, original_price, menu_categories_id) VALUES (?, ?, 0, ?)",
                    menuItemDTO.getName(),
                    menuItemDTO.getUrl(),
                    categoryId
            );
            return true;
        }
        return false;
    }

    public boolean updateMenuItem(String categoryName, Long itemId, MenuItemDTO updatedMenuItem) {
        int updated = jdbcTemplate.update(
                "UPDATE menu_items SET plate = ?, description = ? WHERE id = ? AND menu_categories_id = (SELECT id FROM menu_categories WHERE type = ?)",
                updatedMenuItem.getName(),
                updatedMenuItem.getUrl(),
                itemId,
                categoryName
        );
        return updated > 0;
    }

    public boolean deleteMenuItem(String categoryName, Long itemId) {
        int deleted = jdbcTemplate.update(
                "DELETE FROM menu_items WHERE id = ? AND menu_categories_id = (SELECT id FROM menu_categories WHERE type = ?)",
                itemId,
                categoryName
        );
        return deleted > 0;
    }
}