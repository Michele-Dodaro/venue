import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PromotionDTORequest } from '../models/PromotionDTO';
import { PromotionDTOResponse } from '../models/PromotionDTO';

@Injectable({
  providedIn: 'root'
})
export class PromotionService {
  private apiUrl = 'http://localhost:8080/api/promotions';

  constructor(private http: HttpClient) { }

  createPromotion(request: PromotionDTORequest): Observable<PromotionDTOResponse> {
    return this.http.post<PromotionDTOResponse>(this.apiUrl, request);
  }

  applyPromotionToItem(promotionId: number, menuItemId: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/${promotionId}/apply-to-item/${menuItemId}`, {}, { responseType: 'text' });
  }

  applyPromotionToLayout(promotionId: number, layoutId: number): Observable<string> {
    return this.http.post(`${this.apiUrl}/${promotionId}/apply-to-layout/${layoutId}`, {}, { responseType: 'text' });
  }
}