import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorFinalDecisionDialogComponent } from './editor-final-decision-dialog.component';

describe('EditorFinalDecisionDialogComponent', () => {
  let component: EditorFinalDecisionDialogComponent;
  let fixture: ComponentFixture<EditorFinalDecisionDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorFinalDecisionDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorFinalDecisionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
