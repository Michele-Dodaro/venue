import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuService } from '../../services/menu.service';
import { MenuCategoryDTO, MenuItemDTOResponse } from '../../models/MenuDTO';
import { catchError, forkJoin, map, of, switchMap } from 'rxjs';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { inject } from '@angular/core';

@Component({
    selector: 'app-menu-category-list',
    standalone: true,
    imports: [CommonModule, RouterModule],
    templateUrl: './menu-category-list.html',
    styleUrl: './menu-category-list.css'
})
export class MenuCategoryListComponent implements OnInit {
    categories: { category: MenuCategoryDTO; items: MenuItemDTOResponse[] }[] = [];
    loading = false;
    errorMessage: string | null = null;
    private authService = inject(AuthService);
    isLoggedIn = false;

    constructor(
        private menuService: MenuService,
        private cdr: ChangeDetectorRef
    ) {
        this.isLoggedIn = this.authService.isLoggedIn();
        this.authService.getAuthState().subscribe(v => this.isLoggedIn = v);
    }
    deleteItem(categoryName: string, itemId: number): void {
        console.log(`Attempting to delete item with ID ${itemId}`);
        const confirmDelete = confirm('Sei sicuro di voler eliminare questo elemento?');

        if (confirmDelete) {
            this.menuService.deleteMenuItem(categoryName, itemId).subscribe({
                next: () => {
                    console.log('Item deleted successfully');
                    const categoryIndex = this.categories.findIndex((c: any) => c.category.type === categoryName);
                    if (categoryIndex !== -1) {
                        this.categories[categoryIndex].items = this.categories[categoryIndex].items.filter((item: any) => item.id !== itemId);
                    }
                },
                error: (err) => {
                    console.error('Error during deletion', err);
                }
            });
        }
    }
    ngOnInit(): void {
        this.loading = true;
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
                this.loading = false;
                this.errorMessage = 'Error on load';
                this.cdr.markForCheck();
            }
        });
    }
}