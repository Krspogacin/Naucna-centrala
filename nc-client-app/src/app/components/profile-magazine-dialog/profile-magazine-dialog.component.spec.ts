import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfileMagazineDialogComponent } from './profile-magazine-dialog.component';

describe('ProfileMagazineDialogComponent', () => {
  let component: ProfileMagazineDialogComponent;
  let fixture: ComponentFixture<ProfileMagazineDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfileMagazineDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfileMagazineDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
