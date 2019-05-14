import {Component, Input, OnInit} from '@angular/core';
import {ReservationInfoDto} from '@api/model/reservationInfoDto';

@Component({
  selector: 'app-reservations-list[reservations]',
  templateUrl: './reservations-list.component.html',
  styleUrls: ['./reservations-list.component.scss']
})
export class ReservationsListComponent implements OnInit {

  @Input() reservations: ReservationInfoDto[] = [];

  constructor() { }

  ngOnInit() {
  }

}
