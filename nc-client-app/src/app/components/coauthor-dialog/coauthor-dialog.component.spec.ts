import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CoauthorDialogComponent } from './coauthor-dialog.component';

describe('CoauthorDialogComponent', () => {
  let component: CoauthorDialogComponent;
  let fixture: ComponentFixture<CoauthorDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CoauthorDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CoauthorDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
