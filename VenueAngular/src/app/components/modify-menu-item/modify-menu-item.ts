import { Component, OnInit, ChangeDetectionStrategy, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { MenuService } from '../../services/menu.service';
import { MenuItemDTORequest, MenuItemDTOResponse, MenuCategoryDTO } from '../../models/MenuDTO';

@Component({
  selector: 'app-modify-menu-item',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './modify-menu-item.html',
  styleUrl: './modify-menu-item.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ModifyMenuItemComponent implements OnInit {
  menuItemForm!: FormGroup;
  categories: MenuCategoryDTO[] = [];
  isLoading = false;
  itemId: number | null = null;
  categoryName: string | null = null;
  menuItem: MenuItemDTOResponse | null = null;

  private fb = inject(FormBuilder);
  private menuService = inject(MenuService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.initializeForm();
    this.loadCategories();
    this.loadMenuItem();
  }

  private initializeForm(): void {
    this.menuItemForm = this.fb.group({
      plate: ['', Validators.required],
      description: ['', Validators.required],
      originalPrice: [0, [Validators.required, Validators.min(0)]],
      type: ['', Validators.required],
    });
  }

  private loadMenuItem(): void {
    const itemIdParam = this.route.snapshot.paramMap.get('itemId');
    const categoryNameParam = this.route.snapshot.paramMap.get('categoryName');

    if (itemIdParam && categoryNameParam) {
      this.itemId = Number(itemIdParam);
      this.categoryName = categoryNameParam;
      this.isLoading = true;
      this.cdr.markForCheck();

      // Carica il menu item dalla lista
      this.menuService.getAllMenuItems(categoryNameParam).subscribe({
        next: (items) => {
          const item = items.find((i) => i.id === this.itemId);
          if (item) {
            this.menuItem = item;
            this.populateForm(item);
          }
          this.isLoading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error loading menu item:', err);
          this.isLoading = false;
          this.cdr.markForCheck();
          alert('Errore nel caricamento del piatto');
        },
      });
    }
  }

  private populateForm(item: MenuItemDTOResponse): void {
    this.menuItemForm.patchValue({
      plate: item.name,
      description: item.url || '',
      originalPrice: item.originalPrice,
      type: this.categoryName,
    });
  }

  private loadCategories(): void {
    this.menuService.getMenu().subscribe({
      next: (data) => {
        this.categories = data;
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error fetching categories', err),
    });
  }

  onSubmit(): void {
    if (this.menuItemForm.valid && this.itemId !== null && this.categoryName !== null) {
      this.isLoading = true;
      this.cdr.markForCheck();

      const formValue = this.menuItemForm.value;
      
      // Trova l'ID della categoria dal nome
      const selectedCategory = this.categories.find((cat) => cat.type === formValue.type);
      const categoryId = selectedCategory?.id || 0;

      const updateData: MenuItemDTORequest = {
        plate: formValue.plate,
        description: formValue.description,
        originalPrice: Number(formValue.originalPrice),
        categoryId: categoryId,
      };

      console.log('Dati PUT MenuItem:', updateData);

      this.menuService.updateMenuItem(this.categoryName, this.itemId, updateData).subscribe({
        next: (res) => {
          console.log('Menu item aggiornato!', res);
          this.isLoading = false;
          this.cdr.markForCheck();
          alert('Piatto aggiornato con successo!');
          this.router.navigate(['/menu-category-list']);
        },
        error: (err) => {
          console.error('Errore nella richiesta PUT:', err);
          console.error('Status:', err.status);
          console.error('Error details:', err.error);
          this.isLoading = false;
          this.cdr.markForCheck();
          alert('Errore nell\'aggiornamento del piatto: ' + (err.error?.message || err.statusText || 'Errore sconosciuto'));
        },
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/menu-category-list']);
  }
}
