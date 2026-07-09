import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MenuCategoryDTORequest } from '../models/MenuCategoryDTO';
import { MenuCategoryDTOResponse } from '../models/MenuCategoryDTO';
import { MenuItemDTORequest } from '../models/MenuItemsDTO';
import { MenuItemDTOResponse } from '../models/MenuItemsDTO';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private apiUrl = 'http://localhost:8080/api/menu';

  constructor(private http: HttpClient) { }

  getMenu(): Observable<MenuCategoryDTOResponse[]> {
    return this.http.get<MenuCategoryDTOResponse[]>(this.apiUrl);
  }

  getAllMenuItems(category?: string): Observable<MenuItemDTOResponse[]> {
    let params = new HttpParams();
    if (category) {
      params = params.set('category', category);
    }
    return this.http.get<MenuItemDTOResponse[]>(`${this.apiUrl}/items`, { params });
  }

  createCategory(newCategory: MenuCategoryDTORequest): Observable<MenuCategoryDTORequest> {
    return this.http.post<MenuCategoryDTORequest>(`${this.apiUrl}/categories`, newCategory);
  }

  updateCategory(categoryName: string, updatedCategory: MenuCategoryDTORequest): Observable<MenuCategoryDTORequest> {
    return this.http.put<MenuCategoryDTORequest>(`${this.apiUrl}/categories/modifica/${categoryName}`, updatedCategory);
  }

  deleteCategory(categoryName: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/categories/cancella/${categoryName}`);
  }

  addMenuItemToCategory(categoryName: string, menuItem: MenuItemDTORequest): Observable<MenuItemDTORequest> {
    return this.http.post<MenuItemDTORequest>(`${this.apiUrl}/categories/${categoryName}/items`, menuItem);
  }

  updateMenuItem(categoryName: string, itemId: number, updatedMenuItem: MenuItemDTORequest): Observable<MenuItemDTORequest> {
    return this.http.put<MenuItemDTORequest>(`${this.apiUrl}/categories/${categoryName}/items/${itemId}`, updatedMenuItem);
  }

  deleteMenuItem(categoryName: string, itemId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/categories/${categoryName}/items/${itemId}`);
  }
}