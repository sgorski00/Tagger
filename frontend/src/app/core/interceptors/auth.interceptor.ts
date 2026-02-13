import {HttpInterceptorFn} from "@angular/common/http";
import {inject} from "@angular/core";
import {AuthService} from "../../features/auth/auth.service";
import {catchError, throwError} from "rxjs";
import {Router} from "@angular/router";

export const authInterceptor: HttpInterceptorFn = (req, next) => {
    const authService = inject(AuthService);
    const router = inject(Router);
    const token = authService.getToken();
    if(token) {
        req = req.clone({
            setHeaders: {
                Authorization: `Bearer ${token}`
            }
        });
    }
    return next(req).pipe(
        catchError(err => {
            if(err.status === 401) {
                authService.removeToken();
                router.navigate(['/401'], {state: {details: err.error?.message || 'Login required'}});
            }
            return throwError(() => err);
        })
    );
};