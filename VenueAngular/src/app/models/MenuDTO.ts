export interface MenuItemDTORequest {
  plate: string;
  description: string;
  originalPrice: number;
  categoryId: number;
}

export interface MenuItemDTOResponse {
  id: number;
  name: string;
  url: string;
  originalPrice: number;
}

export interface MenuCategoryDTO {
  name: string;
}