import { TestBed } from '@angular/core/testing';

import { GenerationHttpClient } from './generation-http-client';

describe('GenerationHttpClient', () => {
  let service: GenerationHttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GenerationHttpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
