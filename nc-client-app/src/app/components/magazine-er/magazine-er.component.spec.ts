import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { MagazineERComponent } from './magazine-er.component';

describe('MagazineERComponent', () => {
  let component: MagazineERComponent;
  let fixture: ComponentFixture<MagazineERComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ MagazineERComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(MagazineERComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
