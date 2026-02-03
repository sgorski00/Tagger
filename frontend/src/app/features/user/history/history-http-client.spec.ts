import { TestBed } from '@angular/core/testing';

import { HistoryHttpClient } from './history-http-client';

describe('HistoryHttpClient', () => {
  let service: HistoryHttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HistoryHttpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
