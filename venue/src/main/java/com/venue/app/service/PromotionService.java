package com.venue.app.service;

import com.venue.app.model.dto.PromotionDTORequest;
import com.venue.app.model.dto.PromotionDTOResponse;
import com.venue.app.model.entity.MenuItems;
import com.venue.app.model.entity.Promotion;
import com.venue.app.model.entity.PromotionItems;
import com.venue.app.repository.MenuItemRepository;
import com.venue.app.repository.PromotionRepository;
import com.venue.app.repository.PromotionItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PromotionService {

    private final MenuItemRepository menuItemRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionItemsRepository promotionItemsRepository;

    public PromotionService(MenuItemRepository menuItemRepository,
                            PromotionRepository promotionRepository,
                            PromotionItemsRepository promotionItemsRepository) {
        this.menuItemRepository = menuItemRepository;
        this.promotionRepository = promotionRepository;
        this.promotionItemsRepository = promotionItemsRepository;
    }

    @Transactional
    public PromotionDTOResponse createPromotion(PromotionDTORequest request) {
        Promotion promotion = new Promotion();
        promotion.setPromotionTable(request.getPromotionTable());
        promotion.setPromotionPrice(request.getPromotionPrice());
        promotion.setExpiresIn(request.getExpiresIn());

        Promotion savedPromotion = promotionRepository.save(promotion);

        PromotionDTOResponse response = new PromotionDTOResponse();
        response.setId(savedPromotion.getId());
        response.setPromotionTable(savedPromotion.getPromotionTable());
        response.setPromotionPrice(savedPromotion.getPromotionPrice());
        response.setCreatedAt(savedPromotion.getCreatedAt());
        response.setExpiresIn(savedPromotion.getExpiresIn());

        return response;
    }

    @Transactional
    public boolean applyPromotionToMenuItem(Long promotionId, Long menuItemId) {
        Optional<Promotion> promoOpt = promotionRepository.findById(promotionId);
        Optional<MenuItems> itemOpt = menuItemRepository.findById(menuItemId);

        if (promoOpt.isPresent() && itemOpt.isPresent()) {
            Promotion promotion = promoOpt.get();
            MenuItems item = itemOpt.get();

            PromotionItems promoItem = new PromotionItems();
            promoItem.setPromotion(promotion);
            promoItem.setMenuItem(item);
            promotionItemsRepository.save(promoItem);

            item.setOriginalPrice(promotion.getPromotionPrice());
            menuItemRepository.save(item);

            return true;
        }

        return false;
    }
}