import { MenuItemDTOResponse } from './MenuItemsDTO';

export interface MenuCategoryDTORequest {
    type: string;
}

export interface MenuCategoryDTOResponse {
  name: string;
  items: MenuItemDTOResponse[];
}