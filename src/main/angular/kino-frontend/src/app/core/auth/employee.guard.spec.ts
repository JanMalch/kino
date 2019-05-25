import {inject, TestBed} from '@angular/core/testing';

import {EmployeeGuard} from './employee-guard.service';

describe('EmployeeGuard', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [EmployeeGuard]
    });
  });

  it('should ...', inject([EmployeeGuard], (guard: EmployeeGuard) => {
    expect(guard).toBeTruthy();
  }));
});
