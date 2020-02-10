import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SetChangingTimeAgainDialogComponent } from './set-changing-time-again-dialog.component';

describe('SetChangingTimeAgainDialogComponent', () => {
  let component: SetChangingTimeAgainDialogComponent;
  let fixture: ComponentFixture<SetChangingTimeAgainDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SetChangingTimeAgainDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SetChangingTimeAgainDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
