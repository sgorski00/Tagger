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
    }
];
