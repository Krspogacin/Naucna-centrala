import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazineHomepageComponent } from './magazine-homepage.component';

describe('MagazineHomepageComponent', () => {
  let component: MagazineHomepageComponent;
  let fixture: ComponentFixture<MagazineHomepageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazineHomepageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineHomepageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
