import { TestBed, async, inject } from '@angular/core/testing';

import { MovieResolver } from './movie.resolver';

describe('MovieResolver', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MovieResolver]
    });
  });

  it('should ...', inject([MovieResolver], (guard: MovieResolver) => {
    expect(guard).toBeTruthy();
  }));
});
