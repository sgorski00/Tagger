import {CanActivateFn, Router} from '@angular/router';
import {inject} from "@angular/core";
import {AuthService} from "./auth.service";

export const oauth2LoginGuard: CanActivateFn = (route) => {
  const redirectUrl = '/generate';
  const router = inject(Router);
  const authService = inject(AuthService);
  const token = route.queryParamMap.get('token');

  if(token) {
    authService.saveToken(token);
    router.navigate([redirectUrl]).then();
    return false;
  }

  router.navigate([redirectUrl], {queryParams: {error: 'missing-token'}}).then();
  return false;
};
