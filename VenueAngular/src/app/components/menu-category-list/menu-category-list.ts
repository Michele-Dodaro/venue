import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuService } from '../../services/menu.service';
import { MenuCategoryDTO, MenuItemDTOResponse } from '../../models/MenuDTO';
import { catchError, forkJoin, map, of, switchMap } from 'rxjs';

@Component({
  selector: 'app-menu-category-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './menu-category-list.html',
  styleUrl: './menu-category-list.css'
})
export class MenuCategoryListComponent implements OnInit {
  categories: { category: MenuCategoryDTO; items: MenuItemDTOResponse[] }[] = [];
  loading = false;
  errorMessage: string | null = null;

  constructor(
    private menuService: MenuService,
    private cdr: ChangeDetectorRef 
  ) {}

  ngOnInit(): void {
    this.loading = true;
    this.menuService.getMenu().pipe(
      switchMap(cats => {
        if (!cats || cats.length === 0) return of([]);
        
        const requests = cats.map(cat => {
          const catKey = cat.name ?? '';
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