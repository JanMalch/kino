import {Injectable, OnDestroy} from '@angular/core';
import {DefaultService} from '@api/api/default.service';
import {Observable} from 'rxjs';
import {ReservationInfoDto} from '@api/model/reservationInfoDto';
import {untilDestroyed} from 'ngx-take-until-destroy';
import {map, shareReplay, tap} from 'rxjs/operators';
import {SuccessMessage} from "@api/model/successMessage";

@Injectable({
  providedIn: 'root'
})
export class ReservationService implements OnDestroy {

  private myReservations$: Observable<ReservationInfoDto[]>;

  constructor(private api: DefaultService) {
    this.refresh();
  }

  refresh() {
    this.myReservations$ = this.api.getMyReservations().pipe(
      untilDestroyed(this),
      shareReplay(1)
    );
  }

  getMyReservations(): Observable<ReservationInfoDto[]> {
    return this.myReservations$;
  }

  getMyReservation(id: number): Observable<ReservationInfoDto> {
    return this.myReservations$.pipe(
      map(reservations => reservations.find(r => r.id === id))
    );
  }

  deleteMyReservation(id: number): Observable<SuccessMessage> {
    return this.api.deleteReservationById(id).pipe(
      tap(() => this.refresh())
    );
  }

  ngOnDestroy(): void {
  }

}
