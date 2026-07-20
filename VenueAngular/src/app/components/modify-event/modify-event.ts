import { Component, OnInit, ChangeDetectionStrategy, inject, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { EventService } from '../../services/event.service';
import { EventDTORequest, EventDTOResponse } from '../../models/EventDTO';

type LayoutDTO = any;

@Component({
  selector: 'app-modify-event',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './modify-event.html',
  styleUrl: './modify-event.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ModifyEventComponent implements OnInit {
  eventForm!: FormGroup;
  layouts: LayoutDTO[] = [];
  imagePreview: string | null = null;
  isLoading = false;
  eventId: number | null = null;
  event: EventDTOResponse | null = null;

  private fb = inject(FormBuilder);
  private eventService = inject(EventService);
  private route = inject(ActivatedRoute);
  private router = inject(Router);
  private cdr = inject(ChangeDetectorRef);

  ngOnInit(): void {
    this.initializeForm();
    this.loadLayouts();
    this.loadEvent();
  }

  private initializeForm(): void {
    this.eventForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      date: ['', Validators.required],
      layoutId: [null, Validators.required],
      active: [true],
      genre: [''],
      image: [''],
    });
  }

  private loadEvent(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.eventId = Number(id);
      this.isLoading = true;
      this.cdr.markForCheck();
      this.eventService.getEventById(this.eventId).subscribe({
        next: (data) => {
          this.event = data;
          this.populateForm(data);
          this.imagePreview = data.image;
          this.isLoading = false;
          this.cdr.markForCheck();
        },
        error: (err) => {
          console.error('Error loading event:', err);
          this.isLoading = false;
          this.cdr.markForCheck();
          alert('Errore nel caricamento dell\'evento');
        },
      });
    }
  }

  private populateForm(event: EventDTOResponse): void {
    const eventDate = new Date(event.date);
    const formattedDate = eventDate.toISOString().slice(0, 16);

    this.eventForm.patchValue({
      name: event.name,
      description: event.description,
      date: formattedDate,
      layoutId: event.layoutId,
      active: event.active,
      genre: event.genre,
    });
  }

  private loadLayouts(): void {
    this.eventService.getLayouts().subscribe({
      next: (data) => {
        this.layouts = (data || []).map((layout: any) => {
          const conformationValue =
            layout.conformation ??
            layout.Conformation ??
            layout.name ??
            layout.label ??
            layout.eventName ??
            layout.string ??
            '';
          return {
            ...layout,
            id: layout.id ?? layout.eventId ?? layout.layoutId,
            conformation: String(conformationValue),
          };
        });
        this.cdr.markForCheck();
      },
      error: (err) => console.error('Error fetching layouts', err),
    });
  }

  onImageSelected(event: any): void {
    const file: File = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = () => {
        this.imagePreview = reader.result as string;
        this.eventForm.patchValue({ image: this.imagePreview });
        this.cdr.markForCheck();
      };
      reader.readAsDataURL(file);
    }
  }

  onSubmit(): void {
    if (this.eventForm.valid && this.eventId !== null) {
      this.isLoading = true;
      this.cdr.markForCheck();
      const formValue = this.eventForm.value;

      // Formato LocalDateTime con millisecondi: YYYY-MM-DDTHH:mm:ss.SSS
      const dateValue = new Date(formValue.date);
      const year = dateValue.getFullYear();
      const month = String(dateValue.getMonth() + 1).padStart(2, '0');
      const day = String(dateValue.getDate()).padStart(2, '0');
      const hours = String(dateValue.getHours()).padStart(2, '0');
      const minutes = String(dateValue.getMinutes()).padStart(2, '0');
      const seconds = String(dateValue.getSeconds()).padStart(2, '0');
      const milliseconds = String(dateValue.getMilliseconds()).padStart(3, '0');
      const localDateTime = `${year}-${month}-${day}T${hours}:${minutes}:${seconds}.${milliseconds}`;

      const updateData: EventDTORequest = {
        name: formValue.name,
        description: formValue.description,
        date: localDateTime,
        layoutId: Number(formValue.layoutId),
        active: formValue.active,
        genre: formValue.genre,
        image: formValue.image || (this.event?.image || ''),
      };

      console.log('Dati PUT:', updateData);

      this.eventService.updateEvent(this.eventId, updateData).subscribe({
        next: (res) => {
          console.log('Evento aggiornato!', res);
          this.isLoading = false;
          this.cdr.markForCheck();
          alert('Evento aggiornato con successo!');
          this.router.navigate(['/events']);
        },
        error: (err) => {
          console.error('Errore nella richiesta PUT:', err);
          console.error('Status:', err.status);
          console.error('Error details:', err.error);
          this.isLoading = false;
          this.cdr.markForCheck();
          alert('Errore nell\'aggiornamento: ' + (err.error?.message || err.statusText || 'Errore sconosciuto'));
        },
      });
    }
  }

  onCancel(): void {
    this.router.navigate(['/events']);
  }
}
