import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ScientificWorkComponent } from './scientific-work.component';

describe('ScientificWorkComponent', () => {
  let component: ScientificWorkComponent;
  let fixture: ComponentFixture<ScientificWorkComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ScientificWorkComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ScientificWorkComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
