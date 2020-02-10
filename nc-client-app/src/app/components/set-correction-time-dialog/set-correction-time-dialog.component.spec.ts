import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetCorrectionTimeDialogComponent } from './set-correction-time-dialog.component';

describe('SetCorrectionTimeDialogComponent', () => {
  let component: SetCorrectionTimeDialogComponent;
  let fixture: ComponentFixture<SetCorrectionTimeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetCorrectionTimeDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetCorrectionTimeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
