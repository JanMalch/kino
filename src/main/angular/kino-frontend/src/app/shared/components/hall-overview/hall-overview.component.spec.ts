import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HallOverviewComponent } from './hall-overview.component';

describe('HallOverviewComponent', () => {
  let component: HallOverviewComponent;
  let fixture: ComponentFixture<HallOverviewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HallOverviewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HallOverviewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
