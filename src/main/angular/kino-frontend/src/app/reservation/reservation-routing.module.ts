import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {NewReservationComponent} from '@reservation/new-reservation/new-reservation.component';
import {ViewReservationComponent} from '@reservation/view-reservation/view-reservation.component';
import {AuthGuard} from '@core/auth';
import {ReservationsListComponent} from '@reservation/reservations-list/reservations-list.component';
import {AllMyReservationsComponent} from '@reservation/all-my-reservations/all-my-reservations.component';

const routes: Routes = [
  {
    path: 'new',
    pathMatch: 'full',
    component: NewReservationComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'all',
    pathMatch: 'full',
    component: AllMyReservationsComponent,
    canActivate: [AuthGuard]
  },
  {
    path: ':id',
    component: ViewReservationComponent,
    canActivate: [AuthGuard]
  },
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'all'
  },
  {
    path: '**',
    pathMatch: 'full',
    redirectTo: '/404'
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ReservationRoutingModule {
}
