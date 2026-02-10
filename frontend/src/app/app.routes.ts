import { Routes } from '@angular/router';

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
            .then(m => m.userRoutes)
    }
];
