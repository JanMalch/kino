import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NotFoundComponent} from '@core/not-found/not-found.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'movie'
  },
  {
    path: 'admin',
    loadChildren: './../admin/admin.module#AdminModule'
  },
  {
    path: 'account',
    loadChildren: './../account/account.module#AccountModule'
  },
  {
    path: 'movie',
    loadChildren: './../movie/movie.module#MovieModule'
  },
  {
    path: 'reservation',
    loadChildren: './../reservation/reservation.module#ReservationModule'
  },
  {
    path: 'login',
    loadChildren: './../login/login.module#LoginModule'
  },
  {
    path: '404',
    component: NotFoundComponent
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '404'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { enableTracing: false })],
  exports: [RouterModule]
})
export class CoreRoutingModule { }
