import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MyScientificWorksComponent } from './my-scientific-works.component';

describe('MyScientificWorksComponent', () => {
  let component: MyScientificWorksComponent;
  let fixture: ComponentFixture<MyScientificWorksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MyScientificWorksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MyScientificWorksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
