import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventCreationComponent } from './event-creation';

describe('EventCreation', () => {
  let component: EventCreationComponent;
  let fixture: ComponentFixture<EventCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventCreationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(EventCreationComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
