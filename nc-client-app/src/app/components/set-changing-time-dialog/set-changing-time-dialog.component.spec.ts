import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetChangingTimeDialogComponent } from './set-changing-time-dialog.component';

describe('SetChangingTimeDialogComponent', () => {
  let component: SetChangingTimeDialogComponent;
  let fixture: ComponentFixture<SetChangingTimeDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetChangingTimeDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetChangingTimeDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
