import { Component, OnInit, ChangeDetectorRef } from '@angular/core'; 
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { EventService } from '../../services/event.service';
import { EventLayoutService } from '../../services/event-layout.service'; 
import { EventLayoutDTO } from '../../models/EventDTO'; 

@Component({
  selector: 'app-event-creation',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule], 
  templateUrl: './event-creation.html',
  styleUrl: './event-creation.css',
})
export class CreateEventComponent implements OnInit {
  eventForm: FormGroup;
  layouts: EventLayoutDTO[] = [];
  isReady: boolean = false;
  
  constructor(
    private fb: FormBuilder, 
    private eventService: EventService,
    private layoutService: EventLayoutService,
    private cdr: ChangeDetectorRef
  ) {
    this.eventForm = this.fb.group({
      name: ['', Validators.required],
      description: [''],
      date: ['', Validators.required],
      layoutId: [null, Validators.required],
      active: [true],
      genre: ['']
    });
  }

  ngOnInit(): void {
    this.layoutService.getAllEventLayouts().subscribe({
      next: (data) => {
        this.layouts = data;
        this.isReady = true;
        this.cdr.detectChanges();
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
}