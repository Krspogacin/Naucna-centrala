import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ActiveReviewsComponent } from './active-reviews.component';

describe('ActiveReviewsComponent', () => {
  let component: ActiveReviewsComponent;
  let fixture: ComponentFixture<ActiveReviewsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ActiveReviewsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ActiveReviewsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
