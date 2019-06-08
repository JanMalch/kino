import {inject, TestBed} from '@angular/core/testing';

import {ReservationPermissionGuard} from './reservation-permission.guard';

describe('ReservationPermissionGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ReservationPermissionGuard]
    });
  });

  it('should ...', inject([ReservationPermissionGuard], (guard: ReservationPermissionGuard) => {
    expect(guard).toBeTruthy();
  }));
});
