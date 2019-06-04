import {Component, Input, OnInit} from '@angular/core';
import {ParseDatePipe} from "@shared/pipes";
import {ReservationInfoWithPresentation} from "@reservation/all-my-reservations/all-my-reservations.component";

@Component({
  selector: 'app-reservations-list[reservations]',
  templateUrl: './reservations-list.component.html',
  styleUrls: ['./reservations-list.component.scss']
})
export class ReservationsListComponent implements OnInit {

  private readonly parseDatePipe = new ParseDatePipe();

  @Input() set reservations(value: ReservationInfoWithPresentation[]) {
    this.upcoming = [];
    this.seen = [];
    const now = Date.now();
    (value || []).forEach(r => {
      const parsedDate = this.parseDatePipe.transform(r.presentation.date);
      if (parsedDate.getTime() < now) {
        this.seen.push(r);
      } else {
        this.upcoming.push(r);
      }
    });
  }

  upcoming: ReservationInfoWithPresentation[] = [];
  seen: ReservationInfoWithPresentation[] = [];

  constructor() {
  }

  ngOnInit() {
  }

}
