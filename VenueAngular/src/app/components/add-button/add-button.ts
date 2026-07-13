import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-add-button',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './add-button.html',
  styleUrl: './add-button.css'
})
export class AddButtonComponent {}
