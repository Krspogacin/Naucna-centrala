import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangeFormatDialogComponent } from './change-format-dialog.component';

describe('ChangeFormatDialogComponent', () => {
  let component: ChangeFormatDialogComponent;
  let fixture: ComponentFixture<ChangeFormatDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChangeFormatDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChangeFormatDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
