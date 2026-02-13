import { Routes } from '@angular/router';
import {authGuard} from "./core/guards/auth-guard";

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'generate',
        pathMatch: 'full'
    },
    {
        path: 'generate',
        loadChildren: () => import('./features/generation/generation.routes')
            .then(m => m.generationRoutes)
    },
    {
        path: 'oauth2',
        loadChildren: () => import('./features/auth/oauth2.routes')
            .then(m => m.oauth2Routes)
    },
    {
        path: 'user',
        loadChildren: () => import('./features/user/user.routes')
            .then(m => m.userRoutes),
        canActivate: [authGuard]
    },
    {
        path: '401',
        loadComponent: () => import('./features/errors/unauthorized/unauthorized-page')
            .then(m => m.UnauthorizedPage)
    }
];
