import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyEvent } from './modify-event';

describe('ModifyEvent', () => {
  let component: ModifyEvent;
  let fixture: ComponentFixture<ModifyEvent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModifyEvent],
    }).compileComponents();

    fixture = TestBed.createComponent(ModifyEvent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
