import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckScientificWorkDialogComponent } from './check-scientific-work-dialog.component';

describe('CheckScientificWorkDialogComponent', () => {
  let component: CheckScientificWorkDialogComponent;
  let fixture: ComponentFixture<CheckScientificWorkDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckScientificWorkDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckScientificWorkDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
