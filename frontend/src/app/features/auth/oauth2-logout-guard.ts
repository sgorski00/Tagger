import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "./auth.service";

export const oauth2LogoutGuard: CanActivateFn = () => {
  //TODO: after refresh token implementation deactivate it there
  const router = inject(Router);
  const authService = inject(AuthService);
  authService.removeToken();
  router.navigate(['/'], {queryParams: {message: 'logged-out'}}).then();
  return false;
};
