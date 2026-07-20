import { Component, OnInit, ChangeDetectorRef, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MenuService } from '../../services/menu.service';
import { PromotionService } from '../../services/promotion.service';
import { MenuItemDTOResponse, MenuCategoryDTO } from '../../models/MenuDTO';
import { PromotionDTORequest } from '../../models/PromotionDTO';
import { forkJoin, map, catchError, of, switchMap } from 'rxjs';
import { AuthService } from '../../services/auth.service';

@Component({
    selector: 'app-menu-promotions',
    standalone: true,
    imports: [CommonModule, FormsModule],
    templateUrl: './menu-promotions.html',
    styleUrl: './menu-promotions.css'
})
export class MenuPromotionsComponent implements OnInit {
    categories: { category: MenuCategoryDTO; items: MenuItemDTOResponse[] }[] = [];
    loading = false;
    errorMessage: string | null = null;
    successMessage: string | null = null;
    
    private menuService = inject(MenuService);
    private promotionService = inject(PromotionService);
    private authService = inject(AuthService);
    private cdr = inject(ChangeDetectorRef);

    promotionForm: { [itemId: number]: { promotionPrice: number; expiresIn: string } } = {};
    expandedItemId: number | null = null;

    constructor() {
        if (!this.authService.isLoggedIn()) {
            window.location.href = '/login';
        }
    }

    ngOnInit(): void {
        this.loadMenuItems();
    }

    loadMenuItems(): void {
        this.loading = true;
        this.errorMessage = null;

        this.menuService.getMenu().pipe(
            switchMap(cats => {
                if (!cats || cats.length === 0) return of([]);

                const requests = cats.map(cat => {
                    const catKey = cat.type ?? '';
                    return this.menuService.getAllMenuItems(catKey).pipe(
                        map(items => ({ category: cat, items })),
                        catchError(() => of({ category: cat, items: [] }))
                    );
                });

                return forkJoin(requests);
            })
        ).subscribe({
            next: (data) => {
                this.categories = data;
                this.loading = false;
                this.cdr.markForCheck();
            },
            error: (err) => {
                console.error('Error:', err);
                this.errorMessage = 'Errore nel caricamento dei menu items';
                this.loading = false;
                this.cdr.markForCheck();
            }
        });
    }

    toggleExpanded(itemId: number): void {
        this.expandedItemId = this.expandedItemId === itemId ? null : itemId;
        if (!this.promotionForm[itemId]) {
            this.promotionForm[itemId] = { promotionPrice: 0, expiresIn: '' };
        }
    }

    isExpanded(itemId: number): boolean {
        return this.expandedItemId === itemId;
    }

    createPromotion(item: MenuItemDTOResponse): void {
        const formData = this.promotionForm[item.id];
        
        if (!formData || formData.promotionPrice <= 0) {
            this.errorMessage = 'Inserisci un prezzo di offerta valido';
            return;
        }

        if (!formData.expiresIn) {
            this.errorMessage = 'Inserisci una data di scadenza';
            return;
        }

        // Converti la data (formato YYYY-MM-DD) a ISO string
        const dateString = formData.expiresIn + 'T23:59:59.000Z';

        const promotionRequest: PromotionDTORequest = {
            promotionTable: item.id,
            promotionPrice: formData.promotionPrice,
            expiresIn: dateString
        };

        this.promotionService.createPromotion(promotionRequest).subscribe({
            next: (promotion) => {
                this.promotionService.applyPromotionToItem(promotion.id, item.id).subscribe({
                    next: () => {
                        this.successMessage = `Offerta creata per ${item.name}`;
                        item.promotionPrice = formData.promotionPrice;
                        this.promotionForm[item.id] = { promotionPrice: 0, expiresIn: '' };
                        this.expandedItemId = null;
                        this.cdr.markForCheck();
                        setTimeout(() => this.successMessage = null, 3000);
                    },
                    error: (err) => {
                        console.error('Error applying promotion:', err);
                        this.errorMessage = 'Errore nell\'applicazione dell\'offerta';
                        this.cdr.markForCheck();
                    }
                });
            },
            error: (err) => {
                console.error('Error creating promotion:', err);
                this.errorMessage = 'Errore nella creazione dell\'offerta';
                this.cdr.markForCheck();
            }
        });
    }

    hasPromotion(item: MenuItemDTOResponse): boolean {
        return item.promotionPrice !== undefined && item.promotionPrice > 0;
    }

    getDiscountPercentage(item: MenuItemDTOResponse): number {
        if (!this.hasPromotion(item)) return 0;
        const discount = ((item.originalPrice - item.promotionPrice!) / item.originalPrice) * 100;
        return Math.round(discount);
    }

    clearMessages(): void {
        this.errorMessage = null;
        this.successMessage = null;
    }
}
