import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneralForm } from './general-form';

describe('GeneralForm', () => {
  let component: GeneralForm;
  let fixture: ComponentFixture<GeneralForm>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GeneralForm]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GeneralForm);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
