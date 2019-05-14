import {Injectable, OnDestroy} from '@angular/core';
import {DefaultService} from '@api/api/default.service';
import {Observable} from 'rxjs';
import {ReservationInfoDto} from '@api/model/reservationInfoDto';
import {untilDestroyed} from 'ngx-take-until-destroy';
import {map, shareReplay} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ReservationService implements OnDestroy {

  private readonly myReservations$: Observable<ReservationInfoDto[]>;

  constructor(private api: DefaultService) {
    this.myReservations$ = this.api.getMyReservations().pipe(
      untilDestroyed(this),
      shareReplay(1)
    );
  }

  getMyReservation(id: number): Observable<ReservationInfoDto> {
    return this.myReservations$.pipe(
      map(reservations => reservations.find(r => r.id === id))
    );
  }

  ngOnDestroy(): void {
  }

}
