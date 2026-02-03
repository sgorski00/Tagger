import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryItemCard } from './history-item-card';

describe('HistoryItemCard', () => {
  let component: HistoryItemCard;
  let fixture: ComponentFixture<HistoryItemCard>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoryItemCard]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoryItemCard);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
