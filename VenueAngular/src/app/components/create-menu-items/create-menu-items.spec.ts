import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateMenuItems } from './create-menu-items';

describe('CreateMenuItems', () => {
  let component: CreateMenuItems;
  let fixture: ComponentFixture<CreateMenuItems>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateMenuItems],
    }).compileComponents();

    fixture = TestBed.createComponent(CreateMenuItems);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
