import {Injectable, OnDestroy} from '@angular/core';
import {Observable, of} from 'rxjs';
import {DefaultService} from '@api/api/default.service';
import {untilDestroyed} from 'ngx-take-until-destroy';
import {map, shareReplay, tap} from 'rxjs/operators';
import {MovieDto} from '@api/model/movieDto';
import {MovieOverviewDto} from '@api/model/movieOverviewDto';
import {MovieInfoDto} from '@api/model/movieInfoDto';

@Injectable({
  providedIn: 'root'
})
export class MovieService implements OnDestroy {

  private movies$: Observable<MovieOverviewDto>;
  private readonly movieCache = new Map();

  constructor(private api: DefaultService) {
    this.movies$ = this.api.getCurrentMovies().pipe(
      untilDestroyed(this),
      shareReplay(1)
    );
  }

  refresh() {
    this.movieCache.clear();
    this.movies$ = this.api.getCurrentMovies().pipe(
      untilDestroyed(this),
      shareReplay(1)
    );
  }

  getRunningMovies(): Observable<MovieOverviewDto> {
    return this.movies$;
  }

  getMovieInfo(id: number): Observable<MovieInfoDto> {
    return this.movies$.pipe(
      map(overview => overview.movies[id])
    );
  }

  getMovie(id: number): Observable<MovieDto> {
    if (this.movieCache.has(id)) {
      return of(this.movieCache.get(id));
    }

    return this.api.getMovie(id).pipe(
      tap(movie => {
        this.movieCache.set(id, movie);
      })
    );
  }


  ngOnDestroy() {
  }
}
