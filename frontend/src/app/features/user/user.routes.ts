import {Routes} from "@angular/router";
import {ProfilePage} from "./profile/profile-page/profile-page";
import {HistoryPage} from "./history/history-page/history-page";

export const userRoutes: Routes = [
    {
        path: '',
        redirectTo: 'profile',
        pathMatch: 'full'
    },
    {
        path: 'profile',
        component: ProfilePage
    },
    {
        path: 'history',
        component: HistoryPage
    },
    {
        path: 'history/:id',
        component: HistoryPage
    }
]