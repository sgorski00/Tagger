import {Routes} from "@angular/router";
import {oauth2LoginGuard} from "./oauth2-login-guard";
import {Oauth2Placeholder} from "./oauth2-placeholder";
import {oauth2LogoutGuard} from "./oauth2-logout-guard";

export const oauth2Routes: Routes = [
    {
        path: 'success',
        component: Oauth2Placeholder, // Just a placeholder, actual handling is in the guard
        canActivate: [oauth2LoginGuard]
    },
    {
        path: 'logout',
        component: Oauth2Placeholder, // Just a placeholder, actual handling is in the guard
        canActivate: [oauth2LogoutGuard]
    }
]
