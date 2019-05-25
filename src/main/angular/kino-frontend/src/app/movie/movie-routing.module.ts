import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MovieOverviewComponent} from '@movie/movie-overview/movie-overview.component';
import {MovieResolver} from '@movie/movie.resolver';
import {RunningMoviesComponent} from '@movie/running-movies/running-movies.component';

const routes: Routes = [
  {
    path: ':id',
    component: MovieOverviewComponent,
    resolve: {
      movie: MovieResolver
    }
  },
  {
    path: '',
    pathMatch: 'full',
    component: RunningMoviesComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MovieRoutingModule {
}
