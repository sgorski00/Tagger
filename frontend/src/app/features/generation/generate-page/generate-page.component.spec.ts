import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneratePageComponent } from './generate-page.component';

describe('GeneratePage', () => {
  let component: GeneratePageComponent;
  let fixture: ComponentFixture<GeneratePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GeneratePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GeneratePageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
