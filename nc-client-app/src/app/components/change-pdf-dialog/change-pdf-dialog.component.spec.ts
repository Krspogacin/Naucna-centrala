import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangePdfDialogComponent } from './change-pdf-dialog.component';

describe('ChangePdfDialogComponent', () => {
  let component: ChangePdfDialogComponent;
  let fixture: ComponentFixture<ChangePdfDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangePdfDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangePdfDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
