import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MenuCategoryDTO, MenuItemDTORequest, MenuItemDTOResponse } from '../models/MenuDTO';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private apiUrl = 'http://localhost:8080/api/menu';

  constructor(private http: HttpClient) {}

  getMenu(): Observable<MenuCategoryDTO[]> {
    return this.http.get<MenuCategoryDTO[]>(this.apiUrl);
  }

  getAllMenuItems(category?: string): Observable<MenuItemDTOResponse[]> {
    let params = new HttpParams();
    if (category) {
      params = params.set('category', category);
    }
    return this.http.get<MenuItemDTOResponse[]>(`${this.apiUrl}/items`, { params });
  }

  createCategory(newCategory: MenuCategoryDTO): Observable<MenuCategoryDTO> {
    return this.http.post<MenuCategoryDTO>(`${this.apiUrl}/categories`, newCategory);
  }

updateCategory(categoryName: string, updatedCategory: MenuCategoryDTO): Observable<MenuCategoryDTO> {
  return this.http.put<MenuCategoryDTO>(`${this.apiUrl}/categories/modifica/${categoryName}`, updatedCategory);
}

  deleteCategory(categoryName: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/categories/cancella/${categoryName}`);
  }

  addMenuItemToCategory(categoryName: string, menuItem: MenuItemDTORequest): Observable<MenuItemDTOResponse> {
    return this.http.post<MenuItemDTOResponse>(`${this.apiUrl}/categories/${categoryName}/items`, menuItem);
  }

  updateMenuItem(categoryName: string, itemId: number, updatedMenuItem: MenuItemDTORequest): Observable<MenuItemDTOResponse> {
    return this.http.put<MenuItemDTOResponse>(`${this.apiUrl}/categories/${categoryName}/items/${itemId}`, updatedMenuItem);
  }

  deleteMenuItem(categoryName: string, itemId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/categories/${categoryName}/items/${itemId}`);
  }
}