import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HistoryItemsList } from './history-items-list';

describe('HistoryItemsList', () => {
  let component: HistoryItemsList;
  let fixture: ComponentFixture<HistoryItemsList>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HistoryItemsList]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HistoryItemsList);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
