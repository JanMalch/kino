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

  private readonly movies$: Observable<MovieOverviewDto>;
  private readonly movieCache = {};

  constructor(private api: DefaultService) {
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
    if (id in this.movieCache) {
      return of(this.movieCache[id]);
    }

    return this.api.getMovie(id).pipe(
      tap(movie => {
        this.movieCache[id] = movie;
      })
    );
  }


  ngOnDestroy() {
  }
}
