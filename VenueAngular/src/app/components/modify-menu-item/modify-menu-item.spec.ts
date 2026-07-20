import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifyMenuItem } from './modify-menu-item';

describe('ModifyMenuItem', () => {
  let component: ModifyMenuItem;
  let fixture: ComponentFixture<ModifyMenuItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModifyMenuItem],
    }).compileComponents();

    fixture = TestBed.createComponent(ModifyMenuItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
