package com.venue.app.service;

import com.venue.app.model.dto.PromotionDTORequest;
import com.venue.app.model.dto.PromotionDTOResponse;
import com.venue.app.model.entity.EventLayout;
import com.venue.app.model.entity.MenuItems;
import com.venue.app.model.entity.Promotion;
import com.venue.app.model.entity.PromotionItems;
import com.venue.app.repository.EventLayoutRepository;
import com.venue.app.repository.MenuItemRepository;
import com.venue.app.repository.PromotionRepository;
import com.venue.app.repository.PromotionItemsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    private final MenuItemRepository menuItemRepository;
    private final PromotionRepository promotionRepository;
    private final PromotionItemsRepository promotionItemsRepository;
    private final EventLayoutRepository eventLayoutRepository;

    public PromotionService(MenuItemRepository menuItemRepository,
                            PromotionRepository promotionRepository,
                            PromotionItemsRepository promotionItemsRepository, EventLayoutRepository eventLayoutRepository) {
        this.menuItemRepository = menuItemRepository;
        this.promotionRepository = promotionRepository;
        this.promotionItemsRepository = promotionItemsRepository;
        this.eventLayoutRepository = eventLayoutRepository;
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


            return true;
        }

        return false;
    }

    @Transactional
    public boolean applyPromotionToEventLayout(Long promotionId, Long layoutId) {
        Optional<Promotion> promoOpt = promotionRepository.findById(promotionId);
        Optional<EventLayout> layoutOpt = eventLayoutRepository.findById(layoutId);

        if (promoOpt.isPresent() && layoutOpt.isPresent()) {
            Promotion promotion = promoOpt.get();
            EventLayout layout = layoutOpt.get();

            PromotionItems promoItem = new PromotionItems();
            promoItem.setPromotion(promotion);
            promoItem.setEventLayout(layout);
            promotionItemsRepository.save(promoItem);

            BigDecimal discount = promotion.getPromotionPrice();

            if (layout.getPrice1() != null) {
                layout.setPrice1(layout.getPrice1().subtract(discount));
            }

            if (layout.getPrice2() != null) {
                layout.setPrice2(layout.getPrice2().subtract(discount));
            }

            if (layout.getPrice3() != null) {
                layout.setPrice3(layout.getPrice3().subtract(discount));
            }

            eventLayoutRepository.save(layout);

            return true;
        }

        return false;
    }
    @Transactional
    public boolean deletePromotionByMenuItemId(Long menuItemId) {
        List<PromotionItems> promoItems = promotionItemsRepository.findByMenuItemId(menuItemId);

        if (!promoItems.isEmpty()) {
            promotionItemsRepository.deleteAll(promoItems);
            return true;
        }

        return false;
    }
}
