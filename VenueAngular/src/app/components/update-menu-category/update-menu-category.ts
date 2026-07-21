import { Component, OnInit, ChangeDetectionStrategy, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MenuService } from '../../services/menu.service';
import { MenuItemDTOResponse } from '../../models/MenuDTO';
import { AuthService } from '../../services/auth.service';
import { inject } from '@angular/core';

@Component({
  selector: 'app-update-menu-category',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './update-menu-category.html',
  styleUrl: './update-menu-category.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class UpdateMenuCategory implements OnInit {
  categoryForm: FormGroup;
  oldName: string = '';
  menuItems: MenuItemDTOResponse[] = [];
  loadingItems = false;

  private authService = inject(AuthService);
  private cdr = inject(ChangeDetectorRef);
  isLogged = false;

  constructor(
    private fb: FormBuilder,
    private menuService: MenuService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.categoryForm = this.fb.group({
      type: ['', Validators.required],
    });
    this.isLogged = this.authService.isLoggedIn();
    this.authService.getAuthState().subscribe((v) => {
      this.isLogged = v;
      this.cdr.markForCheck();
    });
  }

  ngOnInit(): void {
    this.oldName = this.route.snapshot.params['name'];
    this.categoryForm.patchValue({ type: this.oldName });
    this.loadMenuItems();
  }

  private loadMenuItems(): void {
    this.loadingItems = true;
    this.cdr.markForCheck();
    this.menuService.getAllMenuItems(this.oldName).subscribe({
      next: (items) => {
        this.menuItems = items;
        this.loadingItems = false;
        this.cdr.markForCheck();
      },
      error: (err) => {
        console.error('Error loading menu items:', err);
        this.loadingItems = false;
        this.cdr.markForCheck();
      },
    });
  }

  onSubmit(): void {
    if (this.categoryForm.valid) {
      const updatedData = { type: this.categoryForm.value.type };
      this.menuService.updateCategory(this.oldName, updatedData).subscribe({
        next: () => this.router.navigate(['/menu-category-list']),
        error: (err) => console.error('Errore durante l\'aggiornamento', err),
      });
    }
  }

  deleteItem(itemId: number): void {
    const confirmDelete = confirm('Sei sicuro di voler eliminare questo elemento?');
    if (confirmDelete) {
      this.menuService.deleteMenuItem(this.oldName, itemId).subscribe({
        next: () => {
          this.menuItems = this.menuItems.filter((item) => item.id !== itemId);
          this.cdr.markForCheck();
        },
        error: (err) => console.error('Error deleting item:', err),
      });
    }
  }
}
