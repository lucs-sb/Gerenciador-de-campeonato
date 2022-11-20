import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavbarChampionshipComponent } from './navbar-championship.component';

describe('NavbarChampionshipComponent', () => {
  let component: NavbarChampionshipComponent;
  let fixture: ComponentFixture<NavbarChampionshipComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavbarChampionshipComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavbarChampionshipComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
