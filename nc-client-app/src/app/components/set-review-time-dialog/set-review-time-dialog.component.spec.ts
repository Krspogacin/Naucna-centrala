import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetReviewTimeDialogComponent } from './set-review-time-dialog.component';

describe('SetReviewTimeDialogComponent', () => {
  let component: SetReviewTimeDialogComponent;
  let fixture: ComponentFixture<SetReviewTimeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetReviewTimeDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetReviewTimeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
