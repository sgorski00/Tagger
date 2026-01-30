import {HttpInterceptorFn} from '@angular/common/http';
import {inject} from '@angular/core';
import {LoadingService} from '../services';
import {finalize} from 'rxjs';

export const loadingInterceptor: HttpInterceptorFn = (req, next) => {
  const loadingService = inject(LoadingService);

  const isGenerationRequest = req.method === 'POST' && req.url.includes('/api/tags/');

  if (isGenerationRequest) loadingService.showFormLoading();
  return next(req).pipe(
    finalize(() => {if (isGenerationRequest) loadingService.hideFormLoading()})
  );
};
