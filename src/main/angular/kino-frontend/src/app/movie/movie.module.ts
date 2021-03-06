import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {MovieRoutingModule} from './movie-routing.module';
import {SharedModule} from '@shared/shared.module';
import {MovieOverviewComponent} from './movie-overview/movie-overview.component';
import {MovieResolver} from '@movie/movie.resolver';
import {RunningMoviesComponent} from '@movie/running-movies/running-movies.component';
import {FilterPastPipe} from './pipes';

@NgModule({
  declarations: [MovieOverviewComponent, RunningMoviesComponent, FilterPastPipe],
  imports: [
    CommonModule,
    MovieRoutingModule,
    SharedModule
  ],
  providers: [MovieResolver]
})
export class MovieModule {
}
