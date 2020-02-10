import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NewReviewersDialogComponent } from './new-reviewers-dialog.component';

describe('NewReviewersDialogComponent', () => {
  let component: NewReviewersDialogComponent;
  let fixture: ComponentFixture<NewReviewersDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NewReviewersDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NewReviewersDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
