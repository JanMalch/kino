import {Component, Input, OnInit} from '@angular/core';
import {ReservationInfoDto} from '@api/model/reservationInfoDto';
import {ParseDatePipe} from "@shared/pipes";

@Component({
  selector: 'app-reservations-list[reservations]',
  templateUrl: './reservations-list.component.html',
  styleUrls: ['./reservations-list.component.scss']
})
export class ReservationsListComponent implements OnInit {

  private readonly parseDatePipe = new ParseDatePipe();

  @Input() set reservations(value: ReservationInfoDto[]) {
    this.upcoming = [];
    this.seen = [];
    const now = Date.now();
    (value || []).forEach(r => {
      const parsedDate = this.parseDatePipe.transform(r.reservationDate);
      if (parsedDate.getTime() < now) {
        this.seen.push(r);
      } else {
        this.upcoming.push(r);
      }
    });
  }

  upcoming: ReservationInfoDto[] = [];
  seen: ReservationInfoDto[] = [];

  constructor() {
  }

  ngOnInit() {
  }

}
