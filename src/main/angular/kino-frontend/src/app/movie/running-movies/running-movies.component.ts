import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {catchError, map, shareReplay, startWith, tap} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Observable, throwError} from 'rxjs';
import {MovieInfoDto} from '@api/model/movieInfoDto';
import {MovieService} from '@core/services';

@Component({
  selector: 'app-movie-overview',
  templateUrl: './running-movies.component.html',
  styleUrls: ['./running-movies.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class RunningMoviesComponent implements OnInit {

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches),
      shareReplay(1)
    );

  weeks$: Observable<MoviesByWeek[]>;
  loading = true;

  constructor(private movieService: MovieService,
              private breakpointObserver: BreakpointObserver) {
  }

  ngOnInit(): void {
    this.weeks$ = this.movieService.getRunningMovies().pipe(
      map(dto => {
        return !!dto ? dto : {
          movies: {},
          weeks: []
        };
      }),
      map(dto => {
        return dto.weeks.map(({start, end, movieIds}) => {
          return {
            start, end,
            movies: movieIds.map(id => dto.movies[id])
          };
        });
      }),
      always(() => this.loading = false),
      /*catchError(err => {
        this.loading = false;
        return throwError(err);
      }),
      tap(() => this.loading = false),*/
      startWith([]),
      shareReplay(1)
    );
  }
}

const always = <T>(fn: () => void) => (source$: Observable<T>) =>
  source$.pipe(
    catchError(err => {
      fn();
      return throwError(err);
    }),
    tap(() => fn())
  );

export interface MoviesByWeek {
  start: Date;
  end: Date;
  movies: MovieInfoDto[];
}

