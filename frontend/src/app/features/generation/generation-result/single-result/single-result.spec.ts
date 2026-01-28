import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleResult } from './single-result';

describe('SingleResult', () => {
  let component: SingleResult;
  let fixture: ComponentFixture<SingleResult>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SingleResult]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SingleResult);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
