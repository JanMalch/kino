import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ReservationRoutingModule} from './reservation-routing.module';
import {NewReservationComponent} from './new-reservation/new-reservation.component';
import {SharedModule} from '@shared/shared.module';
import {ViewReservationComponent} from './view-reservation/view-reservation.component';
import {ReservationsListComponent} from '@reservation/reservations-list/reservations-list.component';
import {AllMyReservationsComponent} from './all-my-reservations/all-my-reservations.component';

@NgModule({
  declarations: [NewReservationComponent, ViewReservationComponent, ReservationsListComponent, AllMyReservationsComponent],
  imports: [
    CommonModule,
    ReservationRoutingModule,
    SharedModule
  ],
  exports: [
    ViewReservationComponent
  ],
  providers: []
})
export class ReservationModule { }
