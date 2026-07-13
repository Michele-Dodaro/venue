import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { MenuService } from '../../services/menu.service';

@Component({
  selector: 'app-menu-category-creation',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './menu-category-creation.html',
  styleUrl: './menu-category-creation.css'
})
export class CreateMenuCategoryComponent {
  categoryForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private menuService: MenuService,
    private router: Router
  ) {
    this.categoryForm = this.fb.group({
      type: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.categoryForm.valid) {
      this.menuService.createCategory(this.categoryForm.value).subscribe({
        next: () => {
          this.categoryForm.reset();
          this.router.navigate(['/menu-category-list']);
        },
        error: (err) => console.error('Errore durante il salvataggio della categoria', err)
      });
    }
  }
}
