import { ComponentFixture, TestBed } from '@angular/core/testing';
import { UpdateMenuCategory } from './update-menu-category';

describe('UpdateMenuCategory', () => { 
  let component: UpdateMenuCategory;
  let fixture: ComponentFixture<UpdateMenuCategory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpdateMenuCategory],
    }).compileComponents();

    fixture = TestBed.createComponent(UpdateMenuCategory);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});