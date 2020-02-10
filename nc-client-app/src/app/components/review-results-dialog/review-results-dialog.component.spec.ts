import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ReviewResultsDialogComponent } from './review-results-dialog.component';

describe('ReviewResultsDialogComponent', () => {
  let component: ReviewResultsDialogComponent;
  let fixture: ComponentFixture<ReviewResultsDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ReviewResultsDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ReviewResultsDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
