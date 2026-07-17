import { RenderMode, ServerRoute } from '@angular/ssr';

export const serverRoutes: ServerRoute[] = [
  {
    path: 'events',
    renderMode: RenderMode.Client
  },
  {
    path: 'events/:id',
    renderMode: RenderMode.Server
  },
  {
    path: 'events/:id/seats',
    renderMode: RenderMode.Client
  },
  {
    path: 'edit-category/:name',
    renderMode: RenderMode.Client
  },
  {
    path: 'categories/:categories/items/:items',
    renderMode: RenderMode.Client
  },
  {
    path: 'categories/:categories/items',
    renderMode: RenderMode.Client
  },
  {
    path: '**',
    renderMode: RenderMode.Prerender
  }
];
