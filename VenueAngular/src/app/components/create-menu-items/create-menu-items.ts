import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { MenuService } from '../../services/menu.service';
import { MenuCategoryDTO } from '../../models/MenuDTO';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-create-menu-items',
  standalone: true,
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './create-menu-items.html',
  styleUrl: './create-menu-items.css',
})
export class CreateMenuItems implements OnInit {
  menuItemForm: FormGroup;
  categories: MenuCategoryDTO[] = []; 

  constructor(
    private fb: FormBuilder,
    private menuService: MenuService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.menuItemForm = this.fb.group({
      plate: ['', Validators.required],
      description: ['', Validators.required],
      originalPrice: [0, [Validators.required, Validators.min(0)]],
      type: ['', Validators.required]
    });
  }

  ngOnInit(): void {

    this.menuService.getMenu().subscribe({
      next: (data) => this.categories = data,
      error: (err) => console.error('Error fetching categories', err)
    });
  }

  onSubmit(): void {
    if (this.menuItemForm.valid) {
      const selectedType = this.menuItemForm.get('type')?.value;

      this.menuService.addMenuItemToCategory(selectedType, this.menuItemForm.value).subscribe({
        next: () => this.router.navigate(['/']),
        error: (err) => console.error('Error creating item', err)
      });
    }
  }
}