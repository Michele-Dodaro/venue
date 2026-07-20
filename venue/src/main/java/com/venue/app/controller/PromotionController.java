package com.venue.app.controller;

import com.venue.app.model.dto.PromotionDTORequest;
import com.venue.app.model.dto.PromotionDTOResponse;
import com.venue.app.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final PromotionService promotionService;

    public PromotionController(PromotionService promotionService) {
        this.promotionService = promotionService;
    }


    @PostMapping
    public ResponseEntity<PromotionDTOResponse> createPromotion(@RequestBody PromotionDTORequest request) {
        PromotionDTOResponse response = promotionService.createPromotion(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{promotionId}/apply-to-item/{menuItemId}")
    public ResponseEntity<String> applyPromotionToItem(@PathVariable Long promotionId, @PathVariable Long menuItemId) {
        boolean success = promotionService.applyPromotionToMenuItem(promotionId, menuItemId);

        if (success) {
            return ResponseEntity.ok("Promotion applied successfully and price updated.");
        }

        return ResponseEntity.badRequest().body("Failed to apply promotion. Promotion or Menu Item not found.");
    }

    @PostMapping("/{promotionId}/apply-to-layout/{layoutId}")
    public ResponseEntity<String> applyPromotionToLayout(@PathVariable Long promotionId, @PathVariable Long layoutId) {
        boolean success = promotionService.applyPromotionToEventLayout(promotionId, layoutId);

        if (success) {
            return ResponseEntity.ok("Promotion applied successfully to the event layout.");
        }

        return ResponseEntity.badRequest().body("Failed to apply promotion. Promotion or Event Layout not found.");
    }
    @DeleteMapping("/item/{menuItemId}")
    public ResponseEntity<String> removePromotionFromItem(@PathVariable Long menuItemId) {
        boolean success = promotionService.deletePromotionByMenuItemId(menuItemId);

        if (success) {
            return ResponseEntity.ok("Promotion removed successfully.");
        }

        return ResponseEntity.badRequest().body("Failed to remove promotion. Item not found or no active promotion.");
    }
}