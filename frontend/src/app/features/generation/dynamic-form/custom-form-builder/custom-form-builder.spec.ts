import { TestBed } from '@angular/core/testing';

import CustomFormBuilder from './custom-form-builder';

describe('CustomFormBuilder', () => {
  let service: CustomFormBuilder;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustomFormBuilder);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
