import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateLayout } from './create-layout';

describe('CreateLayout', () => {
  let component: CreateLayout;
  let fixture: ComponentFixture<CreateLayout>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateLayout],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateLayout);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
