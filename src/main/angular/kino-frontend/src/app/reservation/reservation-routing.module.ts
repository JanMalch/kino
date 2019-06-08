import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {NewReservationComponent} from '@reservation/new-reservation/new-reservation.component';
import {ViewReservationComponent} from '@reservation/view-reservation/view-reservation.component';
import {AuthGuard} from '@core/auth';
import {AllMyReservationsComponent} from '@reservation/all-my-reservations/all-my-reservations.component';
import {ReservationPermissionGuard} from '@reservation/reservation-permission.guard';

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
    canActivate: [AuthGuard, ReservationPermissionGuard]
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
