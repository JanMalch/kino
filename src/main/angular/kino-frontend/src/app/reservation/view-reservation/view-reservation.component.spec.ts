import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewReservationComponent } from './view-reservation.component';

describe('ViewReservationComponent', () => {
  let component: ViewReservationComponent;
  let fixture: ComponentFixture<ViewReservationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewReservationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewReservationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
