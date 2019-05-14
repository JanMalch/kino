import { TestBed } from '@angular/core/testing';

import { CinemaHallService } from './cinema-hall.service';

describe('CinemaHallService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: CinemaHallService = TestBed.get(CinemaHallService);
    expect(service).toBeTruthy();
  });
});
