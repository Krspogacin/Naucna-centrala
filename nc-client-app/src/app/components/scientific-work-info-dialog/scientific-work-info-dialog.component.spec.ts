import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientificWorkInfoDialogComponent } from './scientific-work-info-dialog.component';

describe('ScientificWorkInfoDialogComponent', () => {
  let component: ScientificWorkInfoDialogComponent;
  let fixture: ComponentFixture<ScientificWorkInfoDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientificWorkInfoDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientificWorkInfoDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
