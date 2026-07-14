package com.venue.app.service;

import com.venue.app.model.dto.MenuCategoryDTORequest;
import com.venue.app.model.dto.MenuCategoryDTOResponse;
import com.venue.app.model.dto.MenuItemDTORequest;
import com.venue.app.model.dto.MenuItemDTOResponse;
import com.venue.app.model.entity.MenuCategories;
import com.venue.app.model.entity.MenuItems;
import com.venue.app.repository.MenuRepository;
import com.venue.app.repository.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MenuItemRepository menuItemRepository;

    public MenuService(MenuRepository menuRepository, MenuItemRepository menuItemRepository) {
        this.menuRepository = menuRepository;
        this.menuItemRepository = menuItemRepository;
    }

    public List<MenuCategoryDTOResponse> getMenu() {
        List<MenuCategories> categories = menuRepository.findAll();

        return categories.stream().map(cat -> {
            MenuCategoryDTOResponse dto = new MenuCategoryDTOResponse();
            dto.setName(cat.getType());

            List<MenuItemDTOResponse> items = menuItemRepository.findByMenuCategoryType(cat.getType())
                    .stream()
                    .map(item -> new MenuItemDTOResponse(item.getId(), item.getPlate(), item.getDescription(), item.getOriginalPrice()))
                    .collect(Collectors.toList());

            dto.setItems(items);
            return dto;
        }).collect(Collectors.toList());
    }

    public boolean createCategory(MenuCategoryDTORequest newCategory) {
        if (menuRepository.existsByType(newCategory.getType())) {
            return false;
        }

        MenuCategories category = new MenuCategories();
        category.setType(newCategory.getType());
        menuRepository.save(category);
        return true;
    }

    public boolean updateCategory(String categoryName, MenuCategoryDTORequest updatedCategory) {
        Optional<MenuCategories> opt = menuRepository.findByType(categoryName);
        if (opt.isPresent()) {
            MenuCategories cat = opt.get();
            cat.setType(updatedCategory.getType());
            menuRepository.save(cat);
            return true;
        }
        return false;
    }

    @Transactional
    public void deleteCategory(String categoryName) {
        menuRepository.deleteByType(categoryName);
    }

    public boolean addMenuItemToCategory(String categoryName, MenuItemDTORequest request) {
        Optional<MenuCategories> catOpt = menuRepository.findByType(categoryName);

        if (catOpt.isPresent()) {
            MenuItems item = new MenuItems();
            item.setPlate(request.getPlate());
            item.setDescription(request.getDescription());
            item.setOriginalPrice(request.getOriginalPrice() != null ? request.getOriginalPrice() : BigDecimal.ZERO);
            item.setMenuCategory(catOpt.get());

            menuItemRepository.save(item);
            return true;
        }
        return false;
    }

    public boolean updateMenuItem(String categoryName, Long itemId, MenuItemDTORequest request) {
        Optional<MenuItems> itemOpt = menuItemRepository.findByIdAndMenuCategoryType(itemId, categoryName);

        if (itemOpt.isPresent()) {
            MenuItems item = itemOpt.get();
            item.setPlate(request.getPlate());
            item.setDescription(request.getDescription());
            if (request.getOriginalPrice() != null) {
                item.setOriginalPrice(request.getOriginalPrice());
            }
            menuItemRepository.save(item);
            return true;
        }
        return false;
    }

    public boolean deleteMenuItem(String categoryName, Long itemId) {
        Optional<MenuItems> itemOpt = menuItemRepository.findByIdAndMenuCategoryType(itemId, categoryName);

        if (itemOpt.isPresent()) {
            menuItemRepository.delete(itemOpt.get());
            return true;
        }
        return false;
    }

    public List<MenuItemDTOResponse> getMenuItems(String categoryName) {
        List<MenuItems> items;

        if (categoryName == null || categoryName.isBlank()) {
            items = menuItemRepository.findAll();
        } else {
            items = menuItemRepository.findByMenuCategoryType(categoryName);
        }

        return items.stream()
                .map(item -> new MenuItemDTOResponse(item.getId(), item.getPlate(), item.getDescription(), item.getOriginalPrice()))
                .collect(Collectors.toList());
    }
}