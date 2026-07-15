import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { EventLayoutService } from '../../services/event-layout.service';

@Component({
  selector: 'app-create-layout',
  imports: [ReactiveFormsModule],
  templateUrl: './create-layout.html',
  styleUrl: './create-layout.css',
})
export class CreateLayout {
  private fb = inject(FormBuilder);
  private service = inject(EventLayoutService);

  layoutForm: FormGroup = this.fb.group({
    conformation: ['', Validators.required],
    rowField: ['', Validators.required],
    number: [0, Validators.required],
    price1: [0, Validators.required],
    price2: [0, Validators.required],
    price3: [0, Validators.required]
  });

  onSubmit(): void {
    if (this.layoutForm.valid) {
      this.service.createEventLayout(this.layoutForm.value).subscribe({
        next: (res) => console.log('Creato:', res),
        error: (err) => console.error(err)
      });
    }
  }
}