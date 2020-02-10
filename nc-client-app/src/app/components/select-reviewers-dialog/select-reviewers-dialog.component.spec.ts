import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectReviewersDialogComponent } from './select-reviewers-dialog.component';

describe('SelectReviewersDialogComponent', () => {
  let component: SelectReviewersDialogComponent;
  let fixture: ComponentFixture<SelectReviewersDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectReviewersDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectReviewersDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
