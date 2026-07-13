import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { provideHttpClient } from '@angular/common/http';
import { CreateMenuCategoryComponent } from './menu-category-creation';
import { MenuService } from '../../services/menu.service';

describe('CreateMenuCategoryComponent', () => {
  let component: CreateMenuCategoryComponent;
  let fixture: ComponentFixture<CreateMenuCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateMenuCategoryComponent, ReactiveFormsModule],
      providers: [MenuService, provideHttpClient()]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateMenuCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
