export interface PromotionDTORequest {
  promotionTable: number;
  promotionPrice: number;
  expiresIn: string;
}

export interface PromotionDTOResponse {
  id: number;
  promotionTable: number;
  promotionPrice: number;
  createdAt: string;
  expiresIn: string;
}