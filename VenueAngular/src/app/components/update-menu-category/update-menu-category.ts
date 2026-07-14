// src/app/components/update-menu-category/update-menu-category.ts
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { MenuService } from '../../services/menu.service';
import { AuthService } from '../../services/auth.service';
import { inject } from '@angular/core';
@Component({
  selector: 'app-update-menu-category',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './update-menu-category.html',
  styleUrl: './update-menu-category.css'
})
export class UpdateMenuCategory implements OnInit {
    categoryForm: FormGroup;
  oldName: string = '';
  private authService = inject(AuthService);
  isLogged = false;

  constructor(
    private fb: FormBuilder,
    private menuService: MenuService,
    private route: ActivatedRoute,
    private router: Router
  ) {
    this.categoryForm = this.fb.group({
      type: ['', Validators.required]
    });
    this.isLogged = this.authService.isLoggedIn();
    this.authService.getAuthState().subscribe(v => this.isLogged = v);
  }

  ngOnInit(): void {
    this.oldName = this.route.snapshot.params['name'];
    this.categoryForm.patchValue({ type: this.oldName });
  }

  onSubmit(): void {
    if (this.categoryForm.valid) {
      const updatedData = { type: this.categoryForm.value.type };
      this.menuService.updateCategory(this.oldName, updatedData).subscribe({
        next: () => this.router.navigate(['/menu-category-list']),
        error: (err) => console.error('Errore durante l\'aggiornamento', err)
      });
    }
  }
}
