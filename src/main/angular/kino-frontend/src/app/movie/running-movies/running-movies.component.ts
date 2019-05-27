import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {map, shareReplay} from 'rxjs/operators';
import {BreakpointObserver, Breakpoints} from '@angular/cdk/layout';
import {Observable} from 'rxjs';
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
      shareReplay(1)
    );
  }
}

export interface MoviesByWeek {
  start: Date;
  end: Date;
  movies: MovieInfoDto[];
}

