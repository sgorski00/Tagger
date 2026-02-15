import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {LanguageService} from '../../features/language/language.service';

export const languageInterceptor: HttpInterceptorFn = (req, next) => {
  const languageService = inject(LanguageService);
  const language = languageService.getLanguage();
  if(language) {
    req = req.clone({
      setHeaders: {
        'Accept-Language': language
      }
    })
  }
  return next(req);
}
