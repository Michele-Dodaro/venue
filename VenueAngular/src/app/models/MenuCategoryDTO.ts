import { MenuItemDTOResponse } from './MenuDTO';

export interface MenuCategoryDTORequest {
    type: string;
}

export interface MenuCategoryDTOResponse {
  name: string;
  items: MenuItemDTOResponse[];
}