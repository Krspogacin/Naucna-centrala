import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditorDecisionDialogComponent } from './editor-decision-dialog.component';

describe('EditorDecisionDialogComponent', () => {
  let component: EditorDecisionDialogComponent;
  let fixture: ComponentFixture<EditorDecisionDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditorDecisionDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditorDecisionDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
