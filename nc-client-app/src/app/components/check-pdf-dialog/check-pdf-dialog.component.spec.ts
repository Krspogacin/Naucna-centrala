import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckPdfDialogComponent } from './check-pdf-dialog.component';

describe('CheckPdfDialogComponent', () => {
  let component: CheckPdfDialogComponent;
  let fixture: ComponentFixture<CheckPdfDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckPdfDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckPdfDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
