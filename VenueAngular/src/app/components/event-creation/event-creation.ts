import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; 
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { EventService } from '../../services/event.service';
import { EventDTORequest } from '../../models/EventDTO';

type LayoutDTO = any;

@Component({
  selector: 'app-event-creation',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule], 
  templateUrl: './event-creation.html',
  styleUrl: './event-creation.css',
})
export class CreateEventComponent implements OnInit {
  eventForm: FormGroup;
  layouts: LayoutDTO[] = [];
  imagePreview: string | null = null;
  
  constructor(
    private fb: FormBuilder, 
    private eventService: EventService,
    private cdr: ChangeDetectorRef
  ) {
    this.eventForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      date: ['', Validators.required],
      layoutId: [null, Validators.required],
      active: [true],
      genre: [''],
      image: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.eventService.getLayouts().subscribe({
      next: (data) => {
        console.log('Layouts ricevuti:', data);
        this.layouts = (data || []).map((layout: any) => {
          const conformationValue = layout.conformation ?? layout.Conformation ?? layout.name ?? layout.label ?? layout.eventName ?? layout.string ?? '';
          return {
            ...layout,
            id: layout.id ?? layout.eventId ?? layout.layoutId,
            conformation: String(conformationValue)
          };
        });
        console.log('Layouts elaborati:', this.layouts);
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error fetching layouts', err)
    });
  }

  onSubmit(): void {
    if (this.eventForm.valid) {
      this.eventService.createEvent(this.eventForm.value).subscribe({
        next: (res) => console.log('Creato!', res),
        error: (err) => console.error('Errore!', err)
      });
    }
  }

  onImageSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result as string;
        this.eventForm.patchValue({ image: this.imagePreview });
      };
      reader.readAsDataURL(file);
    }
  }
}