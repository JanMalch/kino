import {Component, OnInit} from '@angular/core';
import {Observable} from 'rxjs';
import {ReservationInfoDto} from '@api/model/reservationInfoDto';
import {DefaultService} from '@api/api/default.service';
import {tap} from "rxjs/operators";

@Component({
  selector: 'app-all-my-reservations',
  templateUrl: './all-my-reservations.component.html',
  styleUrls: ['./all-my-reservations.component.scss']
})
export class AllMyReservationsComponent implements OnInit {

  reservations$: Observable<ReservationInfoDto[]>;
  loading = true;

  constructor(private api: DefaultService) {
  }

  ngOnInit() {
    this.reservations$ = this.api.getMyReservations().pipe(
      tap(() => this.loading = false)
    );
  }

}
